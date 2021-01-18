package com.redhat.springbootkafkaproducer;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteRestConsumer extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("kafka:rest-topic?brokers=my-cluster-kafka-bootstrap:9092&groupId=demo-consumer")
		.log("Message received from rest-topic: ${body}")
		.setHeader(Exchange.HTTP_METHOD, simple("POST"))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		.to("http://rest-route-amq.apps.cluster-f610.sandbox1234.opentlc.com/messages");
		
	}

}
