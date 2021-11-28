package pl.zalewskigrzegorz.exchange;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@ToString
public class RateInfo implements Serializable {

        private String currency;
        private String code;

        private float mid;
    }


