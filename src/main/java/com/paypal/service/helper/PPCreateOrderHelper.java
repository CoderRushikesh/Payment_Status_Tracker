package com.paypal.service.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.paypal.http.HttpRequest;
import com.paypal.paypalprovider.PPCreateOrderReq;
import com.paypal.pojo.InitiatePaymentRequest;
import com.paypal.util.JsonUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PPCreateOrderHelper {

	private final   JsonUtil jsonUtil;
	
	public  HttpRequest prepareHttpRequest(String tnxReference, InitiatePaymentRequest initiatePaymentRequest) {
		
	
		HttpHeaders headers = new HttpHeaders();
		// headers.setBearerAuth(accessToken); there is no requirement within the same system 
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		PPCreateOrderReq ppCreateOrderRequest = new PPCreateOrderReq();
		ppCreateOrderRequest.setAmount(1.5);
		ppCreateOrderRequest.setCurrencyCode("USD");
		ppCreateOrderRequest.setReturnUrl(initiatePaymentRequest.getSuccessUrl());
		ppCreateOrderRequest.setCancelUrl(initiatePaymentRequest.getCancelUrl());

		
		 String requestAsJson = jsonUtil.toJson(ppCreateOrderRequest);
		
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setHttpMethod(HttpMethod.POST);
		httpRequest.setUrl("http://localhost:8083/Payments");
		
		httpRequest.setHttpHeaders(headers);
		httpRequest.setBody(requestAsJson);
		
		
		
		
		return httpRequest;
		
		
	}

	
	
	
	
}
