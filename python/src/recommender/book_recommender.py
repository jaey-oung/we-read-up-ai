# ========================= #
# JS → Python 통신용 추천 API
# ========================= #

import pymysql
import os
from dotenv import load_dotenv
from fastapi import APIRouter
from typing import List, Dict
from recommender.book_model import Book
from recommender.mbti_request_model import MbtiRequest
from recommender.book_recommender_core import get_top_book_ids

load_dotenv()

router = APIRouter(
    prefix='/book',
    tags=['book']
)

# 도서 ID 리스트를 받아서 DB에서 상세 정보 조회
def get_books_by_ids(book_ids: List[int]) -> List[Dict]:
    connection = pymysql.connect(
        host=os.getenv("DB_HOST"),
        user=os.getenv("DB_USER"),
        password=os.getenv("DB_PASSWORD"),
        db=os.getenv("DB_NAME"),
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
    )

    try:
        with connection.cursor() as cursor:
            format_strings = ','.join(['%s'] * len(book_ids))
            sql = f"""
                SELECT 
                    b.book_id,
                    b.name AS title,
                    b.image,
                    b.description,
                    w.name AS author
                FROM book b
                JOIN writer_book wb ON b.book_id = wb.book_id
                JOIN writer w ON wb.writer_id = w.writer_id
                WHERE b.book_id IN ({format_strings})
            """
            cursor.execute(sql, tuple(book_ids))
            return cursor.fetchall()
    finally:
        connection.close()

# [POST] 사용자의 MBTI 점수를 받아 도서 추천 결과 반환
@router.post("/recommend", response_model=List[Book])
def recommend_books(data: MbtiRequest):
    user_mbti = data.scores
    top_book_ids = get_top_book_ids(user_mbti)

    book_infos = get_books_by_ids(top_book_ids)

    id_to_book = {book["book_id"]: book for book in book_infos}
    sorted_books = [id_to_book[book_id] for book_id in top_book_ids if book_id in id_to_book]

    return [
        Book(
            book_id=book["book_id"],
            title=book["title"],
            author=book["author"],
            image=book["image"],
            description=book["description"]
        ) for book in sorted_books
    ]