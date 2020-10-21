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
    	  .log("Message received from Kafka: ${body}");
          //.setBody().constant("Hello4")
          //.to("jms:topic:demoQueue")
          //.log("Delivered to jms:topic:demoQueue")
          //.setBody().constant("Hello5")
          //.to("jms:topic:demoTopic.demoQueue")
          //.log("Delivered to jms:topic:demoTopic.demoQueue")
          //.setBody().constant("Hello6")
          //.to("jms:queue:demoTopic.demoQueue")
          //.log("Delivered to jms:queue:demoTopic.demoQueue");
		
		  //jmsTemplate.convertAndSend(destinationQueue, "Hello!!");
          

	}
}
