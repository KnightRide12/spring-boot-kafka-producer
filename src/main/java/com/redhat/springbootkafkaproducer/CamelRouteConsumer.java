package com.redhat.springbootkafkaproducer;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hl7.HL7MLLPNettyDecoderFactory;
import org.apache.camel.component.hl7.HL7MLLPNettyEncoderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteConsumer extends RouteBuilder {

	@Bean
	private HL7MLLPNettyEncoderFactory hl7Encoder() {
	  HL7MLLPNettyEncoderFactory encoder = new HL7MLLPNettyEncoderFactory();
	  encoder.setCharset("iso-8859-1");
	  //encoder.setConvertLFtoCR(true);
	  return encoder;
	}
	
	@Bean
	private HL7MLLPNettyDecoderFactory hl7Decoder() {
	  HL7MLLPNettyDecoderFactory decoder = new HL7MLLPNettyDecoderFactory();
	  decoder.setCharset("iso-8859-1");
	  return decoder;
	}
	  
	
	@Override
	public void configure() throws Exception {
		from("kafka:my-topic?brokers=my-cluster-kafka-bootstrap:9092&groupId=demo-consumer")
    	  .log("Message received from my-topic: ${body}")
    	  .to("netty4:tcp://ae93ed7ea90f6497d88974370fb82f52-2119074969.us-east-2.elb.amazonaws.com:3280?sync=true&decoder=#hl7Decoder&encoder=#hl7Encoder");
	}
}
