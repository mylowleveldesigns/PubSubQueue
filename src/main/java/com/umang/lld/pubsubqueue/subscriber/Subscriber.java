package com.umang.lld.pubsubqueue.subscriber;

import com.umang.lld.pubsubqueue.Message;
import com.umang.lld.pubsubqueue.service.PubSubService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Getter
@Setter
public class Subscriber {
    private PubSubService service;

    private String name;
    private Map<String, Integer> topicOffsetMap;

    public Subscriber(String name, PubSubService service) {
        this.name = name;
        this.topicOffsetMap = new HashMap<>();
        this.service = service;
    }

    public void addSubscriber(String topic) {
        service.addSubscriber(topic, this);
        this.topicOffsetMap.put(topic, service.getCurrentOffsetForTopic(topic));
    }

    public void unSubscribe(String topic){
        service.removeSubscriber(topic, this);
    }

    public Message consumeNextMessage(String topic){
        if(topicOffsetMap.containsKey(topic)){
            Integer currentTopicOffset = topicOffsetMap.get(topic);
            Message message =  service.getMessageInTopicOnOffset(topic, currentTopicOffset, this);
            currentTopicOffset++;
            topicOffsetMap.put(topic, currentTopicOffset);
            return message;
        } else {
            System.out.println("Subscriber might not be subscribed to this topic, cannot consume message");
            return null;
        }
    }
}
