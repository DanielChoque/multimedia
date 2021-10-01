package com.sap.multimedia.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dato")
public class Dato {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String url;
	private String config;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	@Override
	public String toString() {
		return "Dato [id=" + id + ", url=" + url + ", config=" + config + "]";
	}
	
	
	
}
