package pl.zalewskigrzegorz.exchange.Dto;

import lombok.Data;

@Data
public class ExchangeOptions {
    private int quantity;
   private String myCurrency;
   private String targetCurrency;
}
