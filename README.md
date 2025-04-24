# We Read Up AI

## 1️⃣ 프로젝트 개요
- **도서 추천 서비스 :** 도서 소개 문장을 입력받아 MBTI 8가지 성향 점수를 예측하고, 이를 바탕으로 사용자와 유사한 성향의 책을 추천하는 MLP 기반 추천 시스템입니다.
- **LLM 활용 챗봇 :** 저장된 정보를 기반으로 사용자 질문에 LLM이 히스토리를 반영한 자연스러운 응답을 비동기로 제공하는 챗봇 시스템입니다.
- **리팩토링 :** 예외 상황에 대한 안정성을 높이기 위해 예외처리를 강화하고, 운영 가시성을 위해 로그를 추가했으며, 사용자 편의를 위해 카테고리 메뉴 화면을 개선하는 리팩토링을 진행했습니다.



<br><br>
## 2️⃣ 기술 스택
### 🔧 자바 백엔드
- **Java 21**
- **Spring Framework** (Spring Boot, Spring MVC, Spring JDBC)
- **Gradle** (빌드 도구)
- **MySQL** (데이터베이스)
- **MyBatis** (ORM)
- **Tomcat** (서버)

### 🧠 인공지능 / 추천 시스템
- **Python 3.11 / 3.12**
- **FastAPI** (AI 서버)
- **Chroma DB** (벡터 데이터베이스)
- **Upstage AI** (LLM)
- **Hugging Face** (자연어 처리)

### 🔗 기타
- **WebClient** (서버 간 통신)
- **IntelliJ IDEA** (개발 환경)



<br><br>
## 3️⃣ 프로젝트 설정

### 1. 기본 설정

- `application.properties`과 `.env` 파일에서 데이터베이스 설정과 API_KEY를 확인하고 환경에 맞게 변경하세요.

#### 📄 application.properties
```
spring.application.name=we-read-up

spring.datasource.url=jdbc:mysql://localhost:3306/book_store?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.type-aliases-package=java.lang, com.wru.wrubookstore.dto, com.wru.wrubookstore.domain

server.servlet.session.tracking-modes=cookie
```

#### 📄 .env
```
UPSTAGE_API_KEY=

DB_HOST=localhost
DB_PORT=3306
DB_USER=
DB_PASSWORD=
DB_NAME=book_store
```

### 2. 검색 기반 응답 시스템 및 개인화 추천 시스템의 핵심 데이터 저장소(data)
`data` 폴더를 `python`폴더 아래에 추가한다.
`data` 폴더에는 Chroma 벡터 DB와 함께, 학습된 PyTorch 모델 파일(.pt)과 구성 정보 파일(.json) 등이 포함되어 있으며, 각각 다음과 같은 역할을 합니다.

#### 📂 `mysql_vector_store/`
- **Chroma 벡터 DB**가 저장되는 폴더입니다.
- 임베딩된 도서, FAQ, 공지사항 등의 정보를 벡터로 저장하고, 사용자 질문과의 유사도 기반 검색에 활용됩니다.
- **Retrieval-Augmented Generation(RAG)** 시스템에서 핵심 역할을 합니다.

#### 📄 `*.pt`
- 학습된 **PyTorch 모델 파일**입니다.
- 예: `mbti_model.pt`
- 도서 소개 문장에서 MBTI 8가지 성향 점수를 예측하기 위한 다층 퍼셉트론(MLP) 기반 모델의 학습된 가중치가 저장되어 있습니다.
- 사용자 성향과 책의 성향을 비교해 유사한 도서를 추천할 때 사용됩니다.

#### 📄 `*.json`
- 모델 구성 또는 벡터 DB 항목의 메타데이터를 포함한 **설정 파일**입니다.
- 예: 임베딩 시 사용된 메타 정보, 필드 구조, 설정 값 등이 포함되어 있어 추론 또는 재사용 시 참조됩니다.



<br><br>
## 4️⃣ 구현 내용 및 기술 흐름

### 도서 기반 추천 서비스
#### 1. 🌐 **크롤링**
   - **크롤링 구조**: 여러 도서 사이트의 공통 도서를 크롤링하고, 개별 사이트의 구조를 유지.
   - **효율적인 데이터 수집**: 다양한 도서 사이트에서 데이터를 추출하여 일관성 있게 관리.
   - **도서 정보 수집**: 알라딘 API를 통해 도서 정보를 조회하고 수집.
   - **조회 제한 관리**: 전체 도서 리스트 조회에 제한이 있어, 국립중앙도서관 데이터셋을 활용하여 보완.

#### 2. 📚 **도서 성향 구성**
   - **성향 추론**: 도서의 문체와 메시지를 통해 독자의 성향을 예측.
   - **독서와 성향**: 독서는 개인의 성격과 연결되며, 이를 바탕으로 4개의 성향 축을 설정해 16가지 성격 유형을 생성.

#### 3. 🤖 **모델 개발**
   - **MBTI 모델 설계**: BERT 모델을 이용해 문장을 임베딩하고, MLP 회귀 모델로 8가지 MBTI 지표를 예측.

#### 4. 🧠 **훈련 시스템 구성**
   - **training-space 클래스**: MSE 손실 함수와 Adam 옵티마이저를 사용해 모델 훈련, 역전파를 통해 성능 개선.

#### 5. 💡 **추천 시스템 구현**
   - **prediction-space 클래스**: 도서 정보를 정제하고 KoBART로 요약하며, 임베딩 캐싱으로 효율성 향상.
   - **유사도 기반 추천**: YCTIModel의 예측 결과로 유클리드 거리를 계산하여 유사한 도서 추천.

<br>

### LLM 활용 챗봇
#### 1. 📊 데이터 수집 및 임베딩
- FastAPI 서버에서 MySQL DB에 접속하여 `book`, `faq`, `notice`, `chat` 테이블의 데이터를 조회합니다.
- 조회된 데이터는 LangChain의 문서 객체(`Document`)로 변환됩니다.
- 변환된 문서는 Chroma DB에 임베딩되어 벡터 형태로 저장됩니다.


#### 2. 🧳 Retriever 객체 생성
- Chroma DB를 기반으로 `Retriever` 객체를 생성합니다.
- 사용자의 질문과 유사한 정보를 빠르게 검색하기 위한 역할을 수행합니다.

#### 3. 💬 질문 응답 처리
- 사용자의 입력 메시지에 대해 `Retriever`를 통해 관련된 문서(Context)를 검색합니다.
- 검색된 Context와 질문을 함께 LLM(`solar-pro`)에게 전달하여 응답을 생성합니다.
- 이전 대화 히스토리(history)도 Context에 포함되어 더욱 자연스러운 대화 흐름을 유지합니다.

#### 4. 🔗 FastAPI - Spring Boot 연동
- Spring Boot 서버가 사용자의 메시지를 수신합니다.
- `WebClient`를 사용하여 해당 메시지를 FastAPI 서버로 비동기 요청합니다.
- FastAPI는 답변을 생성한 후, 다시 Spring Boot로 응답을 반환합니다.

#### 5. 📡 클라이언트 통신
- Spring Boot 서버는 클라이언트와 `AJAX` 방식으로 실시간 비동기 채팅을 처리합니다.
- 클라이언트는 챗봇과 자유롭게 대화할 수 있으며, 답변은 빠르게 응답됩니다.



<br><br>
## 5️⃣ 구현 사진
- ### 도서 추천 서비스



<br>

- ### 챗봇

