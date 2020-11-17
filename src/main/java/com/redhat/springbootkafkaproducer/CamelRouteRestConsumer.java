package com.redhat.springbootkafkaproducer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteRestConsumer extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("kafka:rest-topic?brokers=my-cluster-kafka-bootstrap:9092&groupId=demo-consumer")
		.log("Message received from rest-topic: ${body}");
		
	}

}
