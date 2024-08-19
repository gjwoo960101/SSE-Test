# SSE Client & Server

화면 > 서버 > 외부 api 호출(실제로는 로컬에 다른 프로젝트 api호출)


# SSE 정리
1. 클라이언트는 eventSource라는 객체를 통해 HTTP 요청(GET)을 보냄
2. 서버는 text/event-stream 이라는 MIME 타입을 가진 헤더를 통해 연결을 열어둠
3. 클라이언트는 서버가 필요할때마다 이벤트를 전달할수있음
