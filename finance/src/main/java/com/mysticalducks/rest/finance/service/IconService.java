package com.mysticalducks.rest.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.repository.IconRepository;

@Service
public class IconService implements IIconService{
	
	@Autowired
	private IconRepository iconRepository;

	public List<Icon> findAllIcon() {
		return (List<Icon>) iconRepository.findAll();
	}

}
