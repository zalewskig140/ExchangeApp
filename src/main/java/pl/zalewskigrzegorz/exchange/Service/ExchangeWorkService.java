package pl.zalewskigrzegorz.exchange.Service;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zalewskigrzegorz.exchange.Dto.ExchangeOptions;
import pl.zalewskigrzegorz.exchange.Dto.RateInfo;
import pl.zalewskigrzegorz.exchange.Repository.TransactionRepository;
import pl.zalewskigrzegorz.exchange.Dto.Transaction;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Service
@Log
public class ExchangeWorkService {
    private ExchangeOptions exchangeOptions;
    private String mainRate;
    private Map<String, String> otherRates;
    private String myCurrencyName;
    private String targetCurrencyName;
    private double amount;
    private double myCurrencyQuantity;


    @Autowired
    private ExchangeRatesService exchangeRatesService;
    @Autowired
    private TransactionRepository transactionRepository;

    public void convert(ExchangeOptions exchangeOptions) {
        this.exchangeOptions = exchangeOptions;
        this.mainRate = convertMainCurrency(exchangeOptions);
        this.otherRates = convertOtherCurencies(exchangeOptions);
        this.myCurrencyName = findMyCurrencyName(exchangeOptions);
        this.targetCurrencyName = findTargetCurrencyName(exchangeOptions);
        this.amount = calculateTheAmountOfMoney(exchangeOptions);
        this.myCurrencyQuantity = exchangeOptions.getQuantity();
    }

    public String convertMainCurrency(ExchangeOptions exchangeOptions) {
        double myCurrency = exchangeRatesService.getRates().stream()
                .filter(currency -> currency.getCode().equals(exchangeOptions.getMyCurrency()))
                .findFirst().get().getMid();


        double targetCurrency = exchangeRatesService.getRates().stream()
                .filter(currency -> currency.getCode().equals(exchangeOptions.getTargetCurrency()))
                .findFirst().get().getMid();
        double mainRate = myCurrency / targetCurrency;


        return round(mainRate);
    }

    public Map<String, String> convertOtherCurencies(ExchangeOptions exchangeOptions) {
        Map<String, String> rates = new LinkedHashMap<>();
        double myCurrency = exchangeRatesService.getRates().stream()
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
        rates.put(euro.getCode(), round(myCurrency / euro.getMid()));
        rates.put(pound.getCode(), round(myCurrency / pound.getMid()));
        rates.put(dollar.getCode(), round(myCurrency / dollar.getMid()));
        rates.put(franc.getCode(), round(myCurrency / franc.getMid()));

        return rates;
    }

    private String findMyCurrencyName(ExchangeOptions exchangeOptions) {
        RateInfo currency = exchangeRatesService.getRates().stream()
                .filter(cu -> cu.getCode().equals(exchangeOptions.getMyCurrency()))
                .findFirst().get();
        String currencyName = currency.getCurrency();
        return currencyName;
    }

    private String findTargetCurrencyName(ExchangeOptions exchangeOptions) {
        RateInfo currency = exchangeRatesService.getRates().stream()
                .filter(cu -> cu.getCode().equals(exchangeOptions.getTargetCurrency()))
                .findFirst().get();
        String currencyName = currency.getCurrency();
        return currencyName;
    }

    public double calculateTheAmountOfMoney(ExchangeOptions exchangeOptions) {
        double quantity = exchangeOptions.getQuantity() * Double.parseDouble(mainRate);
        quantity = Math.round(quantity * 100);
        quantity /= 100;

        return quantity;

    }

    public String round(double number) {
        int index = 1;
        String n = "";
        String num = Double.toString(number);
        char[] ch = num.toCharArray();

        for (int i = 0; i < ch.length; i++) {
            if (ch[i] != '0' && ch[i] != '.') {
                index = i;
                break;
            }
        }
        for (int c = 0; c < ch.length; c++) {
            if (ch[c] == 'E') {
                index = 9;
            }

        }

        switch (index) {
            case 0:
            case 2:
                number = Math.round(number * 10000);
                number /= 10000;
                n = String.valueOf(number);

                break;
            case 3:
                number = Math.round(number * 100000);
                number /= 100000;
                n = String.valueOf(number);
                break;
            case 4:
                number = Math.round(number * 1000000);
                number /= 1000000;
                n = String.valueOf(number);
                break;
            case 5:
                number = Math.round(number * 10000000);
                number /= 10000000;
                n = String.valueOf(number);
                break;
            case 6:
                number = Math.round(number * 100000000);
                number /= 100000000;
                n = String.valueOf(number);
                break;
            case 9:
                n = String.format("%f", number);
                n = n.replaceAll(",", ".");
                break;


        }
        return n;

    }

    public List<Transaction> getTransactionHistory() {
        List<Transaction> transactionHistory = transactionRepository.findAll();
        return transactionHistory;
    }

    public void deleteOldTransaction() {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(7);
        transactionRepository.deleteByLocalDateTimetBefore(dateTime);


    }


}
