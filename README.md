# Todo List

## 소개

### JDK Version

JDK 17

### 인증 구현

JWT / Spring Security (Only ActiveToken)

### Swagger URL

http://localhost:8080/swagger-ui/index.html#/

### 코드 구조

```bash
├── aspect # AOP 관련 클래스
├── config # 설정 관련 클래스
├── domain # 도메인 관련 클래스
│   ├── todo # Todo 관련 도메인 모델과 기능
│   │   ├── code # enum
│   │   ├── controller
│   │   ├── dto 
│   │   ├── entity
│   │   ├── repository
│   │   └── service
│   └── user # User 관련 도메인 모델과 기능
│       ├── code # enum
│       ├── controller
│       ├── dto 
│       ├── entity
│       ├── repository
│       └── service
└── security # 스프링 시큐리티 관련 클래스
```
