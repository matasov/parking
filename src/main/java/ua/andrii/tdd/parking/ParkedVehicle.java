package ua.andrii.tdd.parking;

import java.time.Instant;

public record ParkedVehicle(String vehicleReg, VehicleType type, Instant timeIn) {
}
