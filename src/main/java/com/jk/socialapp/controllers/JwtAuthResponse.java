package com.jk.socialapp.controllers;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtAuthResponse {
    private String token;
}
