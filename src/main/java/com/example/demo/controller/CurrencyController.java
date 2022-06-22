package com.example.demo.controller;

import com.example.demo.service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    private CurrencyExchangeService currencyExchangeService;

//    @PostMapping
//    public ResponseEntity<CurrencyExchangeRate> addCurrencyRate(@RequestBody CurrencyExchangeRate rate){
//        return ResponseEntity.ok(currencyExchangeService.addCurrencyRate(rate));
//    }
    @GetMapping("/{from}/{to}/{value}")
    public ResponseEntity getCurrencyRate(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal value){
       try{
           return ResponseEntity.ok(currencyExchangeService.currencyConversion(from, to, value));
       }catch(Exception e){
          return  ResponseEntity.badRequest().body(e.getMessage());
       }
    }

}