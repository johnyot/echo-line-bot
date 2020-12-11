package com.iphayao.linebot.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.iphayao.linebot.model.ActionTemplate;
import com.iphayao.linebot.model.MessageReq;
import com.iphayao.linebot.model.MessageTemplateReq;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
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
	
	@PostMapping(value="/posttemplate", consumes = "application/json", produces = "application/json")
	public BotApiResponse pushButtonTemplate(@RequestBody MessageReq messageReq) {
		
		 URI imageUrl = URI.create("https://w5coaching.com/wp-content/uploads/2014/10/sales.jpg");
         ButtonsTemplate buttonsTemplate = new ButtonsTemplate(
                 imageUrl,
                 "My button sample",
                 "Hello, my button",
                 Arrays.asList(
                         new URIAction("Go to line.me",
                                       URI.create("https://line.me"), null),
                         new PostbackAction("Say hello1",
                                            "hello こんにちは"),
                         new PostbackAction("言 hello2",
                                            "hello こんにちは",
                                            "hello こんにちは"),
                         new MessageAction("Say message",
                                           "Rice=米")
                 ));
         TemplateMessage templateMessage = new TemplateMessage("Button alt text", buttonsTemplate);
         final PushMessage pushMessage = new PushMessage(
        		 messageReq.getUserId(),
        	        templateMessage);
         final LineMessagingClient client = LineMessagingClient
 		        .builder(messageReq.getChannelToken())
 		        .build();
         final BotApiResponse botApiResponse;
         try {
 		    botApiResponse = client.pushMessage(pushMessage).get();
 		} catch (Exception e) {
 		    e.printStackTrace();
 		    return null;
 		}
		
		return botApiResponse;
	}
	
	@PostMapping(value="/postcard", consumes = "application/json", produces = "application/json")
	public BotApiResponse pushButtonTemplate2(@RequestBody MessageReq messageReq) {
		
		List<ActionTemplate> actions = messageReq.getActions();
		List<Action> templateAction = getTemplateAction(actions);
         ButtonsTemplate buttonsTemplate = new ButtonsTemplate(
        		 URI.create(messageReq.getImageUrl()),
        		 messageReq.getCardTitle(),
        		 messageReq.getCardText(),
                 templateAction);
         TemplateMessage templateMessage = new TemplateMessage("Button alt text", buttonsTemplate);
         final PushMessage pushMessage = new PushMessage(
        		 messageReq.getUserId(),
        	        templateMessage);
         final LineMessagingClient client = LineMessagingClient
 		        .builder(messageReq.getChannelToken())
 		        .build();
         final BotApiResponse botApiResponse;
         try {
 		    botApiResponse = client.pushMessage(pushMessage).get();
 		} catch (Exception e) {
 		    e.printStackTrace();
 		    return null;
 		}
		
		return botApiResponse;
	}
	
	private List<Action> getTemplateAction(List<ActionTemplate> actionsTemplate) {
		List<Action> actions = new ArrayList<Action>();
		for (ActionTemplate item : actionsTemplate) {
			if(StringUtils.equalsIgnoreCase("message", item.getType())) {
				actions.add(new MessageAction(item.getLabel(),item.getData()));
			}else if(StringUtils.equalsIgnoreCase("uri", item.getType())) {
				actions.add( new URIAction(item.getLabel(),
                        URI.create(item.getUri()), null));
			}
		}
		return actions;
	}

	private static URI createUri(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                                          .scheme("https")
                                          .path(path).build()
                                          .toUri();
    }
	
}
