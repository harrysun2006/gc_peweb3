package com.gc.spring;

import org.springframework.flex.core.MessageInterceptor;
import org.springframework.flex.core.MessageProcessingContext;

import flex.messaging.messages.Message;

public class FlexMessageInterceptor implements MessageInterceptor {

	public Message postProcess(MessageProcessingContext arg0, Message arg1, Message arg2) {
		return null;
	}

	public Message preProcess(MessageProcessingContext arg0, Message arg1) {
		return null;
	}

}
