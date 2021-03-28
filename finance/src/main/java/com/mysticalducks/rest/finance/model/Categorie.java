package com.mysticalducks.rest.finance.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "categorie")
@ApiModel(description = "Details about the categorie")
public class Categorie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The unique id of the categorie")
	private int id;
	
	@ApiModelProperty(notes = "The name fo the categorie")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName="id")
	@ApiModelProperty(notes = "The user of the categorie")
	private User user;
	
	
	@ManyToOne
	@JoinColumn(name = "iconid", referencedColumnName="id")
	@ApiModelProperty(notes = "The icon of the categorie")
	private Icon icon;
	
	public Categorie() {}
	
	public Categorie(Integer id, String name, User user, Icon icon) {
		
		this.id = id;
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
