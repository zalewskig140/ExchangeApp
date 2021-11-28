package pl.zalewskigrzegorz.exchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.Date;
import java.util.List;
import java.util.Map;

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
