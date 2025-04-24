import json
import numpy as np
from typing import List, Dict
from pathlib import Path

# 캐시된 임베딩 결과 파일
BASE_DIR = Path(__file__).resolve().parents[2]
CACHE_EMB_PATH = BASE_DIR / "data/cache/paragraph_embeddings.pt"

# 유클리드 거리 기반 MBTI 점수 간 유사도 계산 (낮을수록 유사도가 높음)
def mbti_distance(user_scores: Dict[str, float], book_scores: Dict[str, float]) -> float:
    keys = user_scores.keys()
    u = np.array([user_scores[k] for k in keys])
    d = np.array([book_scores.get(k, 50) for k in keys])
    return np.linalg.norm(u - d) / np.linalg.norm(np.array([100]*8)) * 100

# 사용자 MBTI 점수를 받아 가장 유사한 도서 ID 상위 K개 반환
# 임베딩 캐시 파일(.pt)에서 각 도서와 거리 계산 후 정렬
def get_top_book_ids(user_mbti: Dict[str, float], top_k: int = 5) -> List[int]:
    if not CACHE_EMB_PATH.exists():
        raise FileNotFoundError(f"캐시 파일이 없습니다: {CACHE_EMB_PATH}")

    with open(CACHE_EMB_PATH, "r", encoding="utf-8") as f:
        raw_dataset = json.load(f)

    scored = []
    for item in raw_dataset:
        dist = mbti_distance(user_mbti, item["mbti_score"])
        scored.append((dist, item["book_id"]))

    scored.sort(key=lambda x: x[0])
    return [book_id for _, book_id in scored[:top_k]]