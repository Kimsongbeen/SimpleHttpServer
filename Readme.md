# SimpleHttpServer-step4
## Thread Pool
- Worker Thread 는 실제로 요청을 처리하는 작업자를 의미합니다. 이런 작업자를 관리하고(보유하고) 있다면 이를 Thread Pool이라고 합니다.
- Producer Consumer Pattern
- Producer: Http Web Server는 Client로부터 Request를 생성하는 역할
- Consumer: Http Request는 Queue에 배치되고 Worker Thread(작업자)에 의해서 요청을 처리하고 적절한 응답을 반환

## Channel
- Http Request Message 공유하는 매개체
- Queue는 RequestChannel 객체를 통해서 서로 다른 Worker Thread 사이에서 공유됨
- Executable interface를 구현한 모든 요청은 Worker Thread가 실행할 수 있음