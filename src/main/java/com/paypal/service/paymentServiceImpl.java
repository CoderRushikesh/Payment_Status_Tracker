package com.paypal.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.paypal.http.HttpRequest;
import com.paypal.http.HttpServiceEngine;
import com.paypal.interfaces.PaymentService;
import com.paypal.interfaces.TransactionStatusProcessor;
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
	
	public String createPayment(@RequestBody CreatePaymentRequest createPaymentRequest) {
		// TODO Auto-generated method stub
		log.info("Creating payment with amount: "
				+ " {} and currency: {} ");
		
		 String response = paymentStatusService.processPayment(1);
		
		log.info("Transaction status processed with response: {}", response);
		return "Payment created successfully" + createPaymentRequest + "\n" + response;
	}

	@Override
	public String initiatePayment(String tnxReference , InitiatePaymentRequest initiatePaymentRequest) {
		// TODO Auto-generated method stub
		log.info("Initiating payment with "
				+ "transaction reference: {}", 
				"tnxReference");
		
		 // make api call to paypal-provider to initiate payment 
		  
		  /*
		   *  1 Prepare HttpRequest DONE
		   *  2 Pass to HttpServiceEngine
		   *  3 Process the response 
		   * 
		   */
		HttpRequest  httpReq =	 ppCreateOrderHelper.prepareHttpRequest(tnxReference, initiatePaymentRequest);	
	 log.info("Prepared HTTP request for initiating payment: {}", httpReq);	
		
//	ResponseEntity<String> httpResponse = httpServiceEngine.makeHttpCall(httpReq);
//	 log.info("Received HTTP response for initiating payment: {}", httpResponse);
	 
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
