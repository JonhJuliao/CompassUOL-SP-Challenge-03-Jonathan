package com.APIGateway.APIGateway.feign;

import com.APIGateway.APIGateway.payload.TokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "authclient", url = "localhost:8082")
public interface AuthClient {

    @PostMapping(value = "/oauth/validate", consumes = "application/json")
    public ResponseEntity<Void> validateToken(@RequestBody TokenDto token);
}
