package pl.zalewskigrzegorz.exchange.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import pl.zalewskigrzegorz.exchange.Dto.Transaction;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
 @Modifying
 @Transactional
void deleteByLocalDateTimetBefore(LocalDateTime localDateTime);
}
