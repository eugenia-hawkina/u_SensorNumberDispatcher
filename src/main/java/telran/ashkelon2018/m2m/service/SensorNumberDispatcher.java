package telran.ashkelon2018.m2m.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.ashkelon2018.m2m.dto.Sensor;

@EnableBinding(IDispatcher.class)
public class SensorNumberDispatcher {

	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	IDispatcher dispatcher;
	
	@Value("${min}")
	int min;
	
	@Value("${max}")
	int max;
	
	@StreamListener(IDispatcher.INPUT)
	public void getSensorData(String sensorJson) throws JsonParseException, JsonMappingException, IOException{
		Sensor sensor = mapper.readValue(sensorJson, Sensor.class);
		if(sensor.getData() < min) {
			dispatcher.smallNumberChannel()
				.send(MessageBuilder.withPayload(sensorJson).build());
			return;
		}
		if(sensor.getData() > max) {
			dispatcher.bigNumberChannel()
				.send(MessageBuilder.withPayload(sensorJson).build());
			return;
		}
		System.out.println(sensor);
	}
}
