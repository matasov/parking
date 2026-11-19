package ua.andrii.tdd.parking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.andrii.tdd.parking.VehicleType.MEDIUM;
import static ua.andrii.tdd.parking.VehicleType.SMALL;

import java.time.Clock;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class ParkingServiceTest {

	private static final Instant TIME_IN = Instant.parse("2026-11-19T17:30:00Z");
	private static final Instant TIME_OUT = Instant.parse("2026-11-19T17:45:00Z");

	private final Clock clock = mock(Clock.class);
	private final ParkingService service = new ParkingService(new ParkingProperties(2, TariffTest.TARIFF), clock);

	@Test
	void parksInFirstFreeSpace() {
		when(clock.instant()).thenReturn(TIME_IN);

		assertThat(service.park("A1", SMALL)).isEqualTo(new ParkResponse("A1", 1, TIME_IN));
		assertThat(service.park("B2", MEDIUM).spaceNumber()).isEqualTo(2);
		assertThat(service.status()).isEqualTo(new StatusResponse(0, 2));
	}

	@Test
	void rejectsWhenFull() {
		when(clock.instant()).thenReturn(TIME_IN);
		service.park("A1", SMALL);
		service.park("B2", SMALL);

		assertThatThrownBy(() -> service.park("C3", SMALL)).isInstanceOf(ParkingFullException.class);
	}

	@Test
	void rejectsSameVehicleTwice() {
		when(clock.instant()).thenReturn(TIME_IN);
		service.park("A1", SMALL);

		assertThatThrownBy(() -> service.park("A1", SMALL)).isInstanceOf(VehicleAlreadyParkedException.class);
	}

	@Test
	void billsAndFreesSpace() {
		when(clock.instant()).thenReturn(TIME_IN, TIME_OUT);
		service.park("A1", SMALL);

		BillResponse bill = service.bill("A1");

		assertThat(bill.vehicleCharge()).isEqualByComparingTo("4.50");
		assertThat(bill.timeIn()).isEqualTo(TIME_IN);
		assertThat(bill.timeOut()).isEqualTo(TIME_OUT);
		assertThat(service.status()).isEqualTo(new StatusResponse(2, 0));
	}

	@Test
	void rejectsBillForUnknownVehicle() {
		assertThatThrownBy(() -> service.bill("A1")).isInstanceOf(VehicleNotParkedException.class);
	}

}
