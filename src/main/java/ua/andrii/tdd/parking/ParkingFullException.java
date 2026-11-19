package ua.andrii.tdd.parking;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ParkingFullException extends ResponseStatusException {

	public ParkingFullException() {
		super(HttpStatus.CONFLICT, "No free parking space");
	}

}
