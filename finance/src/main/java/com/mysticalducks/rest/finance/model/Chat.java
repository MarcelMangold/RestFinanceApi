package com.mysticalducks.rest.finance.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
