package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.repository.IconRepository;

@Service
public class IconService implements IIconService {

	@Autowired
	private IconRepository iconRepository;

	public List<Icon> findAllIcon() {
		return (List<Icon>) iconRepository.findAll();
	}

	public Optional<Icon> findIcon(int id) {
		return iconRepository.findById(id);
	}

	public Icon save(String name) {
		return iconRepository.save(new Icon(name));
	}

	public void delete(int id) {
		if(iconRepository.existsById(id)) {
			iconRepository.deleteById(id);
		}
	}

}
