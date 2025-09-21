# Spring Boot WebFlux 코딩 컨벤션 (요약판)

---

## 1) 프로젝트 구조 (com.hmm.cbui)
- 최상위 패키지: `com.hmm.cbui`
- 하위 패키지 역할(스크린샷 구조 준수)
    - `annotation` : 팀 공용 어노테이션(`@ApiIgnore`, 커스텀 Annotation)
    - `aspect` : AOP(로깅/트레이싱/권한 단언)
    - `config` : WebFlux/Security/Jackson/CORS/WebSocket 등 스프링 설정
    - `enums` : 공용 열거형(상태/타입 코드)
    - `exception` : 예외 계층 및 에러 코드(`CryptoException` 등)
    - `web` : REST 어댑터(Controller/RouterFunction 선택 시 Handler 포함)
    - `domain` : 도메인별 하위 패키지(아래 레이어 분리 고정)
        - `api` : REST 어댑터(Controller)
        - `app` : Service(비즈 규칙)
        - `infra` : R2DBC Repository, 외부 연동(WebClient 등)
        - `model` : Entity/DTO/VO/Mapper
    - `global.websocket` : STOMP/WebSocket 관련
        - `dto` : WS 메시지 DTO(검증 어노테이션 포함)
        - `handler` : STOMP Controller/MessageMapping, 에러 핸들러
        - `router` : (필요 시) 채널/엔드포인트 라우터
    - `util` : 공용 유틸(AES128Util 등)
    - `resources` : 설정/스키마/문서

---

## 2) 네이밍 / 형식
- 클래스: `PascalCase`, 메서드/필드/변수: `camelCase`, 상수: `UPPER_SNAKE_CASE`
- 패키지: 소문자 단수, 약어 지양(`util`, `infra`, `app` 등 통일)
- REST 엔드포인트: (`/api/daptalk/message`)
- DTO 접미사: `Request`, `Response` / 메시지 DTO는 `Message`, `Event` 등 의미 중심
- JSON 필드: 팀 표준 **camelCase** 고정
- Reactive 반환형: `Mono<T>`, `Flux<T>` 를 **시그니처에 명확히 노출**

---

## 3) 리액티브 규칙 (WebFlux/WS 공통)
- **블로킹 금지**: JDBC/파일 IO/블로킹 HTTP 사용 금지. 불가피하면 `Schedulers.boundedElastic()`로 격리(최소화).
- **서비스 레이어 순수성**: 비즈 로직만
- **타임아웃/리트라이 표준화**: 외부 호출은 기본 타임아웃, 필요 시 지수 백오프.
- **컨텍스트 로깅**: 요청/세션/사용자 ID는 `Context` or WebFilter로 주입.
- 리액티브 연산 규칙(샘플)
~~~
return repository.findById(id)
  .switchIfEmpty(Mono.error(new NotFoundException("user")))
  .timeout(Duration.ofSeconds(2))
  .retryWhen(Retry.backoff(2, Duration.ofMillis(200)).filter(this::isRetriable));
~~~

---

## 4) R2DBC (Spring Data R2DBC)
- **Repository 규약**: 도메인별 `repository`에 배치, `ReactiveCrudRepository` 우선.
- **트랜잭션**: 리액티브 트랜잭션 사용(`@Transactional` on reactive chains). 블로킹 연산 혼입 금지.
- **엔티티/DTO**: 엔티티는 변경 가능 최소화(Setter 금지), 읽기 전용 뷰는 프로젝션/레코드 DTO 사용.
- 예시 엔티티/레포

---

# 📦 Merge Request(Pull Request) Template

## ✅ MR 체크리스트

- [ ] 자체 코드리뷰를 완료했습니까?
- [ ] 기존 기능에 영향을 줄 수 있는 변경사항을 검토했습니까?
- [ ] Issue 또는 Task와 연결되어 있습니까? (`#BALI-123` 등)
- [ ] 정리정돈된 주석(JavaDoc)이 적절히 포함되어 있습니까?
- [ ] WebFlux/Reactive 규칙을 준수했습니까?

---

## 📝 주요 변경사항

> 아래 항목 중 해당되는 내용을 간결히 작성해주세요.

- ✨ **기능 추가**: (예: 사용자 초대 기능 추가)
- 🐛 **버그 수정**: (예: 날짜 포맷 오류 수정)
- 🔧 **리팩토링**: (예: 폼 유효성 분리)
- ♻️ **UI 변경**: (예: 모달 스타일 개선)
- 🧪 **테스트 추가**: (예: useAuth 훅 유닛 테스트 추가)

---

## 🔍 상세 설명

> 변경 이유, 구조, 고려사항, 주의사항 등 자유롭게 작성

---

## 📷 관련 화면

> 스크린샷, 캡처, Figma 링크

---

## 🔗 관련 이슈

> Portal, WBS, GitLab Issue 등

- #JIRA-123 사용자 관리 기능 개선
