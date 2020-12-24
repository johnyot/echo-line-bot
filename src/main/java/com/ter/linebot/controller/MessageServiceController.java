package com.ter.linebot.controller;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.ImagemapMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.VideoMessage;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.imagemap.ImagemapArea;
import com.linecorp.bot.model.message.imagemap.ImagemapBaseSize;
import com.linecorp.bot.model.message.imagemap.ImagemapExternalLink;
import com.linecorp.bot.model.message.imagemap.ImagemapVideo;
import com.linecorp.bot.model.message.imagemap.MessageImagemapAction;
import com.linecorp.bot.model.message.imagemap.URIImagemapAction;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.ImageCarouselColumn;
import com.linecorp.bot.model.message.template.ImageCarouselTemplate;
import com.linecorp.bot.model.message.template.Template;
import com.linecorp.bot.model.response.BotApiResponse;
import com.ter.linebot.controller.LineBotController.DownloadedContent;
import com.ter.linebot.model.ActionTemplate;
import com.ter.linebot.model.CustomCarouselTemplate;
import com.ter.linebot.model.MessageReq;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class MessageServiceController {

	static Logger log = LoggerFactory.getLogger(MessageServiceController.class.getName());

	@PostMapping(value = "/post/msg", consumes = "application/json", produces = "application/json")
	public BotApiResponse pushMessage(@RequestBody MessageReq messageReq)
			throws InterruptedException, ExecutionException {

		final LineMessagingClient client = LineMessagingClient.builder(messageReq.getChannelToken()).build();

		final TextMessage textMessage = new TextMessage(messageReq.getMessage());
		return pusheMessage(messageReq.getUserId(), messageReq.getChannelToken(), textMessage);
	}
	
//	@PostMapping(value = "/post/video", consumes = "application/json", produces = "application/json")
//	public BotApiResponse pushVideo(@RequestBody MessageReq messageReq)
//			throws InterruptedException, ExecutionException {
//		DownloadedContent mp4 = new DownloadedContent();
//		VideoMessage videoMessage = VideoMessage.builder()
//        .originalContentUrl(URI.create("https://www.youtube.com/watch?v=g48-PZZFrEY"))
//        .previewImageUrl(URI.create("https://i.redd.it/y809f4mdwqn01.jpg"))
//        .trackingId(trackingId)
//        .build());
////		ImagemapMessage videoMessage = ImagemapMessage
////                .builder()
////                .baseUrl( URI.create("https://i.redd.it/y809f4mdwqn01.jpg"))
////                .altText("This is an imagemap with video")
////                .baseSize(new ImagemapBaseSize(722, 1040))
////                .video(
////                        ImagemapVideo.builder()
////                                     .originalContentUrl(
////                                             URI.create("https://www.youtube.com/watch?v=g48-PZZFrEY"))
////                                     .previewImageUrl(
////                                    		 URI.create("https://i.redd.it/y809f4mdwqn01.jpg"))
////                                     .area(new ImagemapArea(40, 46, 952, 536))
////                                     .externalLink(
////                                             new ImagemapExternalLink(
////                                                     URI.create("https://example.com/see_more.html"),
////                                                     "See More")
////                                     )
////                                     .build()
////                )
////                .actions(singletonList(
////                        MessageImagemapAction.builder()
////                                             .text("NIXIE CLOCK")
////                                             .area(new ImagemapArea(260, 600, 450, 86))
////                                             .build()
////                ))
////                .build();
//
//		return pusheMessage(messageReq.getUserId(), messageReq.getChannelToken(), videoMessage);
//	}

	@PostMapping(value = "/post/template", consumes = "application/json", produces = "application/json")
	public BotApiResponse pushButtonTemplate(@RequestBody MessageReq messageReq) {

		URI imageUrl = URI.create("https://w5coaching.com/wp-content/uploads/2014/10/sales.jpg");
		ButtonsTemplate buttonsTemplate = new ButtonsTemplate(imageUrl, "My button sample", "Hello, my button",
				Arrays.asList(new URIAction("Go to line.me", URI.create("https://line.me"), null),
						new PostbackAction("Say hello1", "hello こんにちは"),
						new PostbackAction("言 hello2", "hello こんにちは", "hello こんにちは"),
						new MessageAction("Say message", "Rice=米")));
		TemplateMessage templateMessage = new TemplateMessage("Button alt text", buttonsTemplate);
		final PushMessage pushMessage = new PushMessage(messageReq.getUserId(), templateMessage);
		final LineMessagingClient client = LineMessagingClient.builder(messageReq.getChannelToken()).build();
		final BotApiResponse botApiResponse;
		try {
			botApiResponse = client.pushMessage(pushMessage).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return botApiResponse;
	}

	@PostMapping(value = "/post/card", consumes = "application/json", produces = "application/json")
	public BotApiResponse pushButtonTemplate2(@RequestBody MessageReq messageReq)
			throws InterruptedException, ExecutionException {

		List<ActionTemplate> actions = messageReq.getActions();
		List<Action> templateAction = getTemplateAction(actions);
		ButtonsTemplate buttonsTemplate = new ButtonsTemplate(URI.create(messageReq.getImageUrl()),
				messageReq.getCardTitle(), messageReq.getCardText(), templateAction);
		TemplateMessage templateMessage = new TemplateMessage("Button alt text", buttonsTemplate);
		return pusheMessage(messageReq.getUserId(), messageReq.getChannelToken(), templateMessage);
	}

	@PostMapping(value = "/post/cards", consumes = "application/json", produces = "application/json")
	public BotApiResponse pushCardCarouselTemplate(@RequestBody MessageReq messageReq)
			throws InterruptedException, ExecutionException {

		List<CustomCarouselTemplate> customCarouselTemplates = messageReq.getCards();
		List<CarouselColumn> carouseColumns = new ArrayList<>();
		for (CustomCarouselTemplate customCarouselTemplate : customCarouselTemplates) {
			List<Action> templateAction = getTemplateAction(customCarouselTemplate.getActions());
			CarouselColumn carouseColumn = new CarouselColumn(URI.create(customCarouselTemplate.getImageUrl()),
					customCarouselTemplate.getTitle(), customCarouselTemplate.getText(), templateAction);
			carouseColumns.add(carouseColumn);
		}

		CarouselTemplate carouselTemplate = new CarouselTemplate(carouseColumns);
		TemplateMessage templateMessage = new TemplateMessage("Template alt text", carouselTemplate);
		return pusheMessage(messageReq.getUserId(), messageReq.getChannelToken(), templateMessage);
	}
	
	@PostMapping(value = "/post/flex/buble", consumes = "application/json", produces = "application/json")
	public BotApiResponse pushFlexBubleMessage(@RequestBody MessageReq messageReq)
			throws InterruptedException, ExecutionException {
		FlexMessage flexMessage = new FlexMessage(messageReq.getAltTextFlex(), messageReq.getFlexBubble());
		return pusheMessage(messageReq.getUserId(), messageReq.getChannelToken(), flexMessage);
	}
	
	@PostMapping(value = "/post/flex/bubles", consumes = "application/json", produces = "application/json")
	public BotApiResponse pushFlexCarouselBubleMessage(@RequestBody MessageReq messageReq)
			throws InterruptedException, ExecutionException {
		List<Bubble> bubbles = messageReq.getFlexBubles();
		final Carousel carousel = Carousel.builder()
	                .contents(bubbles).build();
		FlexMessage flexMessage = new FlexMessage(messageReq.getAltTextFlex(), carousel);
		return pusheMessage(messageReq.getUserId(), messageReq.getChannelToken(), flexMessage);
	}

	private BotApiResponse pusheMessage(String userId, String channelToken, Message message)
			throws InterruptedException, ExecutionException {

		final PushMessage pushMessage = new PushMessage(userId, message);
		final LineMessagingClient client = LineMessagingClient.builder(channelToken).build();
		final BotApiResponse botApiResponse = client.pushMessage(pushMessage).get();

		return botApiResponse;
	}

	private List<Action> getTemplateAction(List<ActionTemplate> actionsTemplate) {
		List<Action> actions = new ArrayList<Action>();
		for (ActionTemplate item : actionsTemplate) {
			if (StringUtils.equalsIgnoreCase("message", item.getType())) {
				actions.add(new MessageAction(item.getLabel(), item.getData()));
			} else if (StringUtils.equalsIgnoreCase("uri", item.getType())) {
				actions.add(new URIAction(item.getLabel(), URI.create(item.getUri()), null));
			}
		}
		return actions;
	}

	// Image carousel template
	// https://developers.line.biz/en/reference/messaging-api/#image-carousel
	@PostMapping(value = "/post/corousel/images", consumes = "application/json", produces = "application/json")
	public BotApiResponse pushCorouselImages(@RequestBody MessageReq messageReq)
			throws InterruptedException, ExecutionException {

		ImageCarouselTemplate imageCarouselTemplate = new ImageCarouselTemplate(messageReq.getImages());
		TemplateMessage templateMessage = new TemplateMessage(messageReq.getTitle(), imageCarouselTemplate);

		return pusheMessage(messageReq.getUserId(), messageReq.getChannelToken(), templateMessage);
	}

	// carousel template
	// https://developers.line.biz/en/docs/messaging-api/message-types/#carousel-template
	// https://developers.line.biz/en/reference/messaging-api/#carousel
	@PostMapping(value = "/post/corousel/cards", consumes = "application/json", produces = "application/json")
	public BotApiResponse pushImageMap(@RequestBody MessageReq messageReq)
			throws InterruptedException, ExecutionException {

		CarouselTemplate carouselTemplate = new CarouselTemplate(messageReq.getCarousels());
		TemplateMessage templateMessage = new TemplateMessage(messageReq.getTitle(), carouselTemplate);

		return pusheMessage(messageReq.getUserId(), messageReq.getChannelToken(), templateMessage);
	}

}
