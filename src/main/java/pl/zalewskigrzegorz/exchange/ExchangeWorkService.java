package pl.zalewskigrzegorz.exchange;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
@Getter
@Service
@Log
public class ExchangeWorkService {
    private ExchangeOptions exchangeOptions;
    private float mainRate;
    private Map<String, Float> otherRates;
    private String myCurrencyName;
    private String targetCurrencyName;
    private float amount;


    @Autowired
    private ExchangeRatesService exchangeRatesService;

    public void convert(ExchangeOptions exchangeOptions){
        this.exchangeOptions = exchangeOptions;
this.mainRate = convertMainCurrency(exchangeOptions);
this.otherRates = convertOtherCurencies(exchangeOptions);
this.myCurrencyName = findMyCurrencyName(exchangeOptions);
this.targetCurrencyName = findTargetCurrencyName(exchangeOptions);
this.amount = calculateTheAmountOfMoney(exchangeOptions);
    }

    public float convertMainCurrency(ExchangeOptions exchangeOptions) {
        float myCurrency = exchangeRatesService.getRates().stream()
                .filter(currency -> currency.getCode().equals(exchangeOptions.getMyCurrency()))
                .findFirst().get().getMid();

        float targetCurrency = exchangeRatesService.getRates().stream()
                .filter(currency -> currency.getCode().equals(exchangeOptions.getTargetCurrency()))
                .findFirst().get().getMid();
        float mainRate = myCurrency / targetCurrency;

        log.info(String.valueOf(myCurrency));
        log.info(String.valueOf(targetCurrency));
        log.info(String.valueOf(mainRate));
        return mainRate;
    }

    public Map<String, Float> convertOtherCurencies(ExchangeOptions exchangeOptions){
        Map<String, Float> rates = new LinkedHashMap<>();
        float myCurrency = exchangeRatesService.getRates().stream()
                .filter(currency -> currency.getCode().equals(exchangeOptions.getMyCurrency()))
                .findFirst().get().getMid();
        RateInfo euro = exchangeRatesService.getRates().stream()
                .filter(currency -> currency.getCode().equals("EUR"))
                .findFirst().get();
        RateInfo pound = exchangeRatesService.getRates().stream()
                .filter(currency -> currency.getCode().equals("GBP"))
                .findFirst().get();
        RateInfo dollar = exchangeRatesService.getRates().stream()
                .filter(currency -> currency.getCode().equals("USD"))
                .findFirst().get();
        RateInfo franc = exchangeRatesService.getRates().stream()
                .filter(currency -> currency.getCode().equals("CHF"))
                .findFirst().get();
        rates.put(euro.getCode(), myCurrency / euro.getMid());
        rates.put(pound.getCode(), myCurrency / pound.getMid());
        rates.put(dollar.getCode(), myCurrency / dollar.getMid());
        rates.put(franc.getCode(),  myCurrency / franc.getMid());

        return rates;
    }

    private String findMyCurrencyName(ExchangeOptions exchangeOptions){
        RateInfo currency = exchangeRatesService.getRates().stream()
                .filter(cu -> cu.getCode().equals(exchangeOptions.getMyCurrency()))
                .findFirst().get();
        String currencyName = currency.getCurrency();
        return currencyName;
    }
    private String findTargetCurrencyName(ExchangeOptions exchangeOptions){
        RateInfo currency = exchangeRatesService.getRates().stream()
                .filter(cu -> cu.getCode().equals(exchangeOptions.getTargetCurrency()))
                .findFirst().get();
        String currencyName = currency.getCurrency();
        return currencyName;
    }

    public float calculateTheAmountOfMoney(ExchangeOptions exchangeOptions){
       return exchangeOptions.getQuantity() * mainRate;

    }
}
