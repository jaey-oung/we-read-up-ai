import random
from datetime import datetime
import os
import torch
import pandas as pd
import numpy as np
from sklearn.preprocessing import StandardScaler
from transformers import AutoTokenizer, PreTrainedTokenizerFast, BartForConditionalGeneration
from model.mbtimlp import MBTIModel
import matplotlib.pyplot as plt
import matplotlib
from sklearn.decomposition import PCA
from tqdm import tqdm
import re
import json

# ===== 유저 MBTI와 책 MBTI의 거리를 비교하여 책을 추천해주는 클래스 ===== #

# ===== 현재 시간 출력 함수 ===== #
def currentTime():
    return datetime.now().strftime("%Y-%m-%d %H:%M:%S")

# ===== 텍스트 정제 ===== #
def clean_book_intro(text: str) -> str:
    if not isinstance(text, str): return ''
    text = re.sub(r'[^\w\s.,!?가-힣a-zA-Z]', '', text)
    text = re.sub(r'<.*?>', '', text)
    text = re.sub(r'\(.*?\)|\[.*?\]', '', text)
    text = re.sub(r'http[s]?://\S+|www\.\S+', '', text)
    text = re.sub(r'\s+', ' ', text).strip()
    return text

# ===== 거리 계산 ===== #
def mbti_distance(user_scores, doc_scores):
    keys = user_scores.keys()
    u = np.array([user_scores[k] for k in keys])
    d = np.array([doc_scores.get(k, 50) for k in keys])
    return np.linalg.norm(u - d) / np.linalg.norm(np.array([100]*8)) * 100

# ===== 요약 함수 ===== #
def batch_summarize_kobart(text_list, batch_size=32):
    results = []
    for i in tqdm(range(0, len(text_list), batch_size)):
        batch = text_list[i:i+batch_size]
        inputs = kobart_tokenizer(batch, return_tensors="pt", padding=True, truncation=True, max_length=512).to(device)
        with torch.inference_mode():
            summary_ids = kobart_model.generate(
                input_ids=inputs['input_ids'],
                attention_mask=inputs['attention_mask'],
                max_length=50,
                min_length=20,
                num_beams=1,
                do_sample=False
            )
        results.extend(kobart_tokenizer.batch_decode(summary_ids, skip_special_tokens=True))
    return results

# ===== MBTI 배치 예측 함수 ===== #
def predict_mbti_batch(texts, model, tokenizer, device, batch_size=16):
    results = []
    model.eval()
    for i in tqdm(range(0, len(texts), batch_size), desc="MBTI 예측 중"):
        batch = texts[i:i+batch_size]
        inputs = tokenizer(batch, return_tensors='pt', padding=True, truncation=True, max_length=512).to(device)
        inputs.pop('token_type_ids', None)  # ✅ 요 라인 추가
        with torch.no_grad():
            outputs = model(**inputs).cpu()
        results.extend(outputs.tolist())
    return results

# ===== 경로 및 파일 설정 ===== #
dest = os.path.join('data/cache')
DATA_PATH = 'data/raw/merged_results.json'
CACHE_DATA_PATH = 'data/cache/paragraph_cached_data.json'
CACHE_EMB_PATH = 'data/cache/paragraph_embeddings.pt'

# 그래픽카드가 있으면 cuda로, 없으면 cpu로 사용
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
print(f"[{currentTime()}] 사용 중인 디바이스: {device}")

# ===== 모델 로딩 ===== #
print(f"[{currentTime()}] 모델 로딩 중...")
tokenizer = AutoTokenizer.from_pretrained("jhgan/ko-sroberta-multitask")
model = MBTIModel().to(device)
model.load_state_dict(torch.load(os.path.join(dest, 'mbti_model.pt'), map_location=device))
model.eval()
print(f"[{currentTime()}] 모델 로딩 완료")

# ===== KoBART 요약 모델 ===== #
print(f"[{currentTime()}] 요약 모델 로딩 중...")
kobart_tokenizer = PreTrainedTokenizerFast.from_pretrained("digit82/kobart-summarization")
kobart_model = BartForConditionalGeneration.from_pretrained("digit82/kobart-summarization").to(device)
print(f"[{currentTime()}] 요약 모델 로딩 완료")

