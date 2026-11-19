package ua.andrii.tdd.parking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TariffTest {

	static final Tariff TARIFF = new Tariff(
			Map.of(VehicleType.SMALL, new BigDecimal("0.10"),
					VehicleType.MEDIUM, new BigDecimal("0.20"),
					VehicleType.LARGE, new BigDecimal("0.40")),
			BigDecimal.ONE,
			Duration.ofMinutes(5));

	@ParameterizedTest
	@CsvSource({
		"SMALL,  0,   0.00",
		"SMALL,  1,   0.10",
		"SMALL,  60,  0.10",
		"SMALL,  61,  0.20",
		"SMALL,  299, 0.50",
		"SMALL,  300, 1.50",
		"SMALL,  600, 3.00",
		"MEDIUM, 300, 2.00",
		"LARGE,  308, 3.40"
	})
	void charges(VehicleType type, long seconds, String expected) {
		assertThat(TARIFF.charge(type, Duration.ofSeconds(seconds))).isEqualTo(expected);
	}

	@Test
	void requiresRateForEveryType() {
		assertThatThrownBy(() -> new Tariff(Map.of(VehicleType.SMALL, BigDecimal.ONE), BigDecimal.ONE, Duration.ofMinutes(5)))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void rejectsNegativeRate() {
		Map<VehicleType, BigDecimal> rates = Map.of(VehicleType.SMALL, new BigDecimal("-0.10"),
				VehicleType.MEDIUM, BigDecimal.ONE, VehicleType.LARGE, BigDecimal.ONE);

		assertThatThrownBy(() -> new Tariff(rates, BigDecimal.ONE, Duration.ofMinutes(5)))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void requiresPositiveInterval() {
		assertThatThrownBy(() -> new Tariff(TARIFF.ratePerMinute(), BigDecimal.ONE, Duration.ZERO))
				.isInstanceOf(IllegalArgumentException.class);
	}

}
