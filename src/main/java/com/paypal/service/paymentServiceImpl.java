package com.paypal.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.paypal.http.HttpRequest;
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
	
	public String createPayment(@RequestBody CreatePaymentRequest createPaymentRequest) {
		// TODO Auto-generated method stub
		log.info("Creating payment with amount: "
				+ " {} and currency: {} ");
		
		String response = "Payment created with amount: ";
		return "Payment created successfully" + createPaymentRequest;
	}

	@Override
	public String initiatePayment(String tnxReference , InitiatePaymentRequest initiatePaymentRequest) {
		// TODO Auto-generated method stub
		log.info("Initiating payment with "
				+ "transaction reference: {}", 
				"tnxReference");
		
		 // make api call to paypal-provider to initiate payment 
		  
		  /*
		   *  1 Prepare HttpRequest 
		   *  2 Pass to HttpServiceEngine
		   *  3 Process the response 
		   * 
		   */
		HttpRequest  httpReq =	 ppCreateOrderHelper.prepareHttpRequest(tnxReference, initiatePaymentRequest);	
	 log.info("Prepared HTTP request for initiating payment: {}", httpReq);	
		
		return tnxReference;
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
