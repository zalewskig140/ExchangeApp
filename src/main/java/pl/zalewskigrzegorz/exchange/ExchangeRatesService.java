package pl.zalewskigrzegorz.exchange;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log
public class ExchangeRatesService {


    public List<RateInfo> getRates() {
        RestTemplate restTemplate = new RestTemplate();
        TableInfo[] tabA = restTemplate.getForEntity("http://api.nbp.pl/api/exchangerates/tables/a/?format=json", TableInfo[].class).getBody();
        TableInfo[] tabB = restTemplate.getForEntity("http://api.nbp.pl/api/exchangerates/tables/b/?format=json", TableInfo[].class).getBody();

        TableInfo tableInfoA = tabA[0];
        TableInfo tableInfoB = tabB[0];
        List<RateInfo> rates = tableInfoA.getRates();
        for (int i = 0; i < tableInfoB.getRates().size(); i++) {
            rates.add(tableInfoB.getRates().get(i));
        }
        RateInfo pln = new RateInfo("złoty polski","PLN", 1.00f);
        rates.add(pln);
        List<RateInfo> sortedRates = rates.stream()
                .sorted(Comparator.comparing(RateInfo::getCurrency))
                .collect(Collectors.toList());

        return sortedRates;
    }

}