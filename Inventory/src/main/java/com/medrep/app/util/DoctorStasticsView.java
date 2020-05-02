
package com.medrep.app.util;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.medrep.app.model.DoctorNotification;
import com.medrep.app.model.NotificationStat;

public class DoctorStasticsView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,List<DoctorNotification>> reportMap = (Map<String,List<DoctorNotification>>) model.get("notificationstatistics");
		Map.Entry<String,List<DoctorNotification>> entry=reportMap.entrySet().iterator().next(); 
		HSSFSheet excelSheet = workbook.createSheet(entry.getKey());
		
		setExcelHeader(excelSheet);
		
		
		setExcelRows(excelSheet,entry.getValue());
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Notification's Name");
		excelHeader.createCell(1).setCellValue("Survey Name");
		excelHeader.createCell(2).setCellValue("Reminder Set?");
		excelHeader.createCell(3).setCellValue("Product Favourited?");
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<DoctorNotification> reportMap){
		int rowNum = 1;
		for (DoctorNotification docNotification : reportMap) {
			//create the row data
			HSSFRow row = excelSheet.createRow(rowNum++);
			row.createCell(0).setCellValue(docNotification.getDoctorName());
			row.createCell(1).setCellValue(docNotification.getViewCount());
			row.createCell(2).setCellValue(docNotification.getRemindMe());
			row.createCell(3).setCellValue(docNotification.getFavourite() !=null? "Y" : "N");
         }
		
	}
}