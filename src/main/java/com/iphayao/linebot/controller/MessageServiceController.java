package com.iphayao.linebot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iphayao.linebot.model.MessageReq;
import com.iphayao.linebot.model.MessageResp;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

@RestController
@RequestMapping(value = "/v1")
public class MessageServiceController {

	@Value("${line.bot.channel-token}")
	private String chToken;
	
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

		System.out.println(botApiResponse);
		
		return botApiResponse;
	}
	
}
