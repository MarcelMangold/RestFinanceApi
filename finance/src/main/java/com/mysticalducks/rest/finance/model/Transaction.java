package com.mysticalducks.rest.finance.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private double amount;
	
	private String note;
	
	private Double latitude;
	
	private Double longitude;
	
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName="id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "party_id", referencedColumnName="id")
	private Party party;
	
	
	@CreationTimestamp
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	public Transaction() {}
	
	public Transaction(String name, double amount, String note, Category category, Party party) {
		this.name = name;
		this.amount = amount;
		this.note = note;
		this.category = category;
		this.party = party;
	}
	
	public Transaction(String name, double amount, String note, Double latitude, Double longitude, Category category, Party party) {
		this.name = name;
		this.amount = amount;
		this.note = note;
		this.latitude = latitude;
		this.longitude = longitude;
		this.category = category;
		this.party = party;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Party getParty() {
		return party;
	}
	
	public void setParty(Party party) {
		this.party = party;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	

}
