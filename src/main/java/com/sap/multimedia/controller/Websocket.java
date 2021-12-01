package com.sap.multimedia.controller;

import javax.annotation.PostConstruct;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Websocket implements Runnable {
	@PostConstruct
	public void configurar() {
		webSocketSer();
	}
	
	
	
	//@RequestMapping("/ser")
	public String webSocketSer(){		
		Thread mihilo=new Thread(this);
		mihilo.start();
		return "socket Server";
	}	
	
	@CrossOrigin
	@RequestMapping("/pruebas/{name}")
	public String pru(@PathVariable("name") String name) {
		return "daniel"+name;
	}

	
	@Override
	public void run() {
		try {
			ServerSocket servidor=new ServerSocket(9999);
			System.out.println("estoy a la escucha");
			while(true) {
				Socket misocket=servidor.accept();
				DataInputStream flujo_entrada= new DataInputStream(misocket.getInputStream());
				String mensaje_texto=flujo_entrada.readUTF();
				System.out.println(mensaje_texto);
				misocket.close();				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
