package com.nayuntech.sse_api.api.rest;


import com.nayuntech.sse_api.api.dto.SseDto;
import com.nayuntech.sse_api.api.sevice.ApiService;
import com.nayuntech.sse_api.api.sevice.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class SseRestController {

    @Autowired
    private SseService sseService;
    @Autowired
    private ApiService apiService;

    /* 내부 서버 호출 */
    @GetMapping(path = "/emitter", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(){
        //SseEmitter는 서버에서 클라이언트로 이벤트를 전달할 수 있습니다.(Long.MAX_VALUE은 타임아웃 시간임, 즉 타임아웃없다고 생각해도됨)
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        sseService.addEmitter(emitter);
        sseService.sendEvents();
        return emitter;
    }

    /* 외부 api 호출 */
    @GetMapping(value = "/stream-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSse(){
        SseEmitter emitter = new SseEmitter();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {
                /* 로컬 외부 API 호출 */
                SseDto externalApiResponse = apiService.callApi();

                /* 응답을 SSE로 스트리밍*/
                emitter.send(externalApiResponse);

                /* 스트리밍 완료 */
                emitter.complete();

            }catch (IOException e){
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    /* 외부 api 스트리밍 호출 */
    @GetMapping(value = "/stream-response", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamResponse(){
        SseEmitter emitter = new SseEmitter();
        return apiService.streamResponse();
    }

}
