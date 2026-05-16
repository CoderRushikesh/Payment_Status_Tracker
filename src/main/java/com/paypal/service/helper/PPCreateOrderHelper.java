package com.paypal.service.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.paypal.dto.TransactionDto;
import com.paypal.http.HttpRequest;
import com.paypal.paypalprovider.PPCreateOrderReq;
import com.paypal.pojo.InitiatePaymentRequest;
import com.paypal.util.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PPCreateOrderHelper {

	private final   JsonUtil jsonUtil;
	
	public  HttpRequest prepareHttpRequest(String tnxReference, InitiatePaymentRequest initiatePaymentRequest, TransactionDto txnDto) {
		
	log.info("Preparing HTTP request for PayPal order creation with transaction reference: {} || initiatePaymentRequest {} || txnDto {}", tnxReference, initiatePaymentRequest, txnDto);
		
		HttpHeaders headers = new HttpHeaders();
		// headers.setBearerAuth(accessToken); there is no requirement within the same system 
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		PPCreateOrderReq ppCreateOrderRequest = new PPCreateOrderReq();
		ppCreateOrderRequest.setAmount(txnDto.getAmount().doubleValue());
		ppCreateOrderRequest.setCurrencyCode(txnDto.getCurrency());
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
