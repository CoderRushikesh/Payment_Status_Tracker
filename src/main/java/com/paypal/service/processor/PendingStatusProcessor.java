package com.paypal.service.processor;

import org.springframework.stereotype.Service;

import com.paypal.interfaces.TransactionStatusProcessor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PendingStatusProcessor implements TransactionStatusProcessor {

	@Override
	public String processStatus(String statusId) {
		// TODO Auto-generated method stub
		log.info("Processing Pending status for transaction: {}", statusId);
		return "Pending";
	}

}
