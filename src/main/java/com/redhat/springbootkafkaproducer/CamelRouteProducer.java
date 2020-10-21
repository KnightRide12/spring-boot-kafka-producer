package com.redhat.springbootkafkaproducer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hl7.HL7;
import org.apache.camel.component.hl7.HL7MLLPNettyDecoderFactory;
import org.apache.camel.component.hl7.HL7MLLPNettyEncoderFactory;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteProducer extends RouteBuilder {

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
		from("netty4:tcp://0.0.0.0:3180?sync=true&decoder=#hl7Decoder&encoder=#hl7Encoder")
    	  .process(new Processor() {

			@Override
			public void process(Exchange arg0) throws Exception {
				log.info("Received: " + arg0.getIn().getBody(String.class));
			}
    	  })
          //.setBody().constant("Hello1")
    	  //.to("jms:queue:demoQueue")
          //.log("Delivered to jms:queue:demoQueue")
          //.setBody().constant("Hello2")
          //.to("jms:topic:demoTopic")
          //.log("Delivered to jms:topic:demoTopic")
          //.setBody().constant("Hello3")
    	  //.convertBodyTo(String.class)
    	  .setHeader(KafkaConstants.KEY, constant("Demo"))
    	  .to("kafka:my-topic?brokers=my-cluster-kafka-bootstrap:9092")
    	  .transform(HL7.ack());
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
