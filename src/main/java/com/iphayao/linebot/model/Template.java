package com.iphayao.linebot.model;

import java.util.List;

public class Template {
	private String type;
	private String thumbnailImageUrl;
	private String imageAspectRatio;
	private String imageSize;
	private String imageBackgroundColor;
	private String title;
	private String text;
	private DefaultAction defaultAction;
	private List<ActionTemplate> actions;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getThumbnailImageUrl() {
		return thumbnailImageUrl;
	}
	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		this.thumbnailImageUrl = thumbnailImageUrl;
	}
	public String getImageAspectRatio() {
		return imageAspectRatio;
	}
	public void setImageAspectRatio(String imageAspectRatio) {
		this.imageAspectRatio = imageAspectRatio;
	}
	public String getImageSize() {
		return imageSize;
	}
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	public String getImageBackgroundColor() {
		return imageBackgroundColor;
	}
	public void setImageBackgroundColor(String imageBackgroundColor) {
		this.imageBackgroundColor = imageBackgroundColor;
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
	public DefaultAction getDefaultAction() {
		return defaultAction;
	}
	public void setDefaultAction(DefaultAction defaultAction) {
		this.defaultAction = defaultAction;
	}
	public List<ActionTemplate> getActions() {
		return actions;
	}
	public void setActions(List<ActionTemplate> actions) {
		this.actions = actions;
	}
}