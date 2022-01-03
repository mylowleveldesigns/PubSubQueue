package com.umang.lld.pubsubqueue.publisher;

import com.umang.lld.pubsubqueue.Message;
import com.umang.lld.pubsubqueue.service.PubSubService;

public class Publisher {
    public void publish(String topic, Message message, PubSubService service) {
        service.addMessage(topic, message);
    }
}
