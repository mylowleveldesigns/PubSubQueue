package com.umang.lld.pubsubqueue.service;

import com.umang.lld.pubsubqueue.Message;
import com.umang.lld.pubsubqueue.subscriber.Subscriber;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.*;

@Getter
@Setter
@Component
public class PubSubService {
    private Map<String, Set<Subscriber>> subscribersTopicMap;
    private Map<String, List<Message>> messageTopicMap;

    public PubSubService() {
        this.subscribersTopicMap = new HashMap<>();
        this.messageTopicMap = new HashMap<>();
    }

    public void addMessage(String topic, Message message){
        if(messageTopicMap.containsKey(topic)){
            messageTopicMap.get(topic).add(message);
        } else {
            List<Message> newList = new ArrayList<>();
            newList.add(message);
            messageTopicMap.put(topic, newList);
        }
    }

    public void addSubscriber(String topic, Subscriber subscriber){
        if(subscribersTopicMap.containsKey(topic)){
            subscribersTopicMap.get(topic).add(subscriber);
        } else {
            Set<Subscriber> set = new HashSet<>();
            set.add(subscriber);
            subscribersTopicMap.put(topic, set);
        }
    }

    public void removeSubscriber(String topic, Subscriber subscriber) {
        if(subscribersTopicMap.containsKey(topic)) {
            subscribersTopicMap.get(topic).remove(subscriber);
        } else {
            System.out.println("Topic  - " + topic + " +not found while unsubscribing");
        }
    }

    public int getCurrentOffsetForTopic(String topic){
        if(messageTopicMap.containsKey(topic)) {
            return messageTopicMap.get(topic).size();
        } else return 0;
    }

    public Message getMessageInTopicOnOffset(String topic, Integer offset, Subscriber subscriber){
        if(subscribersTopicMap.containsKey(topic) && subscribersTopicMap.get(topic).contains(subscriber)) {
            if (messageTopicMap.containsKey(topic)) {
                List<Message> allMessagesInTopic = messageTopicMap.get(topic);
                if (offset < allMessagesInTopic.size())
                    return allMessagesInTopic.get(offset);
                else return null;
            } else return null;
        } else return null;
    }

}
