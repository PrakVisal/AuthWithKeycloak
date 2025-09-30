package com.example.springmini2.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class KeycloakUser {
    private String username;
    private boolean enabled;
    private List<Credential> credentials;
}

