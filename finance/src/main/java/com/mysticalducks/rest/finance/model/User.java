package com.mysticalducks.rest.finance.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "user_management")
public class User {
	
	@Id
	@Column(unique = true, nullable = false)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Column(name = "telegram_user_id")
	private int telegramUserId;
	
	private String password;
	
	private String email;
	
	private int language;
	
	public User() {}
	
	public User(int id, int telegram_user_id, String name, String email, String password, int language) {
		this.id = id;
		this.telegramUserId = telegram_user_id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.language = language;
	}
	
	public User(int id, String name, String email, String password, int language) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.language = language;
	}
	
	public User(String name, String email, String password, int language) {
		this.name = name;
		this.email = email;
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
	
	public int getTelegramUserId() {
		return telegramUserId;
	}

	public void setTelegramUserId(int telegramUserId) {
		this.telegramUserId = telegramUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}
	


}
