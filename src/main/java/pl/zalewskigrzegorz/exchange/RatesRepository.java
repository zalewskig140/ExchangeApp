package pl.zalewskigrzegorz.exchange;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RatesRepository extends JpaRepository<RateInfo, Long> {
}
