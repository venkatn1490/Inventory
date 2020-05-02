package com.venkat.app.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
	
	public static boolean isFileExists(String path){
		File f=new File(path);
		return !Util.isEmpty(path) && f.exists();
	}

	public static String copyImage(MultipartFile file, String directory) throws IOException {
		File dir=new File(directory);
		if(!dir.exists())
			dir.mkdirs();
		File image = new File(directory, file.getOriginalFilename());
		int i = 1;
		while (image.exists()) {
			String filename = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
			String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			image = new File(directory, filename + "_" + i + ext);
			i++;
		}
		FileOutputStream out = null;
		try {
			// save the image to filesystem
			image.createNewFile();
			out = new FileOutputStream(image);
			IOUtils.copy(file.getInputStream(), out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return image.getName();

	}

	private static void copyImage(String fileName, String group_img_data, String directory) {

		// TODO Auto-generated method stub

	}

	public static void delete(String fileName) throws IOException {
		File f=new File(fileName);
		if(f.exists())
		FileUtils.forceDelete(f);
	}

	public static String copyBinaryData(byte[] bytes, String directory, String fileName) throws IOException {
		File f = null;
		fileName=fileName.replaceAll("\\s","");
		FileOutputStream fout = null;
		try {
		File dir=new File(directory);
		if(!dir.exists())
			dir.mkdirs();
			f = new File(directory, fileName);
			int i = 1;
			String _file=fileName.substring(0,fileName.lastIndexOf(".")).trim();
			String _ext=fileName.substring(fileName.lastIndexOf("."));
			while (f.exists()) {
				f = new File(directory, _file + "_" + i + _ext);
				i++;
			}
			f.createNewFile();
			fout = new FileOutputStream(f);
			IOUtils.copy(new ByteArrayInputStream((Base64.decodeBase64(bytes))), new FileOutputStream(f));
		} finally {
			fout.close();
		}
		return f.getName();
	}

	public static String copyBytesToFile(byte[] bytes, String directory, String fileName) throws IOException {
		File f = null;
		fileName=fileName.replaceAll("\\s","");
		FileOutputStream fout = null;
		try {
		File dir=new File(directory);
		if(!dir.exists())
			dir.mkdirs();
			f = new File(directory, fileName);
			int i = 1;
			String _file=fileName.substring(0,fileName.lastIndexOf(".")).trim();
			String _ext=fileName.substring(fileName.lastIndexOf("."));
			while (f.exists()) {
				f = new File(directory, _file + "_" + i + _ext);
				i++;
			}
			f.createNewFile();
			fout = new FileOutputStream(f);
			IOUtils.copy(new ByteArrayInputStream(bytes), new FileOutputStream(f));
		} finally {
			fout.close();
		}
		return f.getName();
	}

	public static byte[] getBinaryData(String url){
		String localFilePath=url;
		if(url!=null&& url.startsWith("http")){
			String basePath=VenkatProperty.getInstance().getProperties("static.resources.url");
			localFilePath=VenkatProperty.getInstance().getProperties("shared.data")+File.separator+url.replace(basePath, "");
		}
		File localFile=new File(localFilePath);
		byte[] imageByteArray = null;
		if(localFile.exists()){
			InputStream iStream = null;
			try
			{
				imageByteArray = new byte[(int) localFile.length()];
				iStream = new FileInputStream(localFile);
				iStream.read(imageByteArray, 0, imageByteArray.length);

			} catch (Exception e)
			{
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
		}

		return imageByteArray;
	}
}