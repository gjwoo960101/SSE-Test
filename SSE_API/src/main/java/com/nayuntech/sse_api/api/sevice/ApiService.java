package com.nayuntech.sse_api.api.sevice;

import com.nayuntech.sse_api.api.dto.SseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
public class ApiService {

    private final WebClient webClient;

    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://192.168.219.100:71").build();
    }

    public SseDto callApi(){
        return this.webClient.get()
                .uri("/mock-api")
                .retrieve()
                .bodyToMono(SseDto.class)
                .block();
    }

    public SseEmitter streamResponse(){

        SseEmitter emitter = new SseEmitter();

        this.webClient.get()
                .uri("/generate-text")
                .retrieve()
                .bodyToFlux(String.class)
                .doOnNext(data -> {
                    try {
                        emitter.send(data);
                    }catch (IOException e){
                        emitter.completeWithError(e);
                    }
                })
                .doOnComplete(emitter::complete)
                .doOnError(emitter::completeWithError)
                .subscribe();
        return emitter;
    }

}
