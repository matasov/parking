package ua.andrii.tdd.parking;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ParkingPropertiesTest {

	@Test
	void requiresPositiveCapacity() {
		assertThatThrownBy(() -> new ParkingProperties(0, TariffTest.TARIFF))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void requiresTariff() {
		assertThatThrownBy(() -> new ParkingProperties(1, null))
				.isInstanceOf(NullPointerException.class);
	}

}
