package com.venkat.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public class FileIOUtil {

	
	public static final String NOTIFICATIONS_BASE_PATH = "venkat.notification.basepath";
	public static final String NEWS_BASE_PATH = "venkat.news.basepath";
	public static final String TRANSFORM_BASE_PATH = "venkat.transform.basepath";
	
	public byte[] readContent(String fileAbsolutePath) 
	{

		
		byte[] imageByteArray = null;
		File content = null;
		InputStream iStream = null;
		try 
		{
			content = new File(fileAbsolutePath);
			imageByteArray = new byte[(int) content.length()];
			iStream = new FileInputStream(content);
			iStream.read(imageByteArray, 0, imageByteArray.length);

		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if(iStream != null)
				{
					iStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return imageByteArray;

	}


	public String writeNotificationContent(File inputFile, String notificationId,String notificationDetailId) {

		String absolutePath = "";
		byte[] data = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try 
		{

			absolutePath = VenkatProperty.getInstance().getProperties(FileIOUtil.NOTIFICATIONS_BASE_PATH)  + File.separatorChar + notificationId +  File.separatorChar + notificationDetailId;
			File baseFolder = new File(absolutePath);
			if (!baseFolder.exists())
			{
				baseFolder.mkdirs();
			}
			
			data = new byte[(int) inputFile.length()];

			inputStream = new FileInputStream(inputFile);
			inputStream.read(data, 0, data.length);
			File outputFile = new File(absolutePath +  File.separatorChar + inputFile.getName());
			outputStream = new FileOutputStream(outputFile);
			outputStream.write(data);
			outputStream.flush();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				if(inputStream != null)
				{
					inputStream.close();
				}
				if(outputStream != null)
				{
					outputStream.close();
				}				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		return absolutePath +  File.separatorChar  + inputFile.getName();
	}
	
	public String writeNewsContent(File inputFile, String newsId) {

		String absolutePath = "";
		byte[] data = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try 
		{

			absolutePath = VenkatProperty.getInstance().getProperties(FileIOUtil.NEWS_BASE_PATH)  + File.separatorChar + newsId;
			File baseFolder = new File(absolutePath);
			if (!baseFolder.exists())
			{
				baseFolder.mkdirs();
			}
			
			data = new byte[(int) inputFile.length()];

			inputStream = new FileInputStream(inputFile);
			inputStream.read(data, 0, data.length);
			File outputFile = new File(absolutePath +  File.separatorChar + inputFile.getName());
			outputStream = new FileOutputStream(outputFile);
			outputStream.write(data);
			outputStream.flush();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				if(inputStream != null)
				{
					inputStream.close();
				}
				if(outputStream != null)
				{
					outputStream.close();
				}				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		return absolutePath +  File.separatorChar  + inputFile.getName();
	}
	
	public String writeTransformContent(File inputFile, String categoryId) {

		String absolutePath = "";
		byte[] data = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try 
		{

			absolutePath = VenkatProperty.getInstance().getProperties(FileIOUtil.TRANSFORM_BASE_PATH)  + File.separatorChar + categoryId;
			File baseFolder = new File(absolutePath);
			if (!baseFolder.exists())
			{
				baseFolder.mkdirs();
			}
			
			data = new byte[(int) inputFile.length()];

			inputStream = new FileInputStream(inputFile);
			inputStream.read(data, 0, data.length);
			File outputFile = new File(absolutePath +  File.separatorChar + inputFile.getName());
			outputStream = new FileOutputStream(outputFile);
			outputStream.write(data);
			outputStream.flush();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				if(inputStream != null)
				{
					inputStream.close();
				}
				if(outputStream != null)
				{
					outputStream.close();
				}				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		return absolutePath +  File.separatorChar  + inputFile.getName();
	}
	public static File convert(MultipartFile file) {
		File convFile = null;
		FileOutputStream fos = null;
		try 
		{
			convFile = new File(file.getOriginalFilename());
			convFile.createNewFile();
			fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.flush();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				if(fos != null)
				{
					fos.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return convFile;
	}	
	
	public static void main(String args[])
	 {
	 
	 FileIOUtil util = new FileIOUtil();
	 File inputImage = new File("C:\\Users\\Umar Ashraf\\Desktop\\Test Data\\1.png");
	 System.out.println("Reading the file" ); 
	 String path = util.writeNotificationContent(inputImage, "1","3");
	 System.out.println("Writing the file... @ " + path);
	
	 System.out.println("Image writing complete.."); 
	 }
	 
}
