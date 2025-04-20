package com.mysticalducks.rest.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.exception.FinanceInformationNotFoundException;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.repository.FinanceInformationRepository;

@Service
public class FinanceInformationService implements IFinanceInformation {

	@Autowired
	private FinanceInformationRepository financeRepository;

	public Iterable<FinanceInformation> findAll() {
		return financeRepository.findAll();
	}

	public FinanceInformation findById(int id) {
		return financeRepository.findById(id).orElseThrow(() -> new FinanceInformationNotFoundException(id));
	}

	public FinanceInformation save(Double budget) {
		return financeRepository.save(new FinanceInformation(budget));
	}

	public FinanceInformation save(FinanceInformation information) {
		return financeRepository.save(information);
	}

	public void deleteById(int id) {
		financeRepository.deleteById(id);
	}

	public void delete(FinanceInformation information) {
		financeRepository.delete(information);
	}
}
