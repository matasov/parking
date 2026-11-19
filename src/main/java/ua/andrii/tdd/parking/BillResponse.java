package ua.andrii.tdd.parking;

import java.math.BigDecimal;
import java.time.Instant;

public record BillResponse(String vehicleReg, BigDecimal vehicleCharge, Instant timeIn, Instant timeOut) {
}
