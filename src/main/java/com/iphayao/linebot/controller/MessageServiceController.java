package com.iphayao.linebot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iphayao.linebot.model.MessageReq;
import com.iphayao.linebot.model.MessageResp;

@RestController
@RequestMapping(value = "/v1")
public class MessageServiceController {

	@PostMapping(value="/postmsg", consumes = "application/json", produces = "application/json")
	public MessageResp newEmployee(@RequestBody MessageReq messageReq) {
		return new MessageResp();
	}
}
