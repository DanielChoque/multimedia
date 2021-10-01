package com.sap.multimedia.function;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class Fun_File {
	public boolean saveImage(String ruta,String image64,String name) {
		//String string = image;
		//System.out.println("image64: "+image);
		//String[] parts = string.split("::");
		
		 OutputStream opStream = null;
	        try {
	        	//String name = parts[0];
				//String image64=parts[1];
				System.out.println("name"+name+"  image64: "+image64);
	            byte[] byteContent = Base64.getDecoder().decode(image64);;
	                                 ////"E:/avaluos/dani.jpg"+
				File myFile = new File(ruta+name);
	            // check if file exist, otherwise create the file before writing
	            if (!myFile.exists()) {
	                myFile.createNewFile();
	            }
	            opStream = new FileOutputStream(myFile);
	            opStream.write(byteContent);
	            opStream.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        } finally{
	            try{
	                if(opStream != null) opStream.close();
	            } catch(Exception ex){
	            	ex.printStackTrace();
	            	return false; 
	            }
	        }
	        return true;
		
		
	}	
	public boolean saveFile(String ruta,MultipartFile file,String name) {
		 OutputStream opStream = null;
	        try {			
				File myFile = new File(ruta+name);
	            // check if file exist, otherwise create the file before writing
	            if (!myFile.exists()) {
	                myFile.createNewFile();
	            }
	            opStream = new FileOutputStream(myFile);
	            opStream.write(file.getBytes());
	            opStream.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        } finally{
	            try{
	                if(opStream != null) opStream.close();
	            } catch(Exception ex){
	            	ex.printStackTrace();
	            	return false; 
	            }
	        }
	        return true;		
	}
	public boolean createDir(String dirFolder) {		
		boolean directorio = new File(dirFolder).mkdirs();		
		return directorio;
	}
	
}
