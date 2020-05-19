package com.example.demo.mapper;

import com.example.demo.domain.TransactionEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TransactionMapper {

    //Insert new transaction data into database
    @Insert("INSERT INTO transaction(username,action,create_time,transaction_id,amount) " +
            "VALUES( #{username},#{action},#{createTime},#{transactionID},#{amount})")
    void insert(TransactionEntity transactionEntity);

    //Map value from database to TransactionEntity
    @Select("SELECT * FROM transaction WHERE username=#{username}")
    @Results({
           @Result(column = "transaction_id",property = "transactionID"),
           @Result(column = "username",property = "username"),
           @Result(column = "amount",property = "amount"),
           @Result(column = "create_time",property = "createTime"),
           @Result(column = "action",property = "action")
    })
    List<TransactionEntity> getAllTransactionByUser(String username);

    //check if transaction with provided transactionID already exist in database
    @Select("SELECT EXISTS(SELECT 1 FROM transaction WHERE transaction_id=#{transactionID})")
    Boolean checkTransactionExists(String transactionID);

    //Delete test data
    @Delete("DELETE FROM transaction WHERE username=#{username}")
    void delete(String username);
}
