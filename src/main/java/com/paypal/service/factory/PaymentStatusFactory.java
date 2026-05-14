package com.paypal.service.factory;


import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.paypal.interfaces.TransactionStatusProcessor;
import com.paypal.service.processor.CreatedStatusProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentStatusFactory {

	private final ApplicationContext applicationContext;
	private final CreatedStatusProcessor createdStatusProcessor;
	
	
	public TransactionStatusProcessor getStatusProcessor(int statusId) {

	
		log.info("Getting processor for payment status ID: {}", statusId);
		
		switch(statusId){
		case 1 : 
			log.info("Returning CreatedStatusProcessor for payment status ID: {}", statusId);
			return  applicationContext.getBean(CreatedStatusProcessor.class);
		
		case 2 : 
			log.info("Returning InitiatedStatusProcessor for payment status ID: {}", statusId);
			return  applicationContext.getBean("initiatedStatusProcessor", TransactionStatusProcessor.class);
		
		
			default : 
				log.warn("No processor found for payment status ID: {}", statusId);
		
				return null;
		}
		
		
		
	}
	
	
}
