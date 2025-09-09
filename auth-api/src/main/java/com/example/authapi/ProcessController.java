package com.example.authapi;

import com.example.authapi.dto.ProcessRequest;
import com.example.authapi.dto.TransformRequest;
import com.example.authapi.dto.TransformResponse;
import com.example.authapi.repository.ProcessingLogRepository;
import com.example.authapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ProcessController {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final ProcessingLogRepository logRepository;

    @Value("${app.data-api-url}")
    private String dataApiUrl;

    @Value("${app.internal-token}")
    private String internalToken;

    public ProcessController(RestTemplate restTemplate,
                             UserRepository userRepository,
                             ProcessingLogRepository logRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.logRepository = logRepository;
    }

    @PostMapping("/process")
    public TransformResponse process(@RequestBody ProcessRequest req, Authentication auth) {
        // кто вызвал
        String email = auth.getName();
        var user = userRepository.findByEmail(email).orElseThrow();

        // запрос к data-api
        String url = dataApiUrl + "/api/transform";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Internal-Token", internalToken);

        HttpEntity<TransformRequest> entity = new HttpEntity<>(new TransformRequest(req.text()), headers);
        ResponseEntity<TransformResponse> resp =
                restTemplate.postForEntity(url, entity, TransformResponse.class);

        TransformResponse body = resp.getBody();
        if (body == null) throw new RuntimeException("Empty response from data-api");

        // сохраняем лог
        ProcessingLog log = new ProcessingLog();
        log.setUser(user);
        log.setInputText(req.text());
        log.setOutputText(body.result());
        logRepository.save(log);

        return body;
    }
}