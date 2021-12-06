package pl.zalewskigrzegorz.exchange.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity(name = "RATES")
public class RateInfo  {
    @Id
   @GeneratedValue
public Long id;
        private String currency;
        private String code;
        private double mid;

    public RateInfo(String currency, String code, double mid) {
        this.currency = currency;
        this.code = code;
        this.mid = mid;
    }
}


