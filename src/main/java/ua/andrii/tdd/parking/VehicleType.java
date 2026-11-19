package ua.andrii.tdd.parking;

public enum VehicleType {

	SMALL(1),
	MEDIUM(2),
	LARGE(3);

	private final int code;

	VehicleType(int code) {
		this.code = code;
	}

	public static VehicleType fromCode(int code) {
		for (VehicleType type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		throw new InvalidVehicleTypeException(code);
	}

}
