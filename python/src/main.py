from chat.chat import router as chat_router
from recommender.book_recommender import router as book_recommendation_router
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
# - /book → recommender/book_recommender.py (도서 추천)
app.include_router(chat_router)
app.include_router(book_recommendation_router)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="127.0.0.1", port=8000, reload=True)