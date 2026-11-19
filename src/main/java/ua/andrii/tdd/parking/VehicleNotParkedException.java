package ua.andrii.tdd.parking;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VehicleNotParkedException extends ResponseStatusException {

	public VehicleNotParkedException(String vehicleReg) {
		super(HttpStatus.NOT_FOUND, "Vehicle " + vehicleReg + " is not parked");
	}

}
