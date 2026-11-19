package ua.andrii.tdd.parking;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;

public record Tariff(Map<VehicleType, BigDecimal> ratePerMinute, BigDecimal extraCharge, Duration extraChargeInterval) {

	private static final long SECONDS_PER_MINUTE = 60;

	public Tariff {
		Objects.requireNonNull(ratePerMinute, "ratePerMinute");
		Objects.requireNonNull(extraCharge, "extraCharge");
		Objects.requireNonNull(extraChargeInterval, "extraChargeInterval");
		for (VehicleType type : VehicleType.values()) {
			BigDecimal rate = ratePerMinute.get(type);
			if (rate == null || rate.signum() < 0) {
				throw new IllegalArgumentException("Rate per minute for " + type + " must be set and not negative");
			}
		}
		if (extraCharge.signum() < 0) {
			throw new IllegalArgumentException("Extra charge must not be negative");
		}
		if (!extraChargeInterval.isPositive()) {
			throw new IllegalArgumentException("Extra charge interval must be positive");
		}
		ratePerMinute = Map.copyOf(ratePerMinute);
	}

	public BigDecimal charge(VehicleType type, Duration parked) {
		long startedMinutes = Math.ceilDiv(parked.toSeconds(), SECONDS_PER_MINUTE);
		long fullIntervals = parked.dividedBy(extraChargeInterval);
		return ratePerMinute.get(type).multiply(BigDecimal.valueOf(startedMinutes))
				.add(extraCharge.multiply(BigDecimal.valueOf(fullIntervals)))
				.setScale(2, RoundingMode.HALF_UP);
	}

}
