package com.gc.spring;

import org.springframework.flex.config.MessageBrokerConfigProcessor;

import flex.messaging.MessageBroker;
import flex.messaging.services.RemotingService;

public class FlexConfigProcessor implements MessageBrokerConfigProcessor {

	public MessageBroker processAfterStartup(MessageBroker broker) {
		RemotingService service = (RemotingService) broker.getServiceByType(RemotingService.class.getName());
		if (service.isStarted()) {
			System.out.println("The Remoting Service has been started with " + service.getDestinations().size() + " Destinations!");
		}
		return broker;
	}

	public MessageBroker processBeforeStartup(MessageBroker broker) {
		return broker;
	}
}
