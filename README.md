# 메뉴 추천 CLI 프로그램

CLI 환경에서 식사 조건(종류, 가격대)을 입력하면 4초 동안 로딩되고(스피너가 돌아가고), 
랜덤으로 메뉴 1개와 그 메뉴를 판매하는 식당 리스트를 추천해주는 프로그램 입니다.

활용한 멀티 쓰레드 
- Main Thread(추천 시스템)
- Spinner Thread(진행 표시용)
위의 두 가지 쓰레드가 동시에 실행되며 출력이 섞이지 않도록 SharedPrinter 클래스에서 synchronized를 통해 동기화 처리하였습니다.
---

## 실행 방법

### 1. 레포 클론
```bash
git clone https://github.com/soyeon911/bootcamp_kakao.git
cd bootcamp_kakao
```
### 2. 컴파일
```bash
javac -d out $(find src/main/java -name "*.java")
```
### 3. 실행
```bash
java -cp out Test
```

### 4. 프로그램 사용법
🙏 데이터가 많지 않아 random 추천이 아쉬울 수 있습니다.

step 1. 실행 후 식당 / 카페 중 선택

step 2. 음식 종류 선택
- 한식 / 중식 / 일식 / 양식

step 3. 가격대 선택
- 10,000원 이하
- 10,000원 ~ 15,000원
- 15,000원 이상

step 4. 스피너가 약 4초간 표시

step 5. 추천된 메뉴 1개 출력

step 6. 해당 메뉴를 제공하는 식당 목록 출력


### 5. 주요 기능 설명
- 멀티 쓰레딩 : Spinner 쓰레드와 main 쓰레드 병렬 실행
- 임계구역 동기화 : SharedPrinter 클래스에서 synchronized 활용을 통한 출력이 뒤섞이는 것을 방지함
- 조건 필터링 : 음식 종류 + 가격대 두 가지 조건으로 candidate menu filtering
- 랜덤 추천 : candidate menu list 내에서 Random()를 통해 하나 선택, 이후 해당 메뉴를 제공하는 식당 리스트 표시



