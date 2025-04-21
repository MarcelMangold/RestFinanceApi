package com.mysticalducks.rest.finance.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


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
	@JoinColumn(name = "party_id", referencedColumnName="id")
	@Schema(description = "The party of the category")
	private Party party;
	
	
	@ManyToOne
	@JoinColumn(name = "icon_id", referencedColumnName="id")
	@Schema(description  = "The icon of the category")
	private Icon icon;
	
	public Category() {}
	
	public Category(String name, Party party, Icon icon) {
		this.name = name;
		this.party = party;
		this.icon = icon;
	}
	 
	public Party getParty() {
		return party;
	}
	
	public void setParty(Party party) {
		this.party = party;
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
