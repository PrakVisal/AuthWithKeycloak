package com.example.springmini2.client;

import com.example.springmini2.config.KeycloakAdminFeignConfig;
import com.example.springmini2.model.dto.KeycloakUser;
import com.example.springmini2.model.dto.request.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(
        name = "keycloak-admin-client",
        url = "${keycloak.auth-server-url}")
public interface KeycloakAdminClient {

    @PostMapping(
            value = "/admin/realms/{realm}/users",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void createUser(
            @PathVariable("realm") String realm,
            @RequestBody KeycloakUser user,
            @RequestHeader("Authorization") String bearerToken);

    @PostMapping
    void login(@RequestBody LoginRequest loginRequest);
}

