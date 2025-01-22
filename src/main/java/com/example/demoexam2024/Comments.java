package com.example.demoexam2024;

import lombok.*;

public class Comments {

    @Getter
    private int commentID;

    @Getter @Setter
    private String message;

    @Getter @Setter
    private int masterID;

    @Getter
    private int clientID;

    @Getter
    private int requestID;

    public Comments(int requestID, int clientID, int masterID, String message) {
        this.requestID = requestID;
        this.clientID = clientID;
        this.masterID = masterID;
        this.message = message;
    }
}
