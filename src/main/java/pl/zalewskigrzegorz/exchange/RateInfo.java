package pl.zalewskigrzegorz.exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RateInfo  {

        private String currency;
        private String code;
        private float mid;
    }


