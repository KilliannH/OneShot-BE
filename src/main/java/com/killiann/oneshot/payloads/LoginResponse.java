package com.killiann.oneshot.payloads;

public class LoginResponse {

    public String token;
    public String id;

    public LoginResponse() {}

    public LoginResponse(String token, String id) {
        this.token = token;
        this.id = id;
    }
}