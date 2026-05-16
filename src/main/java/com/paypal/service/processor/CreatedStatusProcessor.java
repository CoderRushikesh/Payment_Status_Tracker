package com.paypal.service.processor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.paypal.dao.interfaces.TransactionDao;
import com.paypal.dto.TransactionDto;
import com.paypal.entity.TransactionEntity;
import com.paypal.interfaces.TransactionStatusProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreatedStatusProcessor implements TransactionStatusProcessor {

	private final ModelMapper modelMapper;
    private final TransactionDao transactionDao;
	
	public TransactionDto processStatus( TransactionDto txnDto) {
		// TODO Auto-generated method stub
		
		log.info("Processing Created status for transaction: {}", txnDto);
		
	TransactionEntity txnEntity =	modelMapper.map(txnDto, TransactionEntity.class);
	 log.info("Mapped TransactionEntity: {}", txnEntity);
	
	TransactionEntity responseEntity = transactionDao.createTransaction(txnEntity);
	 log.info("Transaction created in DB: {}", responseEntity);	
	return  txnDto;
	}

	

	

}
