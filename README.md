# bootcamp_kakao

# 메뉴 추천 CLI 프로그램

간단한 CLI 기반 "오늘의 메뉴 추천" 프로그램입니다.  
사용자가 원하는 **음식 종류(Cuisine)** 와 **예산(PriceRange)** 을 입력하면,  
조건에 맞는 메뉴를 하나 랜덤으로 추천하고 그 메뉴를 판매하는 식당들을 보여줍니다.  

---

## 🚀 실행 방법

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
java -cp out com.example.menu.AStage
```


### 4. 회고
개념으로만 배우고, 예시만 봐왔던 동시성, lock 처리를 실제로 적용해봐서 신기하고 재미있게 과제를 했습니다.
데이터들은 부트캠프 근처의 식당과 랜덤으로 정한 식당과 메뉴를 적용하여 간단하게 만들어보았습니다.
랜덤성의 목적으로 다량의 데이터가 필요함은 알지만 대략적인 프로젝트의 흐름을 파악한 후 올바른 방향이라면 데이터를 더 추가하도록 하겠습니다.

