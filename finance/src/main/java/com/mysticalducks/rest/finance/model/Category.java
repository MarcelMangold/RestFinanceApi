package com.mysticalducks.rest.finance.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;


@Entity
@Table(name = "category")
@Schema(description = "Details about the category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description  = "The unique id of the category")
	private int id;
	
	@Schema(description  = "The name fo the category")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName="id")
	@Schema(description = "The user of the category")
	private User user;
	
	
	@ManyToOne
	@JoinColumn(name = "icon_id", referencedColumnName="id")
	@Schema(description  = "The icon of the category")
	private Icon icon;
	
	public Category() {}
	
	public Category(String name, User user, Icon icon) {
		this.name = name;
		this.user = user;
		this.icon = icon;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setID(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	
	public Icon getIcon() {
		return icon;
	}
	

}
