package com.example.springmini2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credential {
    private String type;
    private String value;
    private boolean temporary;
}

