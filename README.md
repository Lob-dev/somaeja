# Somaeja

*모든 사람이 소매상이 될 수 있는 거래 플랫폼*



<br/>

지역 정보 기반의 물품 거래 플랫폼 RESTful API 서버 프로젝트입니다.

당근마켓과 유사한 서비스를 제공함으로써 사용자들은 설정된 지역 정보를 가지고 다른 사람들의 물품을 확인하고 구매 의사를 밝힐 수 있습니다.  

Kakao Oven을 이용하여 프로토타입 설계를 진행하였으며, API 서버만 집중하여 개발을 진행합니다.

<br/>
<br/>


## 프로젝트의 대략적인 인프라 구조
--- 

<br/>

<p align="center">
<img src="https://user-images.githubusercontent.com/67903919/105021619-1342d380-5a8c-11eb-9d98-3bdaed70f283.png" width="85%">
</p>

<br/>
<br/>

## 프로젝트 설계 정보
---

### 프로젝트 UML

<br/>

<p align="center">
<img src="https://user-images.githubusercontent.com/67903919/105015275-bc85cb80-5a84-11eb-83e2-674ed82b0e7e.png" width="85%">
</p>

### 구조 변경 사항

- **AFFICHE**
    - USER FK 를 가짐으로 USER의 NICKNAME 제외 (닉네임 변경시 처리가 불편하다.)
    - 11.25 테이블 네이밍 변경 AFFICHE → POST (이해하기 쉬운 익숙한 네이밍이 좋다)
    - 11.25 게시글 삭제 유무 DELETE_YN 이라는 Flag 를 추가

        → **히스토리 테이블 혹은 로그 테이블 생성하여 대체하자. (제거)**

    - 테이블 이름 변경

- **AFFICHE INFO**

    **생성 이유 : 컨텐츠 사이즈의 따른 성능 부하 여부, 목록 조회시에 컨텐츠가 보이지 않는다.**

    - AFFICHE Table에 컨텐츠를 두고 조회할 때 컨텐츠를 제외하는 방법이 있다.
    - 단순히 생성 이유 때문에 분리하는 것은 이득보다 관리하는 비용이 늘어날 수 있다.
    - **AFFICHE TABLE 와 병합**

- **LOCATION**
    - PK, FK 를 통한 USER 와의 양방향 종속성(의존성) 제거

- **HASHTAG**

    생성 이유 : AFFICHE 테이블에서 사용시 태그별 분할 문제점

    - 1 대 N 관계 해소를 위해 중간의 AFFICHE_HASHTAG 테이블 생성 (Toxi solution)

        **→ 과한 정규화, 복잡해질 수 있는 쿼리, 데이터의 고아 상태 발생 가능 (Scuttle으로 변경)**

- **AFFICHE_LOCATION**

    생성 이유 : POST가 여러 지역정보를 가지게 되면 다대다 관계가 됨으로 이를 분리하기 위해 작성

    - POST의 LOCATION 설정 정보를 하나만 제공하도록 요구사항을 변경함으로써 제거

<br/>
<br/>

### 프로젝트 화면 설계

<br/>

<p align="center">
<img src="https://user-images.githubusercontent.com/67903919/105026514-f4dfd680-5a91-11eb-905f-adf4ed430582.png" width="100%">
</p>



<br/>
<br/>

## 프로젝트 개발 시 집중하는 부분
---
### 지속적인 코드 개선
  - 작성된 코드를 방치하는 것이 아니라 계속해서 개선합니다.
  - 가독성과 Depth 뿐만 아니라 호출 로직들도 개선하려 노력합니다.

<br/>

### 멀티스레드 환경에서 안전한 코드 작성
  - 공유하는 자원을 최소화하고 최대한 모든 객체를 불변 객체로 작성합니다.


<br/>
<br/>

## 프로젝트 개발 시 준수 사항
---

### Convention

+ Naver Hackday Convention 준수
+ 명시적인 Naming Convention 준수
+ Encapsulation 을 지킬 수 있는 원칙들을 준수
+ 좋은 커밋 메시지를 위해 Git commit 규칙을 준수 https://blog.ull.im/engineering/2019/03/10/logs-on-git.html
 

<br/>

### Work Flow

+ Git Branch Strategy "**Git-Flow**" 

<p align="center">
<img src="https://user-images.githubusercontent.com/67903919/103394069-98138f00-4b69-11eb-925e-d79966b3f716.png" width="80%">
</p>


+ 참고 자료
    + https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html
    + https://gmlwjd9405.github.io/2018/05/11/types-of-git-branch.html

<br/>


### Tech Stack 

+ Spring Boot 2.3.5
+ JDK 11
+ Gradle
+ H2(Test, InMemory) + MySQL(Operational DB) + MyBatis
+ Naver Cloud Platform 



<br/>
