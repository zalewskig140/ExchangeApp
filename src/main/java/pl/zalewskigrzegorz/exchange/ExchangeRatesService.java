package pl.zalewskigrzegorz.exchange;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log
public class ExchangeRatesService {
    ArrayList<TableInfo> tableInfos = new ArrayList<>();

    public static void getRates() {
        RestTemplate restTemplate = new RestTemplate();
        TableInfo[] tabA =  restTemplate.getForEntity("http://api.nbp.pl/api/exchangerates/tables/a/?format=json", TableInfo[].class).getBody();
        TableInfo[] tabB =  restTemplate.getForEntity("http://api.nbp.pl/api/exchangerates/tables/b/?format=json", TableInfo[].class).getBody();

        TableInfo tableInfoA = tabA[0];
        TableInfo tableInfoB = tabB[0];
        List rates = tableInfoA.getRates();
        System.out.println(rates.size());
        System.out.println(tableInfoB.getRates().size());
        for (int i = 0; i < tableInfoB.getRates().size(); i++) {
            rates.add(tableInfoB.getRates().get(i));
        }
        System.out.println(rates);
        System.out.println(rates.size());

    }

}