package com.paypal.interfaces;

import com.paypal.pojo.CreatePaymentRequest;
import com.paypal.pojo.InitiatePaymentRequest;
import com.paypal.pojo.PaymentResponse;

public interface PaymentService {

	public String createPayment(CreatePaymentRequest request);
	
	public PaymentResponse initiatePayment(String tnxReference , InitiatePaymentRequest initiatePaymentRequest);
	
	public String capturePayment(String tnxReference);
	
	
}
