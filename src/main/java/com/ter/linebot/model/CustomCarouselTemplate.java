package com.ter.linebot.model;

import java.util.List;

public class CustomCarouselTemplate {
	private String title;
	private String text;
	private String imageUrl;
	private List<ActionTemplate> actions;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
