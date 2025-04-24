from pydantic import BaseModel

# 추천 결과로 전달되는 도서 정보
class Book(BaseModel):
    book_id: int
    title: str
    author: str
    image: str
    description: str