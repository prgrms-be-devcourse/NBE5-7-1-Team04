# ☕️ 커피 메뉴 관리 프로젝트



## 📝 소개
본 프로젝트는 **Spring Boot** 기반의 웹 애플리케이션으로, **상품 관리**와 **주문 처리 기능**을 제공합니다.  
**Spring Security**를 활용하여 사용자와 관리자 권한을 분리하여 관리의 효율성과 보안을 강화하였습니다.

---

### 📌 주요 기능

### 🔐 권한 분리 (Spring Security 기반)
- **관리자(Admin)**: 상품 및 주문 전체 관리
- **사용자(User)**: 상품 조회 및 주문 기능 사용

---

### 🛒 상품 관리

| 역할     | 기능                            |
|--------|---------------------------------|
| 관리자  | 상품 등록, 수정, 삭제, 조회 (CRUD) |
| 사용자  | 상품 목록 및 상세 조회              |

---

### 📦 주문 처리

| 역할     | 기능                                       |
|--------|--------------------------------------------|
| 관리자  | 전체 사용자 주문 내역 조회                    |
| 사용자  | 주문 생성, 내 주문 내역 조회, 주문 취소, 배송지 수정 |

---

### ⏱️ 주문 일괄 처리

- `@Scheduled` 어노테이션을 활용하여 **주문 상태를 일괄적으로 처리**하는 기능 구현
- 특정 시간마다 예약 실행되어 주문 처리 자동화

---

> ✅ 본 프로젝트는 **역할 기반 권한 제어**, **RESTful API 설계**, **스케줄링 처리** 등 실무 중심 기능을 반영하여 설계되었습니다.

## ⚙ 기술 스택
<table>
  <tr>
    <td align="center">
      <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Java.png?raw=true" width="80" alt="Java"/><br/>
      <sub><b>Java 23</b></sub>
    </td>
    <td align="center">
      <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringBoot.png?raw=true" width="80" alt="Spring Boot"/><br/>
      <sub><b>Spring Boot 3.4.4</b></sub>
    </td>
    <td align="center">
      <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringSecurity.png?raw=true" width="80" alt="Spring Security"/><br/>
      <sub><b></b></sub>
    </td>
    <td align="center">
      <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/SpringDataJPA.png?raw=true" width="80" alt="Spring Data JPA"/><br/>
      <sub><b></b></sub>
    </td>
    <td align="center">
      <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Mysql.png?raw=true" width="80" alt="MySQL"/><br/>
      <sub><b></b></sub>
    </td>
    <td align="center">
      <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Thymeleaf.png?raw=true" width="80" alt="Thymeleaf"/><br/>
      <sub><b></b></sub>
    </td>
  </tr>
</table>

### Infra

### Tools
<div>
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Github.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Notion.png?raw=true" width="80">
</div>

<br />

## 💁‍♂️ 프로젝트 팀원

<table>
  <thead>
    <tr>
      <th align="center">팀장</th>
      <th align="center">팀원</th>
      <th align="center">팀원</th>
      <th align="center">팀원</th>
      <th align="center">팀원</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td align="center">
        <a href="https://github.com/rjswjddn">
          <img src="https://github.com/rjswjddn.png" width="120" height="120" alt="김건우"/><br/>
          <sub><b>김건우</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/jiwon1217">
          <img src="https://github.com/jiwon1217.png" width="120" height="120" alt="곽지원"/><br/>
          <sub><b>곽지원</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/sehee123">
          <img src="https://github.com/sehee123.png" width="120" height="120" alt="황세희"/><br/>
          <sub><b>황세희</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/gffd94">
          <img src="https://github.com/gffd94.png" width="120" height="120" alt="이승태"/><br/>
          <sub><b>이승태</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/pbk2312">
          <img src="https://github.com/pbk2312.png" width="120" height="120" alt="박유한"/><br/>
          <sub><b>박유한</b></sub>
        </a>
      </td>
    </tr>
  </tbody>
</table>

## 🛠️ 역할 분담

