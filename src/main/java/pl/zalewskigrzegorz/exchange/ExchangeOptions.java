package pl.zalewskigrzegorz.exchange;

import lombok.Data;

@Data
public class ExchangeOptions {
    private int quantity;
   private String myCurrency;
   private String targetCurrency;
}
