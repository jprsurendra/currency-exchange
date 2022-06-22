package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.demo.model.ForexRate;



@Repository
public interface ForexRateRepo extends JpaRepository<ForexRate, Long> {
    List<ForexRate> findByToCurrency_CurrencyCode(String countryCode);
    List<ForexRate> findByIsActiveAndToCurrency_CurrencyCode(Boolean isActive, String countryCode);
}
