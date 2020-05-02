package com.medrep.app.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.medrep.app.model.News;
import com.medrep.app.model.NewsSource;
import com.medrep.app.service.CachingService;
import com.medrep.app.service.NewsService;
import com.medrep.app.service.TherapeuticAreaService;
import com.medrep.app.util.MedrepException;


@Controller
@RequestMapping("/web/admin")
@SessionAttributes( {"tareaList"})
public class NewsController {

	private static final String VIEW ="/web/admin/news";
	protected static Logger logger = Logger.getLogger("controller");

	@Autowired
	NewsService newsService;

	@Autowired
	TherapeuticAreaService therapeuticAreaService;

	@Autowired
	CachingService cachingService;
	/**
     * Handles and retrieves the admin JSP page that only admins can see
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/news.do", method = RequestMethod.GET)
    public String getNewsList(Model model) {
    	logger.debug("Received request to show admin page");
    try{
    	 setModelObjects(model, null, null);
    	 if(!model.containsAttribute("tareaList")) {
    	     model.addAttribute("tareaList", therapeuticAreaService.getAll());
    	 }
    }catch(MedrepException e){
    	model.addAttribute("newsmsg", e.getMessage());
    }
		return VIEW;
	}

    @RequestMapping(value = "/uploadNews.do", method = RequestMethod.POST)
    public String uploadNews(Model model,@ModelAttribute("newsFormObj") News news,HttpServletRequest request) {
    	logger.debug("Received request to show admin page");
    try{
    	 List<NewsSource> list = newsService.getAllNewsSources();
    	 if(list == null || list.isEmpty()) {
    		 setModelObjects(model, null, null);
    		 model.addAttribute("newsmsg", "Please create at least once Source");
    		 return VIEW;
    	 }
    	 news.setUserId((Integer)request.getSession().getAttribute("UserSecuritId"));
    	 newsService.uploadNews(news);
    	 setModelObjects(model, null, null);
    	 model.addAttribute("newsmsg", "Upload News Successfully");
    	 cachingService.clear();
    }catch(Exception e){
    	model.addAttribute("newsmsg", e.getMessage());
        }
		return VIEW;
	}

    @RequestMapping(value = "/uploadNewsSource.do", method = RequestMethod.POST)
    public String uploadNewsSource(Model model,@ModelAttribute("newsSourceFormObj") NewsSource newsSource,HttpServletRequest request) {
    	logger.debug("Received request to show admin page");
    try{
    	 newsSource.setUserId((Integer)request.getSession().getAttribute("UserSecuritId"));
    	 newsService.createNewsSource(newsSource);
    	 setModelObjects(model, null, null);
    	 model.addAttribute("newsmsg", "Create News Source Successfully");
    	 cachingService.clear();
    }catch(MedrepException e){
    	model.addAttribute("newsmsg", e.getMessage());
    }
		return VIEW;
	}

    @RequestMapping(value = "/getNewsModelData.do", method = RequestMethod.GET)
	public @ResponseBody News getNewsById(@RequestParam String newsId) {
    	return newsService.getNewsById(Integer.valueOf(newsId));
	}


    @RequestMapping(value = "/deleteNews.do", method = RequestMethod.POST)
   	public String deleteNews(Model model,@RequestParam String newsId) {
       	try{
       		newsService.deleteNews(newsId);
       		setModelObjects(model,null,null);
       		model.addAttribute("newsmsg", "News Removed Successfully");
       		cachingService.clear();
       	}catch(MedrepException me){
       		model.addAttribute("newsmsg", me.getMessage());
       	}
       	return VIEW;
   	}

    private void setModelObjects(Model model, News news ,NewsSource source)throws MedrepException{
		model.addAttribute("newsList", newsService.getAllNews(null));

		if(news == null) model.addAttribute("newsFormObj", new News());
		else model.addAttribute("newsFormObj",news);
		if(source == null) model.addAttribute("newsSourceFormObj", new NewsSource());
		else model.addAttribute("newsSourceFormObj",source);

		//Get all Sources
		model.addAttribute("newsSourcesList", newsService.getAllNewsSources());
	}


}
