package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    private Map<String,Object> params = new HashMap<>();

    @GetMapping("/balance/{user_id}")
    public Map<String, Object> GetBalance(@PathVariable("user_id") String userId) {
        params.clear();
        params.put("id",userId);
        return params;
    }

    @GetMapping("/transaction_history/{user_id}")
    public Map<String, Object> GetTranscationHistory(@PathVariable("user_id") String userId) {
        params.clear();
        params.put("id",userId);
        return params;
    }

    @PutMapping("/credit/{user_id}/{amount}")
    public Map<String, Object> credit(@PathVariable("user_id") String userId, int amount) {
        params.clear();
        params.put("id",userId);
        params.put("amount",amount);
        return params;
    }

    @PutMapping("/withdrawal/{user_id}/{amount}")
    public Map<String, Object> withdrawal(@PathVariable("user_id") String userId, int amount) {
        params.clear();
        params.put("id",userId);
        params.put("amount",amount);
        return params;
    }
}
