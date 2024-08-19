package com.nayuntech.sseendpoint.api.rest;

import com.nayuntech.sseendpoint.api.dto.ResponseSseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class ExternalEndPointContoller {

    @GetMapping(value = "/mock-api")
    public ResponseSseDto mockExternalApi(){

        ResponseSseDto responseSseDto = new ResponseSseDto();

        responseSseDto.setId("gjwoo96");
        responseSseDto.setName("이건우");

        return responseSseDto;
    }

    @GetMapping(value = "/generate-text")
    public SseEmitter generate(){
        SseEmitter emitter = new SseEmitter();
        ExecutorService excutor = Executors.newSingleThreadExecutor();

        excutor.execute(()->{
            try {
                // 예제 텍스트 데이터를 순차적으로 스트리밍
                emitter.send("Generating text response 1...");
                Thread.sleep(1000);
                emitter.send("Generating text response 2...");
                Thread.sleep(1000);
                emitter.send("Final response generated.");
                emitter.complete();
            }catch (IOException | InterruptedException e){
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

}
