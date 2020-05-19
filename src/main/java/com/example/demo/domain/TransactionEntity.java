package com.example.demo.domain;

import java.util.Date;

public class TransactionEntity {
    private String transactionID;
    private String username;
    private String action;
    private int amount;
    private Date createTime;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
