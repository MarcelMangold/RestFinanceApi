package com.mysticalducks.rest.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.exception.DataNotFoundException;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.repository.IconRepository;

@Service
public class IconService implements IIconService {

	@Autowired
	private IconRepository iconRepository;

	public List<Icon> findAll() {
		return (List<Icon>) iconRepository.findAll();
	}

	public Icon findById(int id) {
		return iconRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
	}

	public Icon save(String name) {
		return iconRepository.save(new Icon(name));
	}

	public void delete(Icon icon) {
		iconRepository.delete(icon);
	}

	public void deleteById(int id) {
		iconRepository.deleteById(id);
	}

}
