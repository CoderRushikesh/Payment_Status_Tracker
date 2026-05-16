package com.paypal.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.paypal.dao.interfaces.TransactionDao;
import com.paypal.dto.TransactionDto;
import com.paypal.entity.TransactionEntity;
import com.paypal.http.HttpRequest;
import com.paypal.http.HttpServiceEngine;
import com.paypal.interfaces.PaymentService;
import com.paypal.pojo.CreatePaymentRequest;
import com.paypal.pojo.InitiatePaymentRequest;
import com.paypal.service.helper.PPCreateOrderHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class paymentServiceImpl implements PaymentService {

	private final PPCreateOrderHelper ppCreateOrderHelper;
	private final HttpServiceEngine httpServiceEngine;
	private final PaymentStatusService paymentStatusService;
	private final ModelMapper modelMapper;
    private final TransactionDao transactionDao;

	public String createPayment(@RequestBody CreatePaymentRequest createPaymentRequest) {
		// TODO Auto-generated method stub
		log.info("Creating payment with amount: "
				+ " {} and currency: {} ");

		TransactionDto txnDto = modelMapper.map(createPaymentRequest, TransactionDto.class);
//		TransactionDto transactionDto = new TransactionDto();
		log.info("Mapped CreatePaymentRequest to TransactionDto: {}", txnDto);

        int txnStatusId = 1;
        String txnReference = UUID.randomUUID().toString();
        
        txnDto.setTxnStatusId(txnStatusId);
        txnDto.setTxnReference(txnReference);
		
		String response = paymentStatusService.processPayment(txnDto);

		log.info("Transaction status processed with response: {}", response );
		return "Payment created successfully" + createPaymentRequest + "\n" + response + " \n" + txnDto;
	}

	@Override
	public String initiatePayment(String tnxReference , InitiatePaymentRequest initiatePaymentRequest) {
		// TODO Auto-generated method stub
		log.info("Initiating payment with "
				+ "transaction reference: {}", 
				"tnxReference");

		
		TransactionEntity	txnEntity = transactionDao.getTransactionById(tnxReference);
	    log.info("Fetched TransactionEntity from DB: {}", txnEntity);
			
	    TransactionDto txnDto = modelMapper.map(txnEntity, TransactionDto.class);
	    
		// make api call to paypal-provider to initiate payment 

		/*
		 *  1 Prepare HttpRequest DONE
		 *  2 Pass to HttpServiceEngine
		 *  3 Process the response 
		 * 
		 */
	
		HttpRequest  httpReq =	 ppCreateOrderHelper.prepareHttpRequest(tnxReference, initiatePaymentRequest , txnDto);	
		log.info("Prepared HTTP request for initiating payment: {}", httpReq);	

		//	ResponseEntity<String> httpResponse = httpServiceEngine.makeHttpCall(httpReq);
		

		return  "Payment initiated successfully with transaction reference: " + tnxReference + " and response: " ;
	}

	@Override
	public String capturePayment(String tnxReference) {
		// TODO Auto-generated method stub
		log.info("Capturing payment with"
				+ " transaction reference: {}", 
				"tnxReference");
		return tnxReference;
		
		
		
	}




}
