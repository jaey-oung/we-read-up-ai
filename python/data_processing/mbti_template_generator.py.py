import json

SAVE_PATH = "../data/processed/book_mbti_sample_data.json"

traits_pairs = [('S', 'I'), ('F', 'D'), ('N', 'M'), ('Q', 'A')]

result = []
for trait in range(0, 4):
    for i in range(0, 101, 10):
        for j in range(0, 101, 10):
            label = {}
            if trait == 0 and i + j == 100:
                label = {
                    "S": i,
                    "I": j,
                    "F": 0,
                    "D": 0,
                    "N": 0,
                    "M": 0,
                    "Q": 0,
                    "A": 0
                }
            elif trait == 1 and i + j == 100:
                label = {
                    "S": 0,
                    "I": 0,
                    "F": i,
                    "D": j,
                    "N": 0,
                    "M": 0,
                    "Q": 0,
                    "A": 0
                }
            elif trait == 2 and i + j == 100:
                label = {
                    "S": 0,
                    "I": 0,
                    "F": 0,
                    "D": 0,
                    "N": i,
                    "M": j,
                    "Q": 0,
                    "A": 0
                }
            elif trait == 3 and i + j == 100:
                label = {
                    "S": 0,
                    "I": 0,
                    "F": 0,
                    "D": 0,
                    "N": 0,
                    "M": 0,
                    "Q": i,
                    "A": j
                }

            if i + j == 100:
                for a in range(0, 10):
                    result.append({
                        "text": "",
                        "label": label
                    })

with open(SAVE_PATH, 'w', encoding='utf-8') as f:
    json.dump(result, f, ensure_ascii=False, indent=2)