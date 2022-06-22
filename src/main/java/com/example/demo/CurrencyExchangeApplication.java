package com.example.demo;

import com.example.demo.config.ConfigProperties;
import com.example.demo.model.Currency;
import com.example.demo.model.ForexRate;
import com.example.demo.service.CurrencyExchangeService;
import com.example.demo.vo.ForexRateWrapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@SpringBootApplication
public class CurrencyExchangeApplication implements CommandLineRunner {

	private RestTemplate restTemplate;
	private ModelMapper modelMapper;

	@Bean
	public ModelMapper modelMapper() {
		modelMapper = new ModelMapper();
		return modelMapper;
	}

	@Bean
	public RestTemplate restTemplate() {
		restTemplate = new RestTemplate();
		return restTemplate;
	}

	@Autowired
	private CurrencyExchangeService currencyExchangeService;

	@Autowired
	private ConfigProperties configProp;

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Inside run() method of CurrencyExchangeApplication");

		List<Currency> lstCurrencies = currencyExchangeService.getCurrencyList();

		List<ForexRate> allData = fetchForexRate(lstCurrencies);

		currencyExchangeService.refreshForexRateInDB(allData);

	}
	private List<ForexRate> fetchForexRate(List<Currency> lstCurrencies){
		String forexrateApiURL =  configProp.getConfigValue("forex-rate-api.url");
		Map<String, Currency> currencyCodeMap = new HashMap<>();

		ForexRateWrapper wrapper = restTemplate.getForObject(forexrateApiURL, ForexRateWrapper.class);

		for (Currency currency : lstCurrencies ) {
			currencyCodeMap.put(currency.getCurrencyCode().toUpperCase(), currency);
		}

		List<ForexRate>  allData = wrapper.toWrap(currencyCodeMap);
		return allData;
	}
}
