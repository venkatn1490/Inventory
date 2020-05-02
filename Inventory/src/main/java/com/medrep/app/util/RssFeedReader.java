package com.medrep.app.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.medrep.app.model.News;
import com.medrep.app.model.NewsSource;
import com.medrep.app.model.TransformCategory;
import com.medrep.app.model.TransformModel;

public class RssFeedReader {

	public static void main(String[] args) {
	TransformCategory newsSource=new TransformCategory();
	newsSource.setCategoryUrl("http://www.nejm.org/action/showFeed?jc=nejm&type=etoc&feed=rss");
		/*	NewsSource obj = new NewsSource();
		obj.setSourceUrl(
				"https://www.medtechintelligence.com/category/combination-products/combination-products/feed/");
		obj.setSourceId(1);
		readNewsXml(obj);*/
		readTransformXml(newsSource);

	}

	public static List<News> readNewsXml(NewsSource newsSource) {
		List<News> newsList = new ArrayList<News>();
		try {
			URL url1 = new URL(newsSource.getSourceUrl());
			HttpURLConnection httpUrl = (HttpURLConnection) url1.openConnection();
			httpUrl.addRequestProperty("User-Agent", "Mozilla/4.76");

			// Reading stream returned by url
			BufferedReader in = new BufferedReader(new InputStreamReader(httpUrl.getInputStream()));
			String inputLine;
			// Creating XML String
			StringBuilder xmlString = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				xmlString.append(inputLine);
			}

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlString.toString()));
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("item");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				News feed = new News();

				Element element = (Element) nList.item(temp);
				NodeList title = element.getElementsByTagName("title");
				NodeList link = element.getElementsByTagName("link");
				NodeList description = element.getElementsByTagName("description");
				NodeList content = element.getElementsByTagName("content:encoded");

				Element titleEle = (Element) title.item(0);
				Element linkEle = (Element) link.item(0);
				Element descriptionEle = (Element) description.item(0);
				Element contentEle = (Element) content.item(0);

				feed.setTitle(checkForNullAndEncoded(titleEle.getFirstChild().getNodeValue()));
				feed.setTagDesc(checkForNullAndEncoded(descriptionEle.getFirstChild().getNodeValue()));
				feed.setPostUrl(checkForNullAndEncoded(linkEle.getFirstChild().getNodeValue()));
				String desc = "";
				if (contentEle != null) {
					desc = checkForNullAndEncoded(contentEle.getFirstChild().getNodeValue());
				}
				if (desc.length() > 50000) {
					desc = desc.substring(0, 49999);
				}
				if(Util.isEmpty(desc))
					desc=feed.getTagDesc();
				feed.setNewsDesc(desc);
				feed.setSourceId(newsSource.getSourceId());

				newsList.add(feed);
			}

		} catch (Exception e) {
			e.printStackTrace();
			newsList = new ArrayList<News>();
		}
		return newsList;
	}

	public static List<TransformModel> readTransformXml(TransformCategory newsSource) {
		List<TransformModel> newsList = new ArrayList<TransformModel>();
		try {
			URL url1 = new URL(newsSource.getCategoryUrl());
			HttpURLConnection httpUrl = (HttpURLConnection) url1.openConnection();
			httpUrl.addRequestProperty("User-Agent", "Mozilla/4.76");

			// Reading stream returned by url
			BufferedReader in = new BufferedReader(new InputStreamReader(httpUrl.getInputStream()));
			String inputLine;
			// Creating XML String
			StringBuilder xmlString = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				xmlString.append(inputLine);
			}

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlString.toString()));
//			System.out.println(xmlString.toString());
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			 NodeList nList = getElementsByTagName(doc.getDocumentElement(),"item");
			 System.out.println(nList.getLength());
			 for (int temp = 0; temp < nList.getLength(); temp++) {
				TransformModel feed = new TransformModel();

				Element element = (Element) nList.item(temp);
				NodeList title = getElementsByTagName(element,"title");
				NodeList link = getElementsByTagName(element,"link");
				NodeList description = getElementsByTagName(element,"description");
				NodeList content =getElementsByTagName(element,"content:encoded");

				Element titleEle = (Element) title.item(0);
				Element linkEle = (Element) link.item(0);
				Element descriptionEle = (Element) description.item(0);
				Element contentEle = (Element) content.item(0);

				feed.setTitle(Util.subString(checkForNullAndEncoded(titleEle.getFirstChild().getNodeValue()),150));
				if(!Util.isEmpty(descriptionEle)&& !Util.isEmpty(descriptionEle.getFirstChild()))
				feed.setTagDesc(checkForNullAndEncoded(descriptionEle.getFirstChild().getNodeValue()));

				if(Util.isEmpty(feed.getTagDesc())){
					feed.setTagDesc(feed.getTitle());
				}
				feed.setPostUrl(checkForNullAndEncoded(linkEle.getFirstChild().getNodeValue()));
				String desc = "";
				if (contentEle != null) {
					desc = checkForNullAndEncoded(contentEle.getFirstChild().getNodeValue());
				}
				if (desc.length() > 50000) {
					desc = desc.substring(0, 49999);
				}

				if(Util.isEmpty(desc))
					desc=feed.getTagDesc();
				feed.setTransformDesc(desc);
				feed.setSourceId(newsSource.getCategoryId());

				newsList.add(feed);
			}

		} catch (Exception e) {
			System.out.println("Unable to get FEEDS::"+newsSource.getCategoryName()+"::"+newsSource.getCategoryUrl());
			e.printStackTrace();
			newsList = new ArrayList<TransformModel>();
		}
		return newsList;
	}

	private static NodeList getElementsByTagName(Element element, String tagName) {
		NodeList nodeList=element.getElementsByTagName(tagName);
		if(nodeList.getLength()==0)
			nodeList=element.getElementsByTagName("rss:"+tagName);
		return nodeList;
	}

	private static String checkForNullAndEncoded(String str) {
		if (str != null) {
			if (str.contains("CDATA")) {
				str = str.substring(9, str.indexOf("]]>"));
			}
			return removeHTMLtags(str).trim();
//			return str.trim();
		} else {
			return "";
		}
	}

	private static String removeHTMLtags(String str) {
		return html2text(str);
	}

	public static String html2text(String html) {
		return Jsoup.parse(html).text();
	}
}
