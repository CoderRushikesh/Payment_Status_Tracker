package com.paypal.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.paypal.dao.interfaces.TransactionDao;
import com.paypal.entity.TransactionEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class TransactionDaoImpl implements TransactionDao {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	@Override
    public TransactionEntity createTransaction(TransactionEntity transaction) {
		String sql = "INSERT INTO `Transactions` (" +
		        "userId, paymentMethodId, providerId, paymentTypeId, txnStatusId, " +
		        "amount, currency, merchantTransactionReference, txnReference, providerReference, " +
		        "errorCode, errorMessage, retryCount" +
		        ") VALUES (" +
		        ":userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId, " +
		        ":amount, :currency, :merchantTransactionReference, :txnReference, :providerReference, " +
		        ":errorCode, :errorMessage, :retryCount" +
		        ")";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(transaction);

        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});

        transaction.setId(keyHolder.getKey().intValue());
        return transaction;
	}
	@Override
	public TransactionEntity getTransactionById(String txnReferenc) {
		
		String sql = " SELECT * FROM `Transactions` WHERE txnReference = :txnReference lIMIT 1";
		
		 Map<String, Object> params = new HashMap<>();
		 
		 params.put("txnReference",txnReferenc);
		
		 
     
    	TransactionEntity txnEntity =jdbcTemplate.queryForObject(
    		  sql,
    		  params,
    		  new BeanPropertyRowMapper<>(TransactionEntity.class)
    			 
    			 );
     
   
    	return txnEntity;
		
	}

	
}
