from pydantic import BaseModel
from typing import List

# 요청 모델: 도서 ID 리스트
class BookIdListRequest(BaseModel):
    book_ids: List[int]