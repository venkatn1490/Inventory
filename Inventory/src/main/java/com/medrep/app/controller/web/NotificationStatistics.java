package com.medrep.app.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class NotificationStatistics extends AbstractController{

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		String output =
			ServletRequestUtils.getStringParameter(request, "output");

		List<String> list = new ArrayList<String>();
		list.add("pinnaka");
		list.add("suresh");
		list.add("ddddddd");
		list.add("ddddddd");
		list.add("ddddddd");
		return new ModelAndView("AnimalListExcel", "animalList", list);

	}
}