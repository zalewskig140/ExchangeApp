package pl.zalewskigrzegorz.exchange;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.awt.*;

@Log
@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    private ExchangeRatesService exchangeRatesService;


    @Override
    public void run(String... args) throws Exception {
        log.info("Executing startup actions...");
        ExchangeRatesService.getRates();

    }

}
