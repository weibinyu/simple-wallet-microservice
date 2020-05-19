package com.example.demo.controller;

import com.example.demo.domain.TransactionEntity;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Request url has to be in format of /api/actionName?username= to reach controller
@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/balance")
    public int GetBalance(String username) throws Exception {
        return transactionService.checkBalance(username);
    }

    @GetMapping("/transaction_history")
    public List <TransactionEntity> GetTransactionHistory(String username) throws Exception {
        return transactionService.getTransactionHistory(username);
    }

    //maps requestBody which contain amount and transactionID to transactionEntity
    @PutMapping("/credit")
    public TransactionEntity credit(String username,
                                    @RequestBody TransactionEntity transactionEntity) throws Exception {
        //set username from param into transactionEntity
        transactionEntity.setUsername(username);
        return transactionService.credit(transactionEntity);
    }

    //maps requestBody to transactionEntity
    @PutMapping("/withdraw")
    public TransactionEntity withdraw(String username,
                                      @RequestBody TransactionEntity transactionEntity) throws Exception {
        //set username in transactionEntity
        transactionEntity.setUsername(username);
        return transactionService.withdraw(transactionEntity);
    }
}


