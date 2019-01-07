package telran.ashkelon2018.m2m.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.MessageChannel;

public interface IDispatcher extends Sink{
	String BIG_NUMBER = "bigNumber";
	String SMALL_NUMBER = "smallNumber";
	
	// channel's name = BIG_NUMBER, method's name = bigNumberChannel
	// if we don't indicate name -> auto: name = method
	@Output(BIG_NUMBER)
	MessageChannel bigNumberChannel();
	
	@Output(SMALL_NUMBER)
	MessageChannel smallNumberChannel();
}
