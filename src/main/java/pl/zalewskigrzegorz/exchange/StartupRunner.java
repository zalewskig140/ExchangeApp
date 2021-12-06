package pl.zalewskigrzegorz.exchange;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

@Log
@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    RatesRepository ratesRepository;
    @Autowired
    ExchangeRatesService exchangeRatesService;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ExchangeWorkService exchangeWorkService;


    @Override
    public void run(String... args) throws Exception {
        ratesRepository.deleteAll();
        log.info("Executing startup actions...");
        for (int i = 0; i < exchangeRatesService.getRates().size(); i++) {
            ratesRepository.save(exchangeRatesService.getRates().get(i));
            exchangeWorkService.deleteOldTransaction();
        }
        log.info("Application is ready to use");


    }
}

