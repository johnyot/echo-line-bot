package com.iphayao.linebot.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iphayao.linebot.model.MessageReq;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class MessageServiceController {

	static Logger log = LoggerFactory.getLogger(MessageServiceController.class.getName());
	
	@PostMapping(value="/postmsg", consumes = "application/json", produces = "application/json")
	public BotApiResponse pushMessage(@RequestBody MessageReq messageReq) {
		
		final LineMessagingClient client = LineMessagingClient
		        .builder(messageReq.getChannelToken())
		        .build();

		final TextMessage textMessage = new TextMessage(messageReq.getMessage());
		final PushMessage pushMessage = new PushMessage(messageReq.getUserId(),  textMessage);

		final BotApiResponse botApiResponse;
		try {
		    botApiResponse = client.pushMessage(pushMessage).get();
		} catch (Exception e) {
		    e.printStackTrace();
		    return null;
		}

		log.info(ToStringBuilder.reflectionToString(botApiResponse));
		
		return botApiResponse;
	}
	
}
