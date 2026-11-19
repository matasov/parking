package ua.andrii.tdd.parking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class VehicleTypeTest {

	@ParameterizedTest
	@CsvSource({"1, SMALL", "2, MEDIUM", "3, LARGE"})
	void mapsCode(int code, VehicleType expected) {
		assertThat(VehicleType.fromCode(code)).isEqualTo(expected);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 4})
	void rejectsUnknownCode(int code) {
		assertThatThrownBy(() -> VehicleType.fromCode(code)).isInstanceOf(InvalidVehicleTypeException.class);
	}

}
