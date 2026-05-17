package com.paypal.constant;



import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

		
	GENERIC_ERROR("20000" , "Something went Wrong  please try again later  "),
	
	
	 INVALID_REQUEST("20001" , "Invalid request payload"),
	
	
	 PAYPAL_SERVICE_UNAVAILABLE("40001" , "PayPal service is currently unavailable , please try again later "),
	
	 PAYPAL_API_TIMEOUT("40003" , "PayPal API request timed out, please try again later "),
	
	 PAYPAL_API_INTERNAL_ERROR("50001" , "Internal server error occurred while processing the request "),
	 PAYPAL_ERROR("300007" , "<paypal error>"), PAYPAL_UNKNOWN_ERROR("30002","error"), CURRENCY_CODE_REQUIRED("30008","Currency code is required field and cannot be null / blank"),
   NO_STATUS_PROCESSOR_FOUND("20003" , "No processor found  "),
   PAYPAL_PROVIDER_UKNOWN_ERROR("20004 " , "Uknown error occurred in paypal provider ") ;
	
	private final String errorCode;
	private final String errorMessage;
	
	ErrorCodeEnum(String errorCode , String errorMessage){
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
}
