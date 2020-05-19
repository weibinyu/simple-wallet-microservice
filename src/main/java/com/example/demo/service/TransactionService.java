package com.example.demo.service;

import com.example.demo.domain.TransactionEntity;

import java.util.List;

public interface TransactionService {

    public List<TransactionEntity> getTransactionHistory(String username) throws Exception;

    public TransactionEntity withdraw(TransactionEntity transactionEntity) throws Exception;

    public TransactionEntity credit(TransactionEntity transactionEntity) throws Exception;

    public int checkBalance(String userID) throws Exception;

    public void deleteTransaction(String username);
}
