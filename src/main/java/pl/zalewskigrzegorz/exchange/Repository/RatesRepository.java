package pl.zalewskigrzegorz.exchange.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zalewskigrzegorz.exchange.Dto.RateInfo;

public interface RatesRepository extends JpaRepository<RateInfo, Long> {
}
