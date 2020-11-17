package com.redhat.springbootkafkaproducer;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteRestProducer extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		restConfiguration().component("servlet").host("localhost").port(8080).contextPath("/rest").bindingMode(RestBindingMode.auto);
		
		rest("/messages/")
		    .post().route()
		    .to("direct:message");
		
		from("direct:message")
		.log("Received message: ${body}")
		.to("kafka:rest-topic?brokers=my-cluster-kafka-bootstrap:9092");
	}

}
