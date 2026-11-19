package ua.andrii.tdd.parking;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidVehicleTypeException extends ResponseStatusException {

	public InvalidVehicleTypeException(int code) {
		super(HttpStatus.BAD_REQUEST, "Vehicle type must be 1, 2 or 3, got " + code);
	}

}
