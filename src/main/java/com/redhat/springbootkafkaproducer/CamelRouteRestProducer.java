package com.redhat.springbootkafkaproducer;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteRestProducer extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		restConfiguration().component("servlet").host("0.0.0.0").port(3080).bindingMode(RestBindingMode.auto);
		
		rest("/messages/")
		    .post().type(String.class)
		    .to("direct:message");
		
		from("direct:message")
		.log("Received message: ${body}")
		.to("kafka:rest-topic?brokers=my-cluster-kafka-bootstrap:9092");
	}

}
