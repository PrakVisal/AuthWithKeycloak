package com.example.springmini2.service.impl;

import com.example.springmini2.client.KeycloakAdminClient;
import com.example.springmini2.client.KeycloakTokenClient;
import com.example.springmini2.model.RegisterUser;
import com.example.springmini2.model.dto.Credential;
import com.example.springmini2.model.dto.KeycloakUser;
import com.example.springmini2.model.dto.request.LoginRequest;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {

    private final KeycloakTokenClient tokenClient;
    private final KeycloakAdminClient adminClient;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}") // client_id
    private String clientId;

    @Value("${keycloak.credentials.secret}") // client_secret
    private String clientSecret;

//    @Value("${keycloak.admin.username}")
//    private String adminUsername;
//
//    @Value("${keycloak.admin.password}")
//    private String adminPassword;

    /**
     * Register a new user in Keycloak using Feign clients
     */
    public void registerUser(RegisterUser request) {
        // 1. Get admin token
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("grant_type", "client_credentials");

        Map<String, Object> tokenResponse = tokenClient.getAdminToken(realm, form);
        String accessToken = (String) tokenResponse.get("access_token");

        // 2. Create user payload
        Credential credential = new Credential("password",request.getPassword(),false);

        KeycloakUser newUser = new KeycloakUser();
        newUser.setUsername(request.getUsername());
        newUser.setEnabled(true);
        newUser.setCredentials(List.of(credential));

        System.out.println("Token: " + accessToken);
        System.out.println("newUser: " + newUser);

        // 3. Call Keycloak Admin API
        adminClient.createUser(realm, newUser, "Bearer " + accessToken);
    }

    public ResponseEntity<?> login(LoginRequest login) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret); // optional if public client
        form.add("grant_type", "password");
        form.add("username", login.getUsername());
        form.add("password", login.getPassword());

        try{
        Map<String, Object> tokenResponse = tokenClient.getAdminToken(realm, form);
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        }catch (FeignException e){
            if(e.status() == HttpStatus.UNAUTHORIZED.value()){
                return new ResponseEntity<>("Username or Password invalid", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

