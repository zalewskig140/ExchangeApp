package pl.zalewskigrzegorz.exchange;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.zalewskigrzegorz.exchange.Dto.RateInfo;
import pl.zalewskigrzegorz.exchange.Repository.RatesRepository;
import pl.zalewskigrzegorz.exchange.Repository.TransactionRepository;
import pl.zalewskigrzegorz.exchange.Service.ExchangeRatesService;
import pl.zalewskigrzegorz.exchange.Service.ExchangeWorkService;

import java.util.List;

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
        exchangeWorkService.deleteOldTransaction();
        log.info("Executing startup actions...");
        List<RateInfo> rates = exchangeRatesService.getRates();
        for (int i = 0; i < rates.size(); i++) {
            ratesRepository.save(rates.get(i));

        }

        log.info("Application is ready to use");


    }
}

