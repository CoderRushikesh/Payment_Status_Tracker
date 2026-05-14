package com.paypal.service.processor;

import org.springframework.stereotype.Service;

import com.paypal.interfaces.TransactionStatusProcessor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreatedStatusProcessor implements TransactionStatusProcessor {

	public String processStatus(String statusId) {
		// TODO Auto-generated method stub
		
		
		return  statusId;
	}

	

	

}
