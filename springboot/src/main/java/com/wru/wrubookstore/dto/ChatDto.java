package com.wru.wrubookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter @Setter
@ToString
public class ChatDto {

    private Integer chatId;
    private Integer userId;
    private String uuid;
    private String sender;
    private String message;
    private Timestamp regDate;
    private String chatStatus;

    public ChatDto() {
    }

    public ChatDto(Integer userId, String uuid, String sender, String message) {
        this.userId = userId;
        this.uuid = uuid;
        this.sender = sender;
        this.message = message;
    }
}
