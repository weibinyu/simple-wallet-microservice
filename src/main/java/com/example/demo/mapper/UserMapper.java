package com.example.demo.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {
    //use query to check if any instance with username exist, and return boolean
    @Select("SELECT EXISTS(SELECT 1 FROM user WHERE username=#{username})")
    boolean checkUserExists(String username);
    //get balance value of user with the username
    @Select("SELECT balance FROM user WHERE username=#{username}")
    int getBalance(String username);
    //increase users balance by given amount
    @Update("update user set balance=balance+#{amount} where username=#{username}")
    void creditBalance(String username, int amount);
    //decrease users balance by given amount if balance wouldn't become negative
    @Update("update user set balance=balance-#{amount} where username=#{username} and balance-#{amount} >= 0")
    void withdrawBalance(String username,int amount);
}
