package com.example.demoexam2024;

import lombok.*;

public class Users {

    @Getter
    private int userID;

    @Getter @Setter
    private String fio;

    @Getter @Setter
    private String phone;

    @Getter @Setter
    private String login;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private String type;

    public Users(int userID, String fio, String phone, String type) {
        this.userID = userID;
        this.fio = fio;
        this.phone = phone;
        this.type = type;
    }
}
