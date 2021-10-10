package com.mysticalducks.rest.finance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class CommonController {
	
	public ResponseEntity<String> iconNotFound(int iconId) {
		return new ResponseEntity<String>("Icon " + iconId + " not found",HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<String> userNotFound(int userId) {
		 return new ResponseEntity<String>("User " + userId + " not found",HttpStatus.NOT_FOUND);
	 }
	 

}
