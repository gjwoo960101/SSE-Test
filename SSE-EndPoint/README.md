# SSE-EndPoint (External API)
- 외부 API를 담당하는 프로젝트로 Client가 내부 서버에 EventSource객체를 통해 호출하면 
해당 url의 메소드는 다시 webClient를 이용해 External API를 호출하고 텍스트를 리턴받는다.
