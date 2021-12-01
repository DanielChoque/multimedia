package com.sap.multimedia.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.sap.multimedia.function.Fun_File;
import com.sap.multimedia.function.Funcion;

import org.springframework.util.StringUtils;


@RestController
public class index {
	@Value("${dirFile.path}")
	private String dirf;
	 
	@Value("${dirFilelocal.path}")
	private String dirl;
	
	@Value("${urlimage.path}")
	private String urlArray;
	
	//String urlserver="http://10.1.26.93:8080/ssp/faces/secure/gestionMultimediaAdministracionVideosImagenes.xhtml";
	//String urlserver="http://10.1.26.93:8080/registro-igf/resources/ssac/configvideo.xhtml";
	//String urlserver="http://10.1.26.93:8080/ssp/faces/secure/gestionMultimediaAdministracionVideosImagenes.xhtml";
	String urlserver="https://desasiat.impuestos.gob.bo/ssp/secure/gestionMultimediaAdministracionVideosImagenes.xhtml";
	//String urlserver="http://desasiat.impuestos.gob.bo/ssp/faces/secure/gestionMultimediaAdministracionVideosImagenes.xhtml";
	//@CrossOrigin(origins = "${client.url}")
	@CrossOrigin
	@RequestMapping("/r")
	public String userR() {	
		File dir = new File(dirf);
		String[] ficheros = dir.list();
		JSONArray Obj = new JSONArray();		
		    for(int i = 0; i < ficheros.length; i++) {
		        JSONObject list1 = new JSONObject();
		        list1.put("num",i+1);
		        list1.put("name",ficheros[i]);
		        list1.put("url",urlArray);
		        list1.put("size","100MB");
		        Obj.put(list1);
		    }
		    System.out.println("Obj:"+Obj);		
		return ""+Obj;
	}
	
	 @GetMapping("/downloadFile/{fileName:.+}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		 System.out.println(dirf+" : "+fileName);
		 Path fileStorageLocation= Paths.get(dirf).toAbsolutePath().normalize();
		 Path filePath = fileStorageLocation.resolve(fileName).normalize();
		 try {
			Resource resource = new UrlResource(filePath.toUri());
			String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			System.out.println("image");
			return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//System.out.println("image url:"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("image io:"+e.getMessage());
			e.printStackTrace();
		}
		 
		 return ResponseEntity.noContent().build();		 
	 }
	 
		@Value("${dirFileU.path}")
		private String dirfd;
		int i=0;
		@CrossOrigin
		@RequestMapping(value="/uploadMultipleFiles",method =RequestMethod.POST)
		@ResponseBody
		public String create(@RequestParam("file") MultipartFile[] file) {
			int i=0;
			i++;
			
			String dir="C:/video/";
			//Met_File met=new Met_File();
			Fun_File met = new  Fun_File();
			try {
				met.del(dir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			met.createDir(dir);
			System.out.println("dadddd");
			System.out.println("uploadMultipleFiles:"+i);
			for (MultipartFile multipartFile : file) {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				System.out.println("dir:" +dir);
				met.saveFile(dir+"/", multipartFile,fileName);
			}		
			System.out.println("da:"+file.length);
			return "<html>\r\n"
					+ "<head>\r\n"
					+ "<title>Cultura Tributaria</title>\r\n"
					+ "<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0.04;URL="
					+ urlserver
					+ "\">\r\n"
					+ "</head>\r\n"
					+ "<body>\r\n"
					+ "window.location.reload (true)\r\n"
					+ "</body>\r\n"
					+ "</html>";
		}
		
		@CrossOrigin
		@RequestMapping(value="/uploadMultipleimg",method =RequestMethod.POST)
		@ResponseBody
		public String createimg(@RequestParam("file") MultipartFile[] file) {
			int i=0;
			i++;
			
			String dir="C:/img/";
			//Met_File met=new Met_File();
			Fun_File met = new  Fun_File();
			try {
				met.del(dir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			met.createDir(dir);
			System.out.println("dadddd");
			System.out.println("uploadMultipleFiles:"+i);
			for (MultipartFile multipartFile : file) {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				String nameAux ="imgti.jpg";
 				System.out.println("dir:" +dir);
				met.saveFile(dir+"/", multipartFile,nameAux);
			}		
			System.out.println("da:"+file.length);
			return "<html>\r\n"
					+ "<head>\r\n"
					+ "<title>Cultura Tributaria</title>\r\n"
					+ "<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0.04;URL="
					+ urlserver
					+ "\">\r\n"
					+ "</head>\r\n"
					+ "<body>\r\n"
					+ "window.location.reload (true)\r\n"
					+ "</body>\r\n"
					+ "</html>";
		}


		@RequestMapping(value="/dd",method =RequestMethod.GET)
		@ResponseBody
		public String creates() {

			return "ddd";
		}
		
		
		 @GetMapping("/downloadFilelocal/{fileName:.+}")
		    public ResponseEntity<Resource> downloadFilelocal(@PathVariable String fileName, HttpServletRequest request) {
			 System.out.println(dirl+" : "+fileName);
			 Path fileStorageLocation= Paths.get(dirl).toAbsolutePath().normalize();
			 Path filePath = fileStorageLocation.resolve(fileName).normalize();
			 try {
				Resource resource = new UrlResource(filePath.toUri());
				String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
				System.out.println("image");
				return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(contentType))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		                .body(resource);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				//System.out.println("image url:"+e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("image io:"+e.getMessage());
				e.printStackTrace();
			}
			 
			 return ResponseEntity.noContent().build();		 
		 }
		 
		 @GetMapping("/downloadFilelocalimg/{fileName:.+}")
		    public ResponseEntity<Resource> downloadFilelocalimg(@PathVariable String fileName, HttpServletRequest request) {
			 String dirl="C:/img"; 
			 System.out.println(dirl+" : "+fileName);
			 Path fileStorageLocation= Paths.get(dirl).toAbsolutePath().normalize();
			 Path filePath = fileStorageLocation.resolve(fileName).normalize();
			 try {
				Resource resource = new UrlResource(filePath.toUri());
				String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
				System.out.println("image");
				return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(contentType))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		                .body(resource);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				//System.out.println("image url:"+e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("image io:"+e.getMessage());
				e.printStackTrace();
			}
			 
			 return ResponseEntity.noContent().build();		 
		 }
		 
		 
		 @GetMapping("/downloadFilelocalAudio/{fileName:.+}")
		    public ResponseEntity<Resource> downloadFilelocalAudio(@PathVariable String fileName, HttpServletRequest request) {
			 String dirl="D:/local/assets/audio"; 
			 System.out.println(dirl+" : "+fileName);
			 Path fileStorageLocation= Paths.get(dirl).toAbsolutePath().normalize();
			 Path filePath = fileStorageLocation.resolve(fileName).normalize();
			 try {
				Resource resource = new UrlResource(filePath.toUri());
				String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
				System.out.println("image");
				return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(contentType))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		                .body(resource);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				//System.out.println("image url:"+e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//System.out.println("image io:"+e.getMessage());
				e.printStackTrace();
			}
			 
			 return ResponseEntity.noContent().build();		 
		 }
		 
		 //uploadFile
			@CrossOrigin
			@RequestMapping(value="/uploadFile",method =RequestMethod.POST)
			@ResponseBody
			public String createFile(@RequestParam("file") MultipartFile[] file) {
				int i=0;
				i++;
				
				String dir="C:/video/";
				//Met_File met=new Met_File();
				Fun_File met = new  Fun_File();
				/*try {
					met.del(dir);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				if(dir.equals(null)) {
					met.createDir(dir);					
				}
				
				System.out.println("dadddd");
				System.out.println("uploadMultipleFiles:"+i);
				for (MultipartFile multipartFile : file) {
					String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
					System.out.println("dir:" +dir);
					met.saveFile(dir+"/", multipartFile,fileName);
				}
				System.out.println("da:"+file.length);
				return "<html>\r\n"
						+ "<head>\r\n"
						+ "<title>Cultura Tributaria</title>\r\n"
						+ "<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0.04;URL="
						+ urlserver
						+ "\">\r\n"
						//+"<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0.04;URL=http://10.1.26.162:8080/jsf/login.xhtml\">\r\n"
						+ "</head>\r\n"
						+ "<body>\r\n"
						+ "window.location.reload (true)\r\n"
						+ "</body>\r\n"
						+ "</html>";
			}
			//uploadFile
			@CrossOrigin
			@RequestMapping(value="/deleteFile/{name}",method =RequestMethod.POST)
			@ResponseBody
			public String deleteFile(@RequestParam("name") String name) {
				int i=0;
				i++;
				
				String dir="C:/video/";
				//Met_File met=new Met_File();
				Fun_File met = new  Fun_File();
				
				try {
					Fun_File.delFile(dir+name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*if(dir.equals(null)) {
					met.createDir(dir);					
				}*/
				
				System.out.println("delete:"+name);
				/*for (MultipartFile multipartFile : file) {
					String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
					System.out.println("dir:" +dir);
					met.saveFile(dir+"/", multipartFile,fileName);
				}
				System.out.println("da:"+file.length);*/
				return "<html>\r\n"
						+ "<head>\r\n"
						+ "<title>Cultura Tributaria</title>\r\n"
						+ "<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0.04;URL="
						+ urlserver
						+ "\">\r\n"
						//+"<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0.04;URL=http://10.1.26.162:8080/jsf/login.xhtml\">\r\n"
						+ "</head>\r\n"
						+ "<body>\r\n"
						+ "window.location.reload (true)\r\n"
						+ "</body>\r\n"
						+ "</html>";
			}
			@CrossOrigin
			@RequestMapping("/search/{name}")
			public String uc(@PathVariable("name") String name) {

				System.out.println("nameSearch:"+name);
				String dir="C:/video/";

				Fun_File met = new  Fun_File();
				
				try {
					met.delFile(dir+name);
				} catch (IOException e) {

					e.printStackTrace();
				}
				
				System.out.println("delete:"+name);
				
				return "<html>\r\n"
						+ "<head>\r\n"
						+ "<title>Cultura Tributaria</title>\r\n"
						+ "<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0.04;URL="
						+ urlserver
						+ "\">\r\n"
						//+"<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"0.04;URL=http://10.1.26.162:8080/jsf/login.xhtml\">\r\n"
						+ "</head>\r\n"
						+ "<body>\r\n"
						+ "window.location.reload (true)\r\n"
						+ "</body>\r\n"
						+ "</html>";
			}
			
			
			@CrossOrigin
			@RequestMapping("/p")
			public String pending() throws ClassNotFoundException, SQLException {	
				Funcion fun=new Funcion();
				return ""+fun.showPending();
			}
			/*------Pruebas------*/
			int sec=1;
			JSONArray Obj = new JSONArray();
			@CrossOrigin
			@RequestMapping("/pp")
			public String pendingPrueba(){
				if(sec>=15) {
					sec=1;
					Obj = new JSONArray();}
				Obj = new JSONArray();
				JSONObject list1 = new JSONObject();			
		        list1.put("puntoid","203000001000004"+sec);
		        list1.put("area","203000003"+sec);
		        list1.put("ticket","KTB-00"+sec);
		        list1.put("punto","V"+sec);
		        list1.put("abr","KTB");
		        list1.put("nume",sec);
		        list1.put("estado","aten");
		        Obj.put(list1);
		        sec++;
		        return ""+Obj;
			}
			
			
			/*------Pruebas2------*/
			int sec2=1;
			JSONArray Obj2 = new JSONArray();
			@CrossOrigin
			@RequestMapping("/pru/{fileName}")
			public String pendingPrueba2(@PathVariable String fileName){
				if(sec2>=40) {
					sec2=1;
					Obj = new JSONArray();}
				Obj = new JSONArray();
				JSONObject list1 = new JSONObject();			
		        list1.put("puntoid","203000001000004"+sec2);
		        list1.put("area","203000003"+sec2);
		        list1.put("ticket","KTB-00"+sec2);
		        list1.put("punto","V"+sec2);
		        list1.put("abr","KTB");
		        list1.put("nume",sec2);
		        list1.put("estado","aten");
		        Obj.put(list1);
		        sec2++;
		        return ""+Obj;
			}
}
