package com.example.springmini2.client;

import com.example.springmini2.config.FeignFormSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "keycloak-token-client",
        url = "${keycloak.auth-server-url}",
        configuration = FeignFormSupportConfig.class
)
public interface KeycloakTokenClient {

    @PostMapping(
            value = "/realms/{realm}/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    Map<String, Object> getAdminToken(
            @PathVariable("realm") String realm,
            @RequestBody MultiValueMap<String, String> form);
}
