package com.example.demo.controller;


import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testController {
    @Autowired
    private TransactionService transactionService;

    //should not exist but put here for test purpose
    @DeleteMapping("/delete_transaction")
    public void deleteTransaction(String username) throws Exception {

        transactionService.deleteTransaction(username);
    }
}
