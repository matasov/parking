package ua.andrii.tdd.parking;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking")
public class ParkingController {

	private final ParkingService parkingService;

	public ParkingController(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	@GetMapping
	public StatusResponse status() {
		return parkingService.status();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ParkResponse park(@Valid @RequestBody ParkRequest request) {
		return parkingService.park(request.vehicleReg(), VehicleType.fromCode(request.vehicleType()));
	}

	@PostMapping("/bill")
	public BillResponse bill(@Valid @RequestBody BillRequest request) {
		return parkingService.bill(request.vehicleReg());
	}

}
