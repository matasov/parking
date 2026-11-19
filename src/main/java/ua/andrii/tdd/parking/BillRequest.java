package ua.andrii.tdd.parking;

import jakarta.validation.constraints.NotBlank;

public record BillRequest(@NotBlank String vehicleReg) {
}
