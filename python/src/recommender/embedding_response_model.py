from pydantic import BaseModel

# 응답 모델: 평균 임베딩 점수
class EmbeddingResponse(BaseModel):
    S: float
    I: float
    F: float
    D: float
    N: float
    M: float
    Q: float
    A: float