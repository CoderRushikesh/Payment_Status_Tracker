package com.paypal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.pojo.CreatePaymentRequest;
import com.paypal.pojo.InitiatePaymentRequest;
import com.paypal.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/payments")
@RequiredArgsConstructor
public class myController {
	
private final PaymentService paymentService;
	
  @PostMapping
  public String createPayment( @RequestBody CreatePaymentRequest createPaymentRequest) {
	log.info("Creating payment with amount: {} and currency: {}");
 String response = paymentService.createPayment(createPaymentRequest);
	return "Payment created successfully" + response;
  }
  
   // JSON request body for initiating payment
//  {
//	  "userId" : 101,
//	  "paymentMethodId" : 2,
//	  "providerId" : 5,
//	  "paymentTypeId" : 1, 
//	   "amount" : 500.75,
//	   "currency " : "INR",
//	   "merchantTransactionREfeerence": "TXN-987654321"
//
//	 }
  @PostMapping("/{tnxReference}/initiate")
  public String initiatePayment(@PathVariable String tnxReference , @RequestBody InitiatePaymentRequest initiatePaymentRequest) {
	  log.info("Initiating payment with transaction reference: {}", "tnxReference");
	
	String response =  paymentService.initiatePayment(tnxReference , initiatePaymentRequest);
	  log.info("Payment initiated successfully with transaction reference: {}", "tnxReference");
	  return "Payment initiated successfully" + tnxReference + response;
	  
  }
  
  
  @PostMapping("/{tnxReference}/capture")
  public String capturePayment(@PathVariable String tnxReference) {
	  log.info("Capturing payment with transaction reference: {}", "tnxReference");
	
	  String response = paymentService.capturePayment(tnxReference);
	  
	  return "Payment captured successfully" + tnxReference + response;
	
}
  
  
}
