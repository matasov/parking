package ua.andrii.tdd.parking;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {

	private final ParkedVehicle[] spaces;
	private final Tariff tariff;
	private final Clock clock;

	public ParkingService(ParkingProperties properties, Clock clock) {
		this.spaces = new ParkedVehicle[properties.capacity()];
		this.tariff = properties.tariff();
		this.clock = clock;
	}

	public synchronized StatusResponse status() {
		int occupied = (int) Arrays.stream(spaces).filter(Objects::nonNull).count();
		return new StatusResponse(spaces.length - occupied, occupied);
	}

	public synchronized ParkResponse park(String vehicleReg, VehicleType type) {
		if (spaceOf(vehicleReg).isPresent()) {
			throw new VehicleAlreadyParkedException(vehicleReg);
		}
		int index = firstFreeSpace().orElseThrow(ParkingFullException::new);
		Instant timeIn = clock.instant();
		spaces[index] = new ParkedVehicle(vehicleReg, type, timeIn);
		return new ParkResponse(vehicleReg, index + 1, timeIn);
	}

	public synchronized BillResponse bill(String vehicleReg) {
		int index = spaceOf(vehicleReg).orElseThrow(() -> new VehicleNotParkedException(vehicleReg));
		ParkedVehicle vehicle = spaces[index];
		Instant timeOut = clock.instant();
		BigDecimal charge = tariff.charge(vehicle.type(), Duration.between(vehicle.timeIn(), timeOut));
		spaces[index] = null;
		return new BillResponse(vehicleReg, charge, vehicle.timeIn(), timeOut);
	}

	private OptionalInt spaceOf(String vehicleReg) {
		return IntStream.range(0, spaces.length)
				.filter(i -> spaces[i] != null && spaces[i].vehicleReg().equals(vehicleReg))
				.findFirst();
	}

	private OptionalInt firstFreeSpace() {
		return IntStream.range(0, spaces.length)
				.filter(i -> spaces[i] == null)
				.findFirst();
	}

}
