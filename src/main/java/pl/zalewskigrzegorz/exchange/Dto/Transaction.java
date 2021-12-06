package pl.zalewskigrzegorz.exchange.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
@Entity(name = "TRANSACTIONS")
public class Transaction {


    @Id
    @GeneratedValue
    public Long id;
    public String date;
    public String myCurrency;
    public String targetCurrency;
    @Column(name = "TRANSACTION_DATE")
    public LocalDateTime localDateTimet;

    public int amount;

    public Transaction(String date, String myCurrency, String targetCurrency, int amount, LocalDateTime localDateTime) {

        this.date = date;
        this.myCurrency = myCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
        this.localDateTimet = localDateTime;
    }
}
