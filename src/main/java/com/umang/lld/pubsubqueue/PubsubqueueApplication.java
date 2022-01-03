package com.umang.lld.pubsubqueue;

import com.umang.lld.pubsubqueue.publisher.Publisher;
import com.umang.lld.pubsubqueue.service.PubSubService;
import com.umang.lld.pubsubqueue.subscriber.Subscriber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class PubsubqueueApplication {

	public static void main(String[] args) {

		ApplicationContext context =  SpringApplication.run(PubsubqueueApplication.class, args);
		System.out.println("Pub Sub Service Started");
		PubSubService service = context.getBean(PubSubService.class);


		Publisher firstPublisher = new Publisher();
		Publisher secondPublisher = new Publisher();
		firstPublisher.publish("Virat", new Message("Virat is the best batsman in world"), service);


		Subscriber umang = new Subscriber("Umang", service);
		Subscriber ramesh = new Subscriber("Ramesh", service);

		umang.addSubscriber("Shahrukh");
		ramesh.addSubscriber("Virat");

		firstPublisher.publish("Shahrukh", new Message("Sharuck is the king of bollywood"), service);
		secondPublisher.publish("Virat", new Message("Virat is my favourite 1"), service);
		secondPublisher.publish("Virat", new Message("Virat is my favourite 2"), service);
		secondPublisher.publish("Virat", new Message("Virat is my favourite 3"), service);
		secondPublisher.publish("Virat", new Message("Virat is my favourite 4"), service);



		printMessage(ramesh.consumeNextMessage("Virat"));
		printMessage(ramesh.consumeNextMessage("Virat"));
		printMessage(ramesh.consumeNextMessage("Virat"));
		ramesh.unSubscribe("Virat");

		printMessage(ramesh.consumeNextMessage("Virat"));
		printMessage(ramesh.consumeNextMessage("Virat"));
		printMessage(ramesh.consumeNextMessage("Virat"));
		printMessage(umang.consumeNextMessage("Shahrukh"));



	}

	public static void printMessage(Message message){
		if(message != null) {
			System.out.println(message.getPayload());
		} else System.out.println("null");
	}

}