# ===== 데이터 로딩 ===== #
if os.path.exists(CACHE_DATA_PATH):
    print(f"[{currentTime()}] 책 소개 캐시 불러오는 중...")
    df_para = pd.read_json(CACHE_DATA_PATH)
    print(f"[{currentTime()}] 캐시 불러오기 완료")
else:
    print(f"[{currentTime()}] 원본 파일 로딩 시작")
    df = pd.read_json(DATA_PATH)
    print(f"[{currentTime()}] 원본 파일 로딩 완료")

    df['title'] = df['title'].astype(str)
    df['book_intro'] = df['book_intro'].astype(str)
    df = df[~df['book_intro'].str.contains('책 소개 정보')]

    # 누락된 값이 있는 행 삭제
    df.dropna(axis=0)

    # 패턴 처리
    df['book_intro'] = df['book_intro'].apply(clean_book_intro)
    print(f"[{currentTime()}] 책 소개 추출 시작...")

    data = []
    for _, row in df.iterrows():
        intro = row['book_intro'].strip()
        if intro:
            data.append({'title': row['title'], 'paragraph': intro})
    df_para = pd.DataFrame(data)

    # 중복 제거
    df_para = df_para.drop_duplicates(subset='title', keep='first').reset_index(drop=True)

    # ===== 책 소개문 요약 적용 ===== #
    # print(f"[{currentTime()}] 책 소개 요약 시작...")
    # tqdm.pandas()
    # df_para['paragraph'] = batch_summarize_kobart(df_para['paragraph'].tolist(), batch_size=32)
    # print(f"[{currentTime()}] 책 소개 요약 완료")

    df_para.to_json(CACHE_DATA_PATH, force_ascii=False, indent=2)
    print(f"[{currentTime()}] 책 소개 캐시 저장 완료")

# ===== 사용자 MBTI 점수 입력 (총합 100점씩) ===== #
s = random.randint(1, 100)
f = random.randint(1, 100)
n = random.randint(1, 100)
q = random.randint(1, 100)

user_mbti_score = {
    'S': s, 'I': 100-s,
    'F': f, 'D': 100-f,
    'N': n, 'M': 100-n,
    'Q': q, 'A': 100-q
    # 'S': 42, 'I': 58, 'F': 18, 'D': 82, 'N': 63, 'M': 37, 'Q': 78, 'A': 22
}

# ===== 예측 ===== #
print(f"[{currentTime()}] 예측 시작...")
raw_dataset = []  # 임베딩만 저장되는 원본 데이터
CACHE_KEYS = ['S','I','F','D','N','M','Q','A']

if os.path.exists(CACHE_EMB_PATH):
    print(f"[{currentTime()}] 임베딩 캐시 불러오는 중...")
    with open(CACHE_EMB_PATH, 'r', encoding='utf-8') as f:
        raw_dataset = json.load(f)
    print(f"[{currentTime()}] 캐시 불러오기 완료")
else:
    print(f"[{currentTime()}] 임베딩 시작...")
    mbti_results = predict_mbti_batch(df_para['paragraph'].tolist(), model, tokenizer, device)
    for i, row in df_para.iterrows():
        mbti_tensor = mbti_results[i]
        mbti_score = {k: float(v * 100) for k, v in zip(CACHE_KEYS, mbti_tensor)}
        raw_dataset.append({
            "title": row["title"],
            "paragraph": row["paragraph"],
            "mbti_score": mbti_score
        })
    with open(CACHE_EMB_PATH, 'w', encoding='utf-8') as f:
        json.dump(raw_dataset, f, ensure_ascii=False, indent=2)
    print(f"[{currentTime()}] 임베딩 종료...")

# ===== 유저 MBTI 점수 기준 거리 계산 ===== #
dataset = []
for item in raw_dataset:
    dist = mbti_distance(user_mbti_score, item["mbti_score"])
    dataset.append((dist, item["title"], item["paragraph"], item["mbti_score"]))

# ===== 상위 5개 출력 ===== #
dataset.sort(key=lambda x: x[0])
print(f"User MBTI 점수: {user_mbti_score}")
for dist, title, para, mbti in dataset[:5]:
    print(f"\n제목: {title}\n거리: {dist:.2f}%\n문단: {para}...\nMBTI 점수: {mbti}")
    print("ㅡ" * 80)

