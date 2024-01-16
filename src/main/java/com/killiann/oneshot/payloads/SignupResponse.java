package com.killiann.oneshot.payloads;

public class SignupResponse {

    public String token;
    public String id;

    public SignupResponse() {}

    public SignupResponse(String token, String id) {
        this.token = token;
        this.id = id;
    }
}