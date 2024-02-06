package com.jk.socialapp.controllers;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String username;
    private String password;
}
