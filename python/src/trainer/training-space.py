from datetime import datetime

from sklearn.model_selection import train_test_split
from transformers import AutoTokenizer
from torch.utils.data import Dataset, DataLoader
from model.mbtimlp import MBTIModel
import torch
import torch.nn as nn
import json
import numpy as np
import os
from tqdm import tqdm

# ===== AI 모델 훈련 클래스 CPU로 실행시 10시간 소요되니 주의!!!!!!!!! ===== #

# ===== 현재 시간 출력 함수 ===== #
def currentTime():
    return datetime.now().strftime("%Y-%m-%d %H:%M:%S")


class MBTIDataset(Dataset):
    def __init__(self, texts, labels, tokenizer, max_length=512):
        self.texts = texts
        self.labels = labels
        self.tokenizer = tokenizer
        self.max_length = max_length

    def __len__(self):
        return len(self.texts)

    def __getitem__(self, idx):
        encoded = self.tokenizer(self.texts[idx], padding='max_length',
                                 truncation=True, max_length=256, return_tensors='pt')
        return {
            'input_ids': encoded['input_ids'].squeeze(),
            'attention_mask': encoded['attention_mask'].squeeze(),
            'labels': torch.tensor(self.labels[idx], dtype=torch.float32)
        }

# 데이터 불러오기
print("훈련 데이터 파일 로드 중")
with open('../../data/processed/book_mbti_sample_data.json', 'r', encoding='utf-8') as f:
    data = json.load(f)
texts = [entry['text'] for entry in data]
labels = [list(entry['label'].values()) for entry in data]
labels = np.array(labels, dtype=np.float32) / 100.0
print("훈련 데이터 파일 로드 종료")

print("토크나이저 설정중")
# tokenizer 설정
tokenizer = AutoTokenizer.from_pretrained("jhgan/ko-sroberta-multitask")
print("토크나이저 설정 종료")
print("훈련 데이터 로드 중")
# Dataset & DataLoader
train_texts, val_texts, train_labels, val_labels = train_test_split(texts, labels, test_size=0.3)
train_ds = MBTIDataset(train_texts, train_labels, tokenizer)
val_ds = MBTIDataset(val_texts, val_labels, tokenizer)

train_loader = DataLoader(train_ds, batch_size=16, shuffle=True)
val_loader = DataLoader(val_ds, batch_size=8)
print("훈련 데이터 로드 종료")

# 모델, 옵티마이저, 손실함수
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
model = MBTIModel().to(device)
# 학습률
optimizer = torch.optim.Adam(model.parameters(), lr=5e-5)
criterion = nn.MSELoss()

print("Train dataset 개수:", len(train_loader))
print("Train dataset 길이:", len(train_ds))

# 학습 루프
print("훈련시작")
for epoch in range(30):
    model.train()
    total_loss = 0
    print(f"\n📢 [Epoch {epoch+1}] 시작")

    # tqdm으로 학습 상태 표시
    progress_bar = tqdm(train_loader, desc=f"Epoch {epoch}", leave=False)

    for batch in progress_bar:
        input_ids = batch['input_ids'].to(device)
        attention_mask = batch['attention_mask'].to(device)
        labels = batch['labels'].to(device)

        optimizer.zero_grad()
        outputs = model(input_ids, attention_mask)
        loss = criterion(outputs, labels)
        loss.backward()
        optimizer.step()
        total_loss += loss.item()

        # 현재 배치 손실을 표시
        progress_bar.set_postfix(loss=loss.item())

    print(f"✅ [Epoch {epoch+1}] 평균 Loss: {total_loss:.4f}")

print("캐시 저장 파일 생성중")
    # 저장 폴더 생성
dest = os.path.join('../../data/cache')
if not os.path.exists(dest):
    os.makedirs(dest)

# 훈련된 모델 저장
torch.save(model.state_dict(), os.path.join(dest, 'mbti_model.pt'))
print("캐시 저장 완료")
