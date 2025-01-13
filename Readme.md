# SimpleHttpServer-step7
### Context
- Application이 실행되는 환경
### ContextHolder
- 서로 다른 Thread에서 ContextHolder를 통해서 Context에 접근할 수 있다.
- Singleton pattern 구현

### Context의 쓰임
#### HttpService
- 요청 시 IndexHttpService, InfoHttpService 객체를 새성하고 실행함.
```  if(httpRequest.getRequestURI().equals("/index.html")) {
  HttpService indexHttpService = new IndexHttpService();
  indexHttpService.service(httpRequest, httpResponse);
  }else if(httpRequest.getRequestURI().equals("/info.html")) {
  HttpService infoHttpService = new InfoHttpService();
  infoHttpService.service(httpRequest, httpResponse);
  } 
  ```

- IndexHttpService, InfoHttpService을 어딘가에 한 번 생성해서 저장한다면(공유할 수 있다면) 100번을 요청하더라도 한 번 생성된 객체를 Singleton처럼 사용할 수 있으니 더욱 효율적으로 관리 가능함.
- Context에 등록하고 Context에 등록된 객체를 서로 다른 Thread 내에서 공유할 수 있음
```  Context context = ContextHolder.getApplicationContext();
  context.setAttribute("/index.html",new IndexHttpService());
  context.setAttribute("/info.html", new InfoHttpService());
  context.setAttribute(CounterUtils.CONTEXT_COUNTER_NAME,0l);
```