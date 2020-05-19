package com.example.demo.service.impl;

import com.example.demo.domain.TransactionEntity;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private UserMapper userMapper;

    //Simply use query to get all transaction record of certain user
    @Override
    public List <TransactionEntity> getTransactionHistory(String username) throws Exception {
        checkUserExist(username);
        return transactionMapper.getAllTransactionByUser(username);
    }

    //Simply use query to get balance of certain user
    @Override
    public int checkBalance(String username) throws Exception {
        checkUserExist(username);
        return userMapper.getBalance(username);
    }

    //withdraw method with @Transactional to ensure the atomicity
    @Override
    @Transactional()
    public TransactionEntity withdraw(TransactionEntity transactionEntity) throws Exception {
        //A transactionEntity with only username, transactionID and amount is sent to method
        //username and transactionID is used to ensure user exist in database and transaction with transactionID does not
        checkUserExist(transactionEntity.getUsername());
        checkTransactionExist(transactionEntity.getTransactionID());
        //set action and create time is set and the transaction record is insert into database
        transactionEntity.setAction("withdrawal");
        transactionEntity.setCreateTime(new Date());
        transactionMapper.insert(transactionEntity);
        //check if user have enough money/balance and preform withdrawal, throw exception when user don't have enough
        if(checkBalance(transactionEntity.getUsername()) >= transactionEntity.getAmount()){
            userMapper.withdrawBalance(transactionEntity.getUsername(),transactionEntity.getAmount());
        }else {
            throw new RuntimeException("user does not have enough balance for withdraw");
        }
        //return entity for test purpose
        return transactionEntity;
    }

    @Override
    @Transactional()
    public TransactionEntity credit(TransactionEntity transactionEntity) throws Exception {
        //ensure user exist in database and transaction with transactionID does not
        checkUserExist(transactionEntity.getUsername());
        checkTransactionExist(transactionEntity.getTransactionID());
        //set action and create time is set and the transaction record is insert into database
        transactionEntity.setAction("credit");
        transactionEntity.setCreateTime(new Date());
        transactionMapper.insert(transactionEntity);
        //increase user balance
        userMapper.creditBalance(transactionEntity.getUsername(),transactionEntity.getAmount());

        return transactionEntity;
    }

    //used in every method to check if exist user with provided username,throw exception if not
    private void checkUserExist(String username) {
        if(!userMapper.checkUserExists(username)){
            throw new RuntimeException("No such user");
        }
    }

    //check if transaction with same transaction ID already exist,throw exception if not
    private void checkTransactionExist(String transactionID) {
        if(transactionMapper.checkTransactionExists(transactionID)){
            throw new RuntimeException("Transaction ID already exist");
        }
    }

    //only used for test purpose
    @Override
    public void deleteTransaction(String username) {
        transactionMapper.delete(username);
    }
}
