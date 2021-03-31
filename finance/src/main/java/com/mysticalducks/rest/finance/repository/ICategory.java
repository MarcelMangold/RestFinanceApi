package com.mysticalducks.rest.finance.repository;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ICategory {

	String getId();
	String getName();
	
	@JsonProperty("icon")
	@Value("#{target.icon.name}")     
	String getIcon();

}
