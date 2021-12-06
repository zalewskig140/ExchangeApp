package pl.zalewskigrzegorz.exchange.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExchangeOptions {
    private int quantity;
   private String myCurrency;
   private String targetCurrency;




}
