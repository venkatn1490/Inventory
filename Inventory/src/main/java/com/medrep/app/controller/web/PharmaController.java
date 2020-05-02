package com.medrep.app.controller.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.annotation.MultipartConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.medrep.app.model.Company;
import com.medrep.app.model.DisplayPicture;
import com.medrep.app.model.Location;
import com.medrep.app.model.TherapeuticArea;
import com.medrep.app.service.CompanyService;

import sun.misc.BASE64Encoder;
/**
 * Handles and retrieves the common or admin page depending on the URI template.
 * A user must be log-in first he can access these pages.  Only the admin can see
 * the adminpage, however.
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping("/web/admin")
@MultipartConfig(fileSizeThreshold=1024*100,    // 100 KB 
maxFileSize=1024*200,          // 200 KB
maxRequestSize=1024*200) 
public class PharmaController {
	
	@Autowired
	CompanyService companyService;
	/*@Autowired
	TherapeuticAreaService therapeuticAreaService;*/

	protected static Logger logger = Logger.getLogger("controller");
	public static Integer companyID;
	
	  
    /**
     * Handles and retrieves the admin JSP page that only admins can see
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/pharma-company.do", method = RequestMethod.GET)
    public ModelAndView getAdminPage() {
    	logger.debug("Received request to show admin page");
    
    	// Do your work here. Whatever you like
    	logger.debug("Received request to show admin page");
   	 ModelAndView mnv = new ModelAndView();
   	 mnv.addObject("pharmaCompaniesList",getPharmaCompaniesList());   
   	 mnv.addObject("therapeuticAreaList",getTherapeuticAreaList());
   	 if(companyID!=null)
   	 {
   		System.out.println("second get "+companyID);
   		mnv.addObject("pharmaCompany",getPharmaCompany(companyID.toString()));
   		companyID = null;
   	 }
  	 mnv.setViewName("/web/admin/pharma-company");   
   	 return mnv;
	}
    
    @RequestMapping(value = "/pharma-company.do", method = RequestMethod.POST)
    public ModelAndView pharmaRemove(@RequestParam("removeUpdate") String companyId,@RequestParam("buttonName") String buttonName){
    	System.out.println("Company id fetched"+companyId);
   	    ModelAndView mnv = new ModelAndView();
   	    String view = null;
    	if(buttonName.equals("Remove"))
    	{
    		companyService.alterPharmaStatusByCompanyId(Integer.parseInt(companyId), "Deleted");
    		view = "/web/admin/pharma-company";
    	}
    	else if(buttonName.equals("UpdateToTab"))
    	{
    		mnv.addObject("pharmaCompany",getPharmaCompany(companyId));
    		companyID = Integer.parseInt(companyId);
    		mnv.addObject("companyId", companyID);
    		view = "/web/admin/pharma-company";
    	}
    	logger.debug("Received request to show admin page");        
    	logger.debug("Received request to show admin page");
       	 mnv.addObject("pharmaCompaniesList",getPharmaCompaniesList()); 
       	 mnv.addObject("therapeuticAreaList",getTherapeuticAreaList());
      	 mnv.setViewName(view);    	 
   	 return mnv;
    }
    
   
	@RequestMapping(value = "/pharma-companyUpdate.do", method = RequestMethod.POST)
    public ModelAndView pharmaUpdate( @RequestParam("CompanyName") String CompanyName,
    								 @RequestPart("CompanyLogo") MultipartFile CompanyLogoImage,
    								 @RequestParam("LogoName") String CompanyLogoName,
    								 @RequestParam("CompanyDesc") String CompanyDesc,
    								 @RequestParam("CompanyUrl") String CompanyUrl,
    								 @RequestParam("CompanyAddress1") String CompanyAddress1,
    								 @RequestParam("CompanyAddress2") String CompanyAddress2,
    								 @RequestParam("CompanyCity") String CompanyCity,
    								 @RequestParam("CompanyState") String CompanyState,
    								 @RequestParam("CompanyCountry") String CompanyCountry,
    								 @RequestParam("CompanyZipcode") String CompanyZipcode,
    								 @RequestParam("CompanyContactName") String CompanyContactName,
    								 @RequestParam("CompanyContactNo") String CompanyContactNo,
    								 @RequestParam("CompanyTherapeuticAreas") List<String> CompanyTherapeuticAreas,
    								 @RequestParam("CompanyId") String CompanyId,
    								 @RequestParam("buttonName") String buttonName,Model model){
		 ModelAndView mnv = new ModelAndView();
		try{
		
		model.addAttribute("companyLogo", CompanyLogoImage);
		Company company = new Company();
		Location location = new Location();
		DisplayPicture displayPicture = new DisplayPicture();
		Set<TherapeuticArea> therapeuticAreas = new HashSet<TherapeuticArea>();
		
	
		company.setCompanyName(CompanyName);
		company.setCompanyDesc(CompanyDesc);
		company.setContactName(CompanyContactName);
		company.setContactNo(CompanyContactNo);
		company.setStatus("Active");
		company.setCompanyUrl(CompanyUrl);
			location.setAddress1(CompanyAddress1);
			location.setAddress2(CompanyAddress2);
			location.setCity(CompanyCity);
			location.setState(CompanyState);
			location.setCountry(CompanyCountry);
			location.setZipcode(CompanyZipcode);
		company.setLocation(location);
		
		for(String theoArea : CompanyTherapeuticAreas)
		{
			TherapeuticArea therapeuticArea = new TherapeuticArea();
			Integer therapeuticId = Integer.parseInt(theoArea);
			therapeuticArea.setTherapeuticId(therapeuticId);
			/*therapeuticArea.setTherapeuticName(therapeuticAreaService.findTherapeuticAreaById(therapeuticId).getTherapeuticName());
			therapeuticArea.setTherapeuticDesc(therapeuticAreaService.findTherapeuticAreaById(therapeuticId).getTherapeuticDesc());*/
			therapeuticAreas.add(therapeuticArea);
			System.out.println("From theo area object :"+therapeuticArea.getTherapeuticId());
		}
		company.setTherapeuticAreas(therapeuticAreas);
		if(buttonName.equals("Create"))
		{
			model.addAttribute("company",company);
			companyService.createPharmaCompany(model);
		}
		else if(buttonName.equals("Update"))
		{
			company.setCompanyId(Integer.parseInt(CompanyId));
			model.addAttribute("company",company);
			companyService.updatePharmaCompany(model);
		}
   	   
 	    logger.debug("Received request to show admin page");        
    	logger.debug("Received request to show admin page");
		}catch(Exception e){
			
		}
       	 mnv.addObject("pharmaCompaniesList",getPharmaCompaniesList()); 
       	 mnv.addObject("therapeuticAreaList",getTherapeuticAreaList());
      	 mnv.setViewName("redirect:/web/admin/pharma-company.do");    	 
   	 return mnv;
    }
	
	public static String encodeToString(BufferedImage image, String type) {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();

			@SuppressWarnings("restriction")
			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);

			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageString;
	}
    	
    
    public List<Company> getPharmaCompaniesList(){
    	List<Company> pharmaCompanies;
    	pharmaCompanies = companyService.fetchAllPharmaCompanies();
        return pharmaCompanies;        
    }
    
    public List<TherapeuticArea> getTherapeuticAreaList(){
    	List<TherapeuticArea> therapeuticAreas;
    	therapeuticAreas = companyService.fetchAllTherapeuticAreas();
        return therapeuticAreas;        
    }
    public Company getPharmaCompany(String Id){
    	Company pharmaCompany= new Company();
    	if(Id!=null)
    		pharmaCompany = companyService.fetchPharmaCompanyById(Id);
        return pharmaCompany;        
    }
    
    @RequestMapping(value = "/getCompany.do", method = RequestMethod.GET)
	public @ResponseBody Company getCompanyById(@RequestParam String companyId) {
    	return getPharmaCompany(companyId);
    }
       
}
