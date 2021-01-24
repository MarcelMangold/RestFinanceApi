package com.mysticalducks.rest.finance.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private int amount;
	
	@Column(name="ispositive")
	private boolean isPositive;
	
	private String notice;
	
	@ManyToOne
	@JoinColumn(name = "categorieid", referencedColumnName="id")
	private Categorie categorie;
	
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName="id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "chatid", referencedColumnName="id")
	private Chat chat;
	
	private Timestamp timestamp;
	
	public Transaction() {}
	
	public Transaction(String name, int amount, boolean isPositive, String notice, Categorie categorie, User user, Chat chat) {
		this.name = name;
		this.amount = amount;
		this.isPositive = isPositive;
		this.notice = notice;
		this.categorie = categorie;
		this.user = user;
		this.chat = chat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isPositive() {
		return isPositive;
	}

	public void setPositive(boolean isPositive) {
		this.isPositive = isPositive;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}
	

}
