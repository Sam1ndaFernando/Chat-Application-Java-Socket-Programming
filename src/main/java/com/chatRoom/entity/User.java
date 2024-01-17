package com.chatRoom.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String userName;
    private String password;
    private Integer status;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
