from transformers import AutoModel
import torch.nn as nn

# ===== 모델 정의 클래스 ===== #
class MBTIModel(nn.Module):
    def __init__(self, pretrained_model_name='jhgan/ko-sroberta-multitask'):
        super().__init__()
        self.bert = AutoModel.from_pretrained(pretrained_model_name)
        self.classifier = nn.Sequential(
            nn.Linear(self.bert.config.hidden_size, 256),
            nn.ReLU(),
            nn.Linear(256, 128),
            nn.ReLU(),
            nn.Linear(128, 8),
            nn.Tanh()
        )

    def forward(self, input_ids, attention_mask):
        outputs = self.bert(input_ids=input_ids, attention_mask=attention_mask)
        cls_embedding = outputs.last_hidden_state[:, 0, :]  # [CLS] 토큰
        return self.classifier(cls_embedding)