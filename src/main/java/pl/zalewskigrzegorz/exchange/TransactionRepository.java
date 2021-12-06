package pl.zalewskigrzegorz.exchange;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
 @Modifying
 @Transactional
void deleteByLocalDateTimetBefore(LocalDateTime localDateTime);
}
