package com.example.demo.vo;

import com.example.demo.model.Currency;
import com.example.demo.model.ForexRate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ForexRateWrapper {
    private Boolean success; // true
    private Long timestamp; // 1655554755
    private String base;    // "USD"
    private String date;    // "2022-06-18",
    private HashMap<String, String> rates; // { "AED": 3.673104, "AFN": 89.437089,.... }


    public List<ForexRate> toWrap(Map<String, Currency> currencyCodeMap){
        List<ForexRate> lst = new ArrayList<>();
        try {
            Currency baseCurrency = currencyCodeMap.get(this.base.toUpperCase());
            Date created_on = new Date();

            for (Map.Entry<String,String> entry : this.rates.entrySet()) {
                try {
                    ForexRate entity = new ForexRate();
                    entity.setBaseCurrency(baseCurrency);
                    entity.setToCurrency(currencyCodeMap.get(entry.getKey().toUpperCase()));
                    entity.setConversionRate(new BigDecimal(entry.getValue())); // 3.673104
                    entity.setIsActive(true);
                    entity.setUpdatedOn(created_on);
                    lst.add(entity);
                }catch (Exception e){
                    log.info("Inside toWrap() method of ForexRateWrapper. Error: "+ e.getMessage());
                }
            }
        }catch (Exception e2){
            log.info("Inside toWrap() method of ForexRateWrapper. Error: "+ e2.getMessage());
        }
        return lst;
    }

}
