package ua.andrii.tdd.parking;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VehicleAlreadyParkedException extends ResponseStatusException {

	public VehicleAlreadyParkedException(String vehicleReg) {
		super(HttpStatus.CONFLICT, "Vehicle " + vehicleReg + " is already parked");
	}

}
