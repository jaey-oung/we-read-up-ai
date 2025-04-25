from chat.chat import router as chat_router
from recommender.book_recommender import router as book_recommendation_router
from recommender.embedding_average import router as embedding_average_router
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
app = FastAPI()

# CORS 설정: http://localhost:8080 허용
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:8080"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# 포함된 라우터:
# - /chat → chat/chat.py (챗봇)
# - /book/recomend → recommender/book_recommender.py (도서 추천)
# - /embedding/average → recommender/embedding_average.py (도서들의 평균 임베딩 점수 계산)

app.include_router(chat_router)
app.include_router(book_recommendation_router)
app.include_router(embedding_average_router)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="127.0.0.1", port=8000, reload=True)