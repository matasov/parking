package ua.andrii.tdd.parking;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ParkingApplicationTests {

	@Autowired
	private ParkingProperties properties;

	@Test
	void bindsSettings() {
		assertThat(properties.capacity()).isEqualTo(10);
		assertThat(properties.tariff().ratePerMinute().get(VehicleType.LARGE)).isEqualByComparingTo("0.40");
		assertThat(properties.tariff().extraCharge()).isEqualByComparingTo(BigDecimal.ONE);
		assertThat(properties.tariff().extraChargeInterval()).isEqualTo(Duration.ofMinutes(5));
	}

}
