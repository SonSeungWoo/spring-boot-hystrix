package com.github.ssw;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ConsumerController {

    private final RestTemplate restTemplate;

    @GetMapping("/test")
    @HystrixCommand(fallbackMethod = "fallback")
    public String test(@RequestParam String path) {
        log.info("param : {}", path);

        ResponseEntity<String> entity = restTemplate.getForEntity("http://127.0.0.1:8080/" + path,
                String.class);

        return entity.getBody();
    }

    @GetMapping("/consumer")
    @HystrixCommand(commandKey = "consumer", fallbackMethod = "fallback")
    public String consumer(@RequestParam String path) {
        log.info("param path : {}", path);

        ResponseEntity<String> entity = restTemplate.getForEntity("http://127.0.0.1:8080/" + path,
                String.class);

        return entity.getBody();
    }


    //fallback 메소드
    private String fallback(String path) {
        log.info("param path : {}", path);
        return "fallback";
    }
}
