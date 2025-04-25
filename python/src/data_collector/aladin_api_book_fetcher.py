# ✅ 사용법 요약
# isbn_list.json 파일과 아래 코드를 같은 폴더에 두세요.

# 코드 상단의 API_KEY에 본인의 알라딘 TTBKey를 입력하세요.

# 필요한 경우 START_INDEX, END_INDEX 값을 조정해서 원하는 범위만 조회할 수 있어요.

# 중간에 종료되더라도 progress.json을 기반으로 다음 실행 시 이어서 진행됩니다.


import json
import requests
import xml.etree.ElementTree as ET
import time
import os

API_KEY = "" # 본인의 TTBKey 입력
ISBN_LIST_PATH = "" # isbn_list 모음집
RESULT_PATH = "" # 도서 상세정보 json 저장 경로
PROGRESS_PATH = "" # isbn_list 중 진행 위치 저장, 재 시작시 참조
START_INDEX = 0 # 본인의 isbn 시작위치 
END_INDEX =  1 # 본인의 isbn 종료위치 
SLEEP_TIME = 1

if os.path.exists(PROGRESS_PATH):
    with open(PROGRESS_PATH, "r", encoding="utf-8") as f:
        progress = json.load(f)
        START_INDEX = progress.get("last_index", START_INDEX)

with open(ISBN_LIST_PATH, "r", encoding="utf-8") as f:
    isbn_list = json.load(f)

isbn_list = isbn_list[START_INDEX-1:END_INDEX]

if os.path.exists(RESULT_PATH):
    with open(RESULT_PATH, "r", encoding="utf-8") as f:
        results = json.load(f)
else:
    results = []

total = len(isbn_list)

for i, isbn in enumerate(isbn_list, start=START_INDEX):
    print(f"[{i+1}/{START_INDEX + total}] 요청 중: {isbn}")
    url = f"http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey={API_KEY}&itemIdType=ISBN&ItemId={isbn}&output=xml&Version=20131101&OptResult=ebookList,usedList,reviewList"
    try:
        response = requests.get(url)
        root = ET.fromstring(response.content)

        # 에러 응답인지 확인
        error_code = root.findtext(".//{http://aladin.co.kr/ttb/apiguide.aspx}errorCode")
        if error_code == "10":
            print("💥 일일 조회 기준 수를 초과했습니다. 중단합니다.")
            break

        # 네임스페이스 정의
        ns = {'ns': 'http://www.aladin.co.kr/ttb/apiguide.aspx'}
        item = root.find(".//ns:item", ns)
        data = None
        if item is not None:
            data = {
                "isbn": isbn,
                "title": item.findtext("ns:title", namespaces=ns),
                "author": item.findtext("ns:author", namespaces=ns),
                "pubDate": item.findtext("ns:pubDate", namespaces=ns),
                "description": item.findtext("ns:description", namespaces=ns),
                "priceSales": item.findtext("ns:priceSales", namespaces=ns),
                "priceStandard": item.findtext("ns:priceStandard", namespaces=ns),
                "cover": item.findtext("ns:cover", namespaces=ns),
                "categoryName": item.findtext("ns:categoryName", namespaces=ns),
                "publisher": item.findtext("ns:publisher", namespaces=ns),
                "itemPage": item.findtext(".//ns:subInfo/ns:itemPage", namespaces=ns)
            }
            results.append(data)
            with open(RESULT_PATH, "w", encoding="utf-8") as f:
                json.dump(results, f, ensure_ascii=False, indent=2)

        with open(PROGRESS_PATH, "w", encoding="utf-8") as f:
            json.dump({"last_index": i + 1}, f)

        if data:
            print(f"  저장 완료: {data['title']}")
        else:
            print(f"  도서 정보를 찾을 수 없습니다: {isbn}")
    except Exception as e:
        print(f"  오류 발생: {e}")

    time.sleep(SLEEP_TIME)

print("📘 모든 요청이 완료되었습니다.")
