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
### 언어 
<table>
  <tr>
    <td align="center">
      <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Java.png?raw=true" width="80" alt="Java"/><br/>
      <sub><b>Java 23</b></sub>
    </td>
    <td align="center">
      <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/HTMLCSS.png?raw=true" width="80" alt="HTMLCSS"/><br/>
      <sub><b></b></sub>
    </td>
  </tr>
</table>

### 프레임 워크 및 라이브러리 
<table>
  <tr>
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
      <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Thymeleaf.png?raw=true" width="80" alt="Thymeleaf"/><br/>
      <sub><b></b></sub>
    </td>
  </tr>
</table>

### 데이터 베이스 
<table>
  <tr>
    <td align="center">
       <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Mysql.png?raw=true" width="80" alt="MySQL"/><br/>
      <sub><b></b></sub>
    </td>
  </tr>
</table>

### 협업 도구 
<table>
  <tr>
    <td align="center">
      <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Github.png?raw=true" width="80" alt="GitHub"/><br/>
      <sub><b></b></sub>
    </td>
    <td align="center">
      <img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Notion.png?raw=true" width="80" alt="Notion"/><br/>
      <sub><b></b></sub>
    </td>
  </tr>
</table>

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

## 시스템 아키텍처
![image](https://github.com/user-attachments/assets/cdc68cb9-14cd-461e-95d4-0670aa327f72)

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

<details>
<summary> 👉🏻 API 바로보기 </summary>
<div markdown="1">


<table>
  <thead>
    <tr>
      <th>화면</th>
      <th>URL</th>
      <th>Method</th>
      <th>Page</th>
      <th>Response<br>Code</th>
      <th>담당</th>
    </tr>
  </thead>
  <tbody>
    <tr><td>상품 등록 페이지</td><td>/admin/products/new</td><td>GET</td><td>view_save_products.html</td><td>200</td><td>황세희</td></tr>
    <tr><td>상품 등록</td><td>/admin/products/</td><td>POST</td><td></td><td>200</td><td>황세희</td></tr>
    <tr><td>상품 조회</td><td>/admin/products/{productId}</td><td>GET</td><td>view_find_products.html</td><td>200</td><td>황세희</td></tr>
    <tr><td>상품 수정 페이지</td><td>/admin/products/edit/{productId}</td><td>GET</td><td></td><td>200</td><td>황세희</td></tr>
    <tr><td>상품 수정</td><td>/admin/products/{productId}</td><td>POST</td><td></td><td>200</td><td>황세희</td></tr>
    <tr><td>상품 삭제</td><td>/admin/products/{productId}</td><td>Delete</td><td></td><td>204</td><td>황세희</td></tr>
    <tr><td>상품 목록 조회</td><td>/admin/products/list</td><td>GET</td><td>view_products.html</td><td>200</td><td>이승태</td></tr>
    <tr><td>상품 상세 페이지 이동</td><td>/admin/products/{productId}</td><td>GET</td><td></td><td>200</td><td>이승태</td></tr>
    <tr><td>전체 상품 조회</td><td>/products</td><td>GET</td><td>view_save_orders.html</td><td>200</td><td>김건우</td></tr>
    <tr><td>주문 생성 view</td><td>/orders</td><td>GET</td><td>view_save_orders.html</td><td>200</td><td>김건우</td></tr>
    <tr><td>주문 생성</td><td>/orders</td><td>POST</td><td></td><td>200</td><td>김건우</td></tr>
    <tr><td>주문 내역 조회 <br>이메일 입력폼(사용자)</td><td>/orders/search</td><td>GET</td><td>email_form</td><td>200</td><td>곽지원</td></tr>
    <tr><td>전체 주문 내역 조회(사용자)</td><td>/orders/email?{email}</td><td>GET</td><td>view_orders</td><td>200</td><td>곽지원</td></tr>
    <tr><td>전체 주문 내역에서<br> 주문 ID로 조회(사용자)</td><td>/orders/id?{orderId}&{email}</td><td>GET</td><td>view_orders</td><td>200</td><td>곽지원</td></tr>
    <tr><td>주문 상세 조회(사용자)</td><td>/orders/{orderId}</td><td>GET</td><td>view_orderDetail.html</td><td>200</td><td>박유한</td></tr>
    <tr><td>주문 수정 페이지</td><td>/orders/{orderId}/edit</td><td>GET</td><td>view_update_order.html</td><td>200</td><td>박유한</td></tr>
    <tr><td>주문 수정</td><td>/orders/{orderId}</td><td>PUT</td><td></td><td>204</td><td>박유한</td></tr>
    <tr><td>주문 취소</td><td>/orders/{orderId}/cancel</td><td>PUT</td><td></td><td>204, 200</td><td>박유한</td></tr>
    <tr><td>관리자 로그인</td><td>/admin/login</td><td>GET</td><td>view_login.html</td><td>200</td><td>박유한</td></tr>
    <tr><td>로그인 요청</td><td>/admin/login</td><td>POST</td><td></td><td>200</td><td>박유한</td></tr>
    <tr><td>로그아웃 요청</td><td>/admin/logout</td><td>POST</td><td></td><td>200</td><td>박유한</td></tr>
    <tr><td>주문 상세 조회(관리자)</td><td>/orders/admin/{orderId}</td><td>GET</td><td></td><td>200</td><td>박유한</td></tr>
    <tr><td>주문 목록 조회</td><td>/admin/orders/list</td><td>GET</td><td>view_orders.html</td><td>200</td><td>이승태</td></tr>
    <tr><td>주문 상세 페이지 이동</td><td>/admin/orders/{orderId}</td><td>GET</td><td></td><td>200</td><td>이승태</td></tr>
  </tbody>
</table>


  
</div>
</details>



## 🤔 기술적 이슈와 해결 과정

### 1) ADMIN 계정 처리 방식

**문제**

- 엔티티 없이 인메모리 인증으로 관리자 계정 하드코딩 고려
- 비밀번호 노출 위험, 계정 변경 시 서버 재배포 필요, 확장성 저하

**해결**

- DB 기반 인증으로 전환
- MemberEntity 생성 후 DB에 관리자·사용자 정보 저장
- 인증 시 DB 조회

**결과**

- 운영 중 계정 추가·삭제·수정 가능
- 코드에 비밀번호 노출 없음 → 보안 강화
- 일반 사용자 포함 확장성 확보


### 2) 예외 처리 통일

**문제**

- 커스텀 vs 기본 예외 혼용 → 에러 포맷 불일치  
- 화면 전환/`@ResponseBody` 구간별 다른 처리 필요  

**해결**

- `ErrorCode` enum + `CustomException` 클래스 도입  
- `@ControllerAdvice`로 일관된 핸들링  
- 커스텀 예외 외에는 403/404/500 에러 페이지 자동 적용  

**결과**

- 모든 예외를 중앙에서 일관 처리  
- 새로운 에러 발생 시 `ErrorCode` 추가만으로 즉시 지원  

---

### 3) 테스트 코드 전략

**문제**

- 단위 테스트(모킹) vs 통합 테스트(DB 연결) 선택 고민  
- DB 오버헤드 vs 제약조건 검증 불가  

**해결**

- 단위 테스트(`@Mock`): 서비스 로직 검증  
- 통합 테스트(`@Transactional` + `@Autowired`): 전체 흐름 검증  
- 팀 표준에 따라 용도별 분리 실행  

**결과**

- 목적에 맞는 테스트 방식 적용으로 효율성과 안정성 확보  

---

### 4) 이미지 업로드 기능

- Notion 링크 참조하여 구현  

---

### 5) 상품 조회 기능 개선

**문제**

- 삭제된 상품도 조회, 이름 검색 불가  

**해결**

- `delYn = 'N'` 필터 추가한 JPQL 작성  
- 이름 `LIKE` 검색용 JPQL 메서드 추가  

**결과**

- 삭제되지 않은 상품만 노출  
- 대소문자 무시 부분 일치 검색 지원  
- UX 및 데이터 정확성 향상  


<br />
