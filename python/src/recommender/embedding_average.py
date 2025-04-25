from fastapi import APIRouter
from fastapi import HTTPException
from typing import Dict

from recommender.book_id_list_request_model import BookIdListRequest
from recommender.embedding_response_model import EmbeddingResponse
from recommender.book_recommender_core import get_average_embedding

router = APIRouter(
    prefix="/embedding",
    tags=["embedding"]
)

# 도서 ID 목록을 받아 해당 도서들의 평균 임베딩 점수를 계산
@router.post("/average", response_model=EmbeddingResponse)
def average_embedding(data: BookIdListRequest) -> Dict[str, float]:
    try:
        return get_average_embedding(data.book_ids)
    except FileNotFoundError as e:
        raise HTTPException(status_code=500, detail=str(e))
    except ValueError as e:
        raise HTTPException(status_code=404, detail=str(e))
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"예상치 못한 에러: {str(e)}")