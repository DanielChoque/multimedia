package com.sap.multimedia.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

import org.springframework.util.StringUtils;


@RestController
public class index {
	@Value("${dirFile.path}")
	private String dirf;
	
	@Value("${urlimage.path}")
	private String urlArray;
	
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
		@RequestMapping(value="/uploadMultipleFiles",method =RequestMethod.POST)
		@ResponseBody
		public String create(@RequestParam("file") MultipartFile[] file) {
			i++;
			System.out.println("uploadMultipleFiles:"+i);
			String dir=dirfd+"AVA-"+i;
			//Met_File met=new Met_File();
			Fun_File met = new  Fun_File();
			met.createDir(dir);
			for (MultipartFile multipartFile : file) {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				System.out.println("dir:" +dir);
				met.saveFile(dir+"/", multipartFile,fileName);
			}		
			System.out.println("da:"+file.length);
			return ""+file.length;
		}
		
		


		@RequestMapping(value="/dd",method =RequestMethod.GET)
		@ResponseBody
		public String creates() {

			return "ddd";
		}

}
