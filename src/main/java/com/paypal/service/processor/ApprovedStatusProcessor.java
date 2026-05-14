package com.paypal.service.processor;

import org.springframework.stereotype.Service;

import com.paypal.dto.TransactionDto;
import com.paypal.interfaces.TransactionStatusProcessor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApprovedStatusProcessor implements TransactionStatusProcessor {

	@Override
	public TransactionDto processStatus(TransactionDto txnDto) {
		// TODO Auto-generated method stub
		log.info("Processing Approved status for transaction: {}", txnDto);
		return txnDto;
	}

}
