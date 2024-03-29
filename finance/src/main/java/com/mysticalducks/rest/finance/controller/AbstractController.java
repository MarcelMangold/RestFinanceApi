package com.mysticalducks.rest.finance.controller;

import com.mysticalducks.rest.finance.exception.EmptyBodyException;

public abstract class AbstractController {
	
	
	protected void checkIfParameterIsEmpty(String param) {
		if(param == null || param.isEmpty() || param.isBlank()) {
			throw new EmptyBodyException();
		}
	}

}