# # 간단한 샘플 예측 결과 보기
# sample_texts = df_para['paragraph'].tolist()[:5]
# sample_preds = predict_mbti_batch(sample_texts, model, tokenizer, device)
#
# for i, pred in enumerate(sample_preds):
#     print(f"\n[{i+1}번째 문장 예측 결과]")
#     print("문장:", sample_texts[i])
#     print("MBTI 예측:", {k: round(v * 100, 2) for k, v in zip(['S','I','F','D','N','M','Q','A'], pred)})

# 한글 폰트 설정 (예: 맑은 고딕)
plt.rcParams['font.family'] = 'Malgun Gothic'  # Windows용
# plt.rcParams['font.family'] = 'AppleGothic'  # macOS용
# plt.rcParams['font.family'] = 'NanumGothic'  # Linux용 (nanum 설치 필요)
matplotlib.use("TkAgg")

# ===== MBTI 점수들 → 2차원 PCA =====
book_mbti_vectors = [list(mbti.values()) for _, _, _, mbti in dataset]
book_titles = [title for _, title, _, _ in dataset]

pca = PCA(n_components=2)
book_pca_coords = pca.fit_transform(book_mbti_vectors)

# ===== 사용자 MBTI 점수도 PCA로 변환 =====
user_vector = np.array([user_mbti_score[k] for k in ['S','I','F','D','N','M','Q','A']])
user_pca_coord = pca.transform([user_vector])[0]

# ===== 산점도 시각화 =====
plt.figure(figsize=(12, 8))
plt.scatter(book_pca_coords[:, 0], book_pca_coords[:, 1], alpha=0.6, label='도서', edgecolors='k')

# 가장 가까운 도서 5개는 텍스트로 출력
for i in range(5):
    dist, title, _, _ = dataset[i]
    x, y = book_pca_coords[i]
    plt.scatter(x, y, color='red', label='추천 도서')
    plt.text(x + 1, y + 1, title[:15], fontsize=9)

# 사용자 MBTI 좌표 표시
plt.scatter(user_pca_coord[0], user_pca_coord[1], color='blue', marker='*', s=200, label='사용자 MBTI')
plt.text(user_pca_coord[0]+1, user_pca_coord[1]+1, '사용자', fontsize=10, color='blue')

plt.title("MBTI 기반 도서 추천 분포 (PCA 2D)")
plt.xlabel("PCA1")
plt.ylabel("PCA2")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.show()



# 3D
# Z-score 정규화 + 3D PCA
scaler = StandardScaler()
book_mbti_scaled = scaler.fit_transform(book_mbti_vectors)

pca = PCA(n_components=3)
book_pca_coords = pca.fit_transform(book_mbti_scaled)

# 사용자도 정규화 후 PCA
user_vector = np.array([user_mbti_score[k] for k in ['S','I','F','D','N','M','Q','A']])
user_vector_scaled = scaler.transform([user_vector])
user_pca_coord = pca.transform(user_vector_scaled)[0]


fig = plt.figure(figsize=(14, 10))
ax = fig.add_subplot(111, projection='3d')

# 전체 도서 산점도
ax.scatter(book_pca_coords[:, 0], book_pca_coords[:, 1], book_pca_coords[:, 2], alpha=0.5, label='도서', edgecolors='k')

# 사용자
ax.scatter(user_pca_coord[0], user_pca_coord[1], user_pca_coord[2], color='blue', marker='*', s=200, label='사용자 MBTI')

# 추천 도서 (상위 5개)
for i in range(5):
    dist, title, _, _ = dataset[i]
    x, y, z = book_pca_coords[i]
    ax.scatter(x, y, z, color='red', label='추천 도서' if i == 0 else "")
    ax.text(x + 1, y + 1, z + 1, title[:15], fontsize=8)

ax.set_title("MBTI 기반 도서 추천 분포 (PCA 3D)")
ax.set_xlabel("PCA1")
ax.set_ylabel("PCA2")
ax.set_zlabel("PCA3")
ax.legend()
plt.tight_layout()
plt.show()