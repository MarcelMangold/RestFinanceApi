package com.mysticalducks.rest.finance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;


@Entity
@Table(name = "user_management")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private int id;
	
	private String name;
	
	private String password;
	
	private int language;
	
	public User() {}
	
	public User(int id, String name, String password, int language) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.language = language;
	}
	
	public User(String name, String password, int language) {
		this.name = name;
		this.password = password;
		this.language = language;
	}
	
	
	@Column(nullable = false)
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}
	


}