| 이름   | 담당 기능 |
|--------|-----------|
| **건우** | - 주문 생성 기능 구현<br>- 주문 관련 스케줄러 설정<br>- 예외 처리 로직 구현 |
| **유한** | - 주문 상세 조회 기능 구현<br>- 주문 수정 및 취소 기능 구현<br>- Spring Security를 통한 인증/인가 적용 |
| **지원** | - 사용자 주문 내역 조회 기능 구현<br>- 전반적인 코드 리팩토링<br>- 프론트엔드 UI 통합 작업 |
| **세희** | - 상품 등록 및 수정 기능 구현<br>- 상품 상세 조회 기능 구현<br>- 상품 삭제 기능 구현 |
| **승태** | - 관리자 페이지 주문 내역 조회 기능 구현<br>- 상품 목록 조회 및 검색 기능 구현 |


## 🛠️ 프로젝트 아키텍쳐


## ERD

<img width="656" alt="image" src="https://github.com/user-attachments/assets/cdb34439-0b0e-4abc-aac4-a2e26b0d7f49" />

## 플로우차트


![image1](https://github.com/user-attachments/assets/2462ab84-9897-49ea-8563-304046d71ee0)





## 화면 구성
#### 주문 생성 및 사용자 주문 관리
![주문생성](https://github.com/user-attachments/assets/4e74e3c9-9711-4aac-a6a6-3d91cc7677f3)

#### 상품 관리
![상품관리](https://github.com/user-attachments/assets/5eae36e1-e7ce-4f77-8da1-5bd5e6eca5a9)

#### 주문 관리
![주문관리](https://github.com/user-attachments/assets/6a7a89ca-653b-415a-aed3-04705052396b)

## 협업 방식
### 🛠️ 브랜치 전략
![Image20250428163351](https://github.com/user-attachments/assets/71405653-385a-4bd0-95dd-bb0f58aed569)
1. **이슈 생성**
    - GitHub 이슈를 통해 작업 항목 정의

2. **브랜치 생성**
    - `dev` 브랜치에서 이슈별 작업 브랜치 생성
    - 브랜치 명명 규칙 예시: `feature/이슈번호-작업내용`

3. **PR 및 코드 리뷰**
    - 작업 완료 후 Pull Request(PR) 생성
    - 팀원 간 코드 리뷰 진행

4. **Merge 및 브랜치 정리**
    - 리뷰 완료 후 `dev` 브랜치로 Merge
    - Merge 후 이슈 브랜치 삭제
    - `dev` 브랜치 최신 상태 유지

---

### 🧑‍💻 코딩 컨벤션

### Git 컨벤션
1. **Commit 메시지 형식**
   - [이모지][타입] 커밋 메시지
   - 예: `♻️ feat:사용자 로그인 기능 구현`

2. **PR 제목 및 설명**
   - 제목: `[타입] 이슈번호 - 작업 내용 요약`
   - 본문: 작업 내용, 고려한 사항, 테스트 방법 등 기재

3. **Branch 명명 규칙**
   - `타입/이슈번호`
   - 예:`feat/15`

4. **Issue 제목 규칙**
   - `[타입] 작업 내용 요약`

---

### 코드 스타일

1. **스타일 가이드**
   - [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) 적용

2. **DTO 작성 기준**
   - 요청(Request) / 응답(Response) DTO 분리
   - `mapper`를 활용한 변환 로직 구성

3. **이름 규칙**
   - **클래스명**: 대문자로 시작, 명확한 의미 전달
       - 예: `UserService`, `OrderController`
   - **메서드명**: 동사 + 대상, camelCase 사용
       - 예: `createUser()`, `getOrderList()`
   - **변수명**: camelCase 사용, 명확하고 간결하게
       - 예: `userId`, `orderRequest`

## 🗂️ APIs

작성한 API는 아래에서 확인할 수 있습니다.

👉🏻 [API 바로보기](/backend/APIs.md)



## 🤔 기술적 이슈와 해결 과정


<br />
