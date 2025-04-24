from typing import Dict
from pydantic import BaseModel

# 사용자로부터 전달받는 MBTI 점수
class MbtiRequest(BaseModel):
    scores: Dict[str, float]