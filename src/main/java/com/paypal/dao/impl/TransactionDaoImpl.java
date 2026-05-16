package com.paypal.dao.impl;

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

}
