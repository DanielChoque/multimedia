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
	
		public static void del(String filepath) throws IOException {
			 File f = new File (filepath); // define la ruta del archivo
			 if (f.exists () && f.isDirectory ()) {// determina si es un archivo o un directorio
				 if (f.listFiles (). length == 0) {// Si no hay archivos en el directorio, elimínelos directamente
					f.delete();
				 } else {// Si lo hay, coloque el archivo en la matriz y determine si hay un directorio subordinado
					File delFile[] = f.listFiles();
					int i = f.listFiles().length;
					for (int j = 0; j < i; j++) {
						if (delFile[j].isDirectory()) {
							 del (delFile [j] .getAbsolutePath ()); // Llame al método del de forma recursiva y obtenga la ruta del subdirectorio
						}
						 delFile [j] .delete (); // eliminar un archivo
					}
				}
			}
	
		
		
	}
		
		public static void delFile(String filepath) throws IOException {
			// File f = new File (filepath); // define la ruta del archivo
			 File fichero = new File(filepath);
			 if (fichero.delete())
				   System.out.println("El fichero ha sido borrado satisfactoriamente");
				else
				   System.out.println("El fichero no puede ser borrado");

	}
}
