package ua.andrii.tdd.parking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParkRequest(@NotBlank String vehicleReg, @NotNull Integer vehicleType) {
}
