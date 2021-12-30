package com.mysticalducks.rest.finance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chat")
public class Chat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	public Chat() {}
	
	public Chat(int id) {
		this.id = id;
	}

	@Column(nullable = false)
	public int getId() {
		return id;
	}


}
