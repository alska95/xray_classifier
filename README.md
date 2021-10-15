
# X-ray Classifier - v2

:link: [서비스 링크](http://ec2-3-133-106-136.us-east-2.compute.amazonaws.com:3000/)

Aws를 이용하여 우분투에서 배포될 예정입니다.

---


## 사용된 기술

***Spring Boot |  Spring Security | JUnit | JPA | H2DataBase  
React | Redux | Saga | Node.js  
Antd  
TensorFlow .py | TensorFlow.js | SkLearn***


---

## X-ray 분석 모델 코드

[코드 링크](https://kaggle.com/kyeonghahwangg/capstone)

---



## 전체 서비스 화면 :camera:  

![](https://i.imgur.com/lQ7plhQ.png)  


## 기능 상세 :wrench: 



### 셈플 이미지 선택

![](https://i.imgur.com/4pSdlIF.png)  

미리 준비된 테스트용 셈플 이미지를 사용하거나, 직접 분석이 필요한 X-ray를 선택할 수 있습니다.


---

### 메인 서비스 - 이미지 선택 후 화면

![](https://i.imgur.com/kXRUtME.png)  

선택한 X-ray가 메인 서비스 화면에 출력됩니다. 사용자는 분석 시작을 눌러 X-ray의 질병 유무를 분석할 수 있습니다.


---

### 메인 서비스 - 분석 완료 후 화면
![](https://i.imgur.com/qBBTg8q.png)  
질병별 최적의 Threshold값 이상의 값을 가지고 있는 X-ray 분석 결과값이 우측 상단 화면에 표시됩니다. 사용자는 이 값을 보고 질병 유무를 판단할 수 있습니다.
이미지가 TensorFlow.js 모델을 통과하며 Client Side에서 분석이 진행됩니다.

--- 

### 메인 서비스 - HeatMap
![](https://i.imgur.com/tXNDmzl.png)  
마지막 relu Layer에서 모델이 집중하고 있는 부분을 표시하는 HeatMap입니다. 사용자는 이 HeatMap을 보고 모델이 어느 부분을 보고 질병을 판단했는지 분석 할 수 있으며, 밝은 부분에 질병이 있다고 유추 할 수 있습니다.

---

### 메인 서비스 - 분석 결과 저장
![](https://i.imgur.com/evuK9FD.png)  

본 서비스에 사용된 X-ray는 분석 결과에 따라 라벨링되어 저장됩니다.

---

### 분석 결과 게시물
![](https://i.imgur.com/JaxG6xO.png)   

사용자는 분석에 대한 소감을 입력하고 저장함으로서 다른 사용자에게 참고가 되도록 제공할 수 있습니다.

![](https://i.imgur.com/hWZdyw3.png)  
저장을 누르면 게시글이 저장됩니다.

![](https://i.imgur.com/Q10tSGn.png)  

저장된 게시글의 모습입니다. 게시글은 작성한 사용자만 수정, 삭제가 가능하며, 댓글은 로그인한 유저라면 누구나 입력할 수 있습니다.

--- 

### 댓글 달기

![](https://i.imgur.com/sWbp1gd.png)  


해당 Input에 댓글을 입력하고 저장을 누르면 댓글이 입력됩니다.

![](https://i.imgur.com/deYKBrD.png)  
댓글이 성공적으로 입력된 모습입니다. 댓글을 입력한 사용자라면, 해당 댓글을 삭제할 수 있습니다.

--- 

### 로그인 

![](https://i.imgur.com/mvPuwvL.png)  

로그인 세션이 존재하지 않는다면 로그인 창이 노출됩니다. 사용자는 올바른 아이디, 비밀번호를 입력하여 로그인 할 수 있습니다.

--- 
### 회원가입

![](https://i.imgur.com/Ck9Ixuy.png)  

비밀번호는 Spring Security를 사용하여 암호화 되어 저장됩니다.
아이디는 중복될 수 없고 사용자는 약관에 동의하고 회원가입을 진행할 수 있습니다.

--- 


## 데이터 구조 :minidisc: 

![](https://i.imgur.com/LVojdLi.png)

--- 

