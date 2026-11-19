package ua.andrii.tdd.parking;

import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("parking")
public record ParkingProperties(int capacity, Tariff tariff) {

	public ParkingProperties {
		if (capacity < 1) {
			throw new IllegalArgumentException("Capacity must be positive, got " + capacity);
		}
		Objects.requireNonNull(tariff, "tariff");
	}

}
