package com.medrep.app.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medrep.app.util.AndroidPushNotification;

@RestController
@RequestMapping("/push")
public class PushController {

	@RequestMapping("/android")
	public String push(@RequestParam String regId,@RequestParam String message){
		try{
		AndroidPushNotification.push(regId, message);
		}catch(Exception e){
			return e.getMessage();
		}
		return "pushed";
	}

}
