package com.umang.lld.pubsubqueue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String payload;

    public Message(String payload) {
        this.payload = payload;
    }


}
