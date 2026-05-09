package com.paypal.service;

import com.paypal.pojo.CreatePaymentRequest;
import com.paypal.pojo.InitiatePaymentRequest;

public interface PaymentService {

	public String createPayment(CreatePaymentRequest request);
	
	public String initiatePayment(String tnxReference , InitiatePaymentRequest initiatePaymentRequest);
	
	public String capturePayment(String tnxReference);
	
	
}
