package com.iphayao.linebot.model;

import java.util.List;

public class MessageReq {
	private String channelToken;
	private String userId;
	private String message;
	private String cardTitle;
	private String cardText;
	private String imageUrl;
	private List<ActionTemplate> actions;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getChannelToken() {
		return channelToken;
	}

	public void setChannelToken(String channelToken) {
		this.channelToken = channelToken;
	}

	public List<ActionTemplate> getActions() {
		return actions;
	}

	public void setActions(List<ActionTemplate> actions) {
		this.actions = actions;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public void setCardTitle(String cardTitle) {
		this.cardTitle = cardTitle;
	}

	public String getCardText() {
		return cardText;
	}

	public void setCardText(String cardText) {
		this.cardText = cardText;
	}
}
