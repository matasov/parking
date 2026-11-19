package ua.andrii.tdd.parking;

import java.time.Instant;

public record ParkResponse(String vehicleReg, int spaceNumber, Instant timeIn) {
}
