import os
import pymysql
from datetime import datetime
from dotenv import load_dotenv
from chat.chat_model import Chat
from fastapi import APIRouter
from langchain_upstage import ChatUpstage
from langchain_core.prompts import PromptTemplate
from langchain_core.output_parsers import StrOutputParser  # LLM 결과 문자열로 파싱
from langchain.schema import Document
from langchain_upstage import UpstageEmbeddings
from langchain_chroma import Chroma
from langchain.text_splitter import RecursiveCharacterTextSplitter

# .env 파일을 읽어 자동으로 환경변수 설정
load_dotenv()

router = APIRouter()

# MySQL DB 연결
conn = pymysql.connect(
    host=os.getenv('DB_HOST'),
    port=int(os.getenv('DB_PORT')),   # 포트는 int로 변환 필요
    user=os.getenv('DB_USER'),
    password=os.getenv('DB_PASSWORD'),
    database=os.getenv('DB_NAME'),
    charset='utf8mb4',                      # 한글 등 특수문자 대응
    cursorclass=pymysql.cursors.DictCursor  # 결과를 dict로 받기
)

# SQL을 실행하는 커서 객체 생성
cursor = conn.cursor()

persist_dir = "./data/mysql_vector_store"

if not os.path.exists(persist_dir):
    print("🚀 벡터 DB가 없으니 새로 생성합니다.")

    # SQL 실행
    cursor.execute('SELECT name, original_price, discount_percent, sale_price FROM book')
    book_rows = cursor.fetchall()

    cursor.execute('SELECT title, content FROM notice')
    notice_rows = cursor.fetchall()

    cursor.execute('SELECT title, content FROM faq')
    faq_rows = cursor.fetchall()

    # 리스트로 문서 구성
    documents = []
    for row in book_rows:
        name = row['name']
        original_price = row['original_price']
        discount_percent = row['discount_percent']
        sale_price = row['sale_price']
        documents.append(f"도서명: {name}, 원가: {original_price}, 할인율: {discount_percent}, 판매가: {sale_price}")

    for row in notice_rows:
        title = row['title']
        content = row['content']
        documents.append(f"공지사항 제목: {title}, 공지사항 본문: {content}")

    for row in faq_rows:
        title = row['title']
        content = row['content']
        documents.append(f"FAQ 제목: {title}, FAQ 본문: {content}")

    # Document 객체로 변환
    doc_objs = [Document(page_content=doc) for doc in documents]

    # Document 객체 짧은 단위로 분할
    text_splitter = RecursiveCharacterTextSplitter(
        chunk_size=300,
        chunk_overlap=30
    )
    docs = text_splitter.split_documents(doc_objs)

    # 임베딩 준비
    embeddings = UpstageEmbeddings(
        api_key=os.getenv('UPSTAGE_API_KEY'),
        model='solar-embedding-1-large'
    )

    # 문서 저장
    vector_store = Chroma.from_documents(
        documents=docs,
        embedding=embeddings,
        persist_directory=persist_dir
    )
else:
    print("✅ 기존 벡터 DB가 있으니 로드합니다.")

    embeddings = UpstageEmbeddings(
        api_key=os.getenv('UPSTAGE_API_KEY'),
        model='solar-embedding-1-large'
    )

    vector_store = Chroma(
        persist_directory=persist_dir,
        embedding_function=embeddings
    )

# retriever 만들기
retriever = vector_store.as_retriever(
    search_type='mmr',
    search_kwargs={"k": 5}
)

# LLM을 위한 모델 준비
llm = ChatUpstage(
    api_key=os.getenv('UPSTAGE_API_KEY'),
    model='solar-pro'
)

# 프롬프트 양식 정의
prompt_template = PromptTemplate.from_template(
    """
    당신은 "We Read Up"이라는 온라인 도서 쇼핑몰 챗봇입니다.

    ## 사이트 정보:
    - We Read Up은 다양한 도서를 판매하는 쇼핑몰입니다.
    - 주요 상품은 건강, 여행, 컴퓨터 등 다양한 도서입니다.

    ## 문맥 정보 사용 지침:
    - 아래 "관련 문맥"에는 도서 이름, 가격, FAQ 내용 등이 담겨 있습니다.
    - 사용자의 질문에 대해 관련 문맥에서 적절한 정보를 찾아 구체적으로 답변하세요.
    - 문맥에 관련된 내용이 있을 경우, 반드시 그 내용을 활용하여 답변을 작성하세요.
    - 문맥에 관련된 내용이 없을 경우, 사용자와 이전에 주고받은 대화, 문맥을 사용하지 말고 답변하세요.
    - 답변은 간결하고, 자연스러운 문장으로 정리하세요.
    
    ## 응답:
    - 책을 알려줄 때는 책의 이름, 원가, 할인율, 판매가 순으로 답변하세요.
    - 응답은 500자 이하로 답변하세요.

    ## 사용자 질문
    {question}

    ## 문맥
    {context}

    ## 사용자와 이전에 주고받은 대화
    {history}
    """
)

chain = prompt_template | llm | StrOutputParser()


# user_id를 통해 채팅 내역 조회
def get_chat_history(chat: Chat):
    # 비로그인 사용자 채팅 내역 조회
    if chat.userId is None:
        sql = """
            SELECT sender, message
            FROM chat
            WHERE user_id IS NULL AND uuid = %s
            ORDER BY reg_date DESC
            LIMIT 10
        """
        cursor.execute(sql, chat.uuid)
    # 로그인 사용자 채팅 내역 조회
    else:
        sql = """
            SELECT sender, message
            FROM chat
            WHERE user_id = %s
            ORDER BY reg_date DESC
            LIMIT 10
        """
        cursor.execute(sql, chat.userId)

    chats = cursor.fetchall()
    history = "\n".join([f"[{chat['sender'].upper()}] {chat['message']}" for chat in chats])
    print(f'채팅 내역 : {history}')

    return history


@router.post("/chat")
def receive_data(chat: Chat):
    print(f'사용자 채팅 === sender : {chat.sender}, message : {chat.message} ===')

    context_docs = retriever.invoke(chat.message)
    unique_contexts = list(set(doc.page_content for doc in context_docs))
    context = "\n\n".join(unique_contexts)
    print(f'context : {context}')

    # 해당 사용자의 채팅 내역 조회
    history = get_chat_history(chat)

    answer = chain.invoke({
        'question': chat.message,
        'context': context,
        'history': history
    })
    print(f'LLM 응답 === {answer} ===')

    return {
        "userId": chat.userId,
        "uuid": chat.uuid,
        "sender": "bot",
        "message": answer,
        "regDate": datetime.now().isoformat()
    }
