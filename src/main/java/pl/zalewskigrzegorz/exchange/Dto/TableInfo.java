package pl.zalewskigrzegorz.exchange.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.zalewskigrzegorz.exchange.Dto.RateInfo;


import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class TableInfo {

    private String table ;
    private String no;
    private String effectiveDate ;

    private List<RateInfo> rates;


    public TableInfo(String table, String country, String symbol, String currency, String code, List<RateInfo> rateArrayList) {
    }
}
