package com.example.dataapi;

import com.example.dataapi.dto.TransformRequest;
import com.example.dataapi.dto.TransformResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransformController {

    @Value("${app.internal-token}")
    private String internalToken;

    @PostMapping("/transform")
    public ResponseEntity<?> transform(
            @RequestHeader(value = "X-Internal-Token", required = false) String token,
            @RequestBody TransformRequest req
    ) {
        if (token == null || !token.equals(internalToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");
        }

        // простая трансформация: переворачиваем строку и делаем UPPERCASE
        String result = new StringBuilder(req.text()).reverse().toString().toUpperCase();

        return ResponseEntity.ok(new TransformResponse(result));
    }
}
