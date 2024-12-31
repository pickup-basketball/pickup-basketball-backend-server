# 구현 기능 목록

## 공통
- [x] 공통 exception 관리
## logging
- [ ] slf4j를 통한 logging 처리
## sign up
- [x] users 테이블 구현 및 crud 작성
- [x] users validate 작성 - 중복, 유효성
- [x] passwordEncoder를 사용한 단방향 암호화
- [ ] 보류 - 추후 이메일 인증 기능 구현
## login, auth
- [x] 비밀번호 검증 (passwordEncoder.matches() 사용)
- [x] JWT 토큰 발급
- [ ] Refresh Token 구현
- [ ] 로그인 시도 횟수 제한 (브루트포스 공격 방지)
- [ ] 마지막 로그인 시간 기록