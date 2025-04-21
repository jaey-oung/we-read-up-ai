import json
from collections import Counter

# 파일 경로
DATA_PATH = '../data/processed/book_mbti_sample_data.json'

# 기준값
EXTREME_THRESHOLD = 90

# JSON 로딩
with open(DATA_PATH, 'r', encoding='utf-8') as f:
    data = json.load(f)

# 극단값과 일반값 분리
extreme_samples = []
normal_samples = []
extreme_feature_count = Counter()

# 수정된 항목 기록용
normalization_logs = []

def normalize_pair(d, key1, key2):
    v1, v2 = d.get(key1, 0), d.get(key2, 0)
    total = v1 + v2
    if total == 0:
        return 50, 50  # 둘 다 0이면 균등 분할
    elif total == 100:
        return v1, v2
    else:
        ratio1 = v1 / total
        ratio2 = v2 / total
        new_v1 = round(ratio1 * 100)
        new_v2 = 100 - new_v1  # 소수 반올림 보정
        return new_v1, new_v2

# 전체 데이터 순회
for idx, item in enumerate(data):
    score_dict = item.get("scores", item.get("label", {}))

    # 원본 값 저장
    original = score_dict.copy()

    # 정규화 수행
    s, i = normalize_pair(score_dict, "S", "I")
    f, d = normalize_pair(score_dict, "F", "D")
    n, m = normalize_pair(score_dict, "N", "M")
    q, a = normalize_pair(score_dict, "Q", "A")

    # 업데이트
    score_dict.update({"S": s, "I": i, "F": f, "D": d, "N": n, "M": m, "Q": q, "A": a})

    # 수정사항 기록
    if original != score_dict:
        normalization_logs.append({
            "index": idx,
            "original": original,
            "normalized": score_dict.copy()
        })

    # 극단값 분류
    extreme_keys = [k for k, v in score_dict.items() if v >= EXTREME_THRESHOLD]
    if extreme_keys:
        extreme_samples.append(item)
        extreme_feature_count.update(extreme_keys)
    else:
        normal_samples.append(item)

# 정리된 순서: 일반값 → 극단값
output_data = normal_samples + extreme_samples

# 덮어쓰기 저장
with open(DATA_PATH, 'w', encoding='utf-8') as f:
    json.dump(output_data, f, ensure_ascii=False, indent=2)

# 결과 출력
print(f"✅ 정리 완료: 일반값 {len(normal_samples)}건 / 극단값 {len(extreme_samples)}건")
print(f"📊 극단값 기준 항목 분포 (기준: {EXTREME_THRESHOLD} 이상):")
for k, v in extreme_feature_count.most_common():
    print(f"  - {k}: {v}건")

# 정규화 로그 출력
if normalization_logs:
    print(f"\n🔧 총 {len(normalization_logs)}건의 데이터에서 합이 100이 되도록 정규화 되었습니다:")
else:
    print("\n✅ 모든 데이터가 이미 S+I, F+D, N+M, Q+A 합 100을 만족합니다.")
