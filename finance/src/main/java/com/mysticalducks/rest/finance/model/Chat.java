package com.mysticalducks.rest.finance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "chat")
public class Chat {
	
	@Id
	@Column(unique = true, nullable = false)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
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
