# Parking Management API

REST API for a car park. State is kept in process memory only.

Java 25, Spring Boot 4.1, Gradle.

## Running

```bash
./gradlew bootRun
./gradlew test
```

## API

```bash
curl localhost:8080/parking

curl -X POST localhost:8080/parking -H 'Content-Type: application/json' \
  -d '{"vehicleReg":"AB12CDE","vehicleType":1}'

curl -X POST localhost:8080/parking/bill -H 'Content-Type: application/json' \
  -d '{"vehicleReg":"AB12CDE"}'
```

| Error | HTTP |
|---|---|
| Car park is full | 409 |
| Vehicle is already parked | 409 |
| Vehicle not found | 404 |
| `vehicleType` not 1/2/3, blank registration, malformed body | 400 |

Error body is Problem Details (RFC 9457): `{"title":"Conflict","status":409,"detail":"No free parking space","instance":"/parking"}`.

## Configuration

Capacity and tariff are set in `src/main/resources/application.yaml`:

```yaml
parking:
  capacity: 10
  tariff:
    rate-per-minute:
      SMALL: 0.10
      MEDIUM: 0.20
      LARGE: 0.40
    extra-charge: 1.00
    extra-charge-interval: 5m
```

Any value can be overridden at startup, e.g. `--parking.tariff.rate-per-minute.SMALL=0.15`.
The application fails to start if capacity is not positive, a vehicle type has no rate, a rate or the extra charge is negative, or the interval is not positive.

## Assumptions

- Every started minute is charged (ceil); the extra £1 is charged for every full 5 minutes (floor).
- A vehicle takes the free space with the lowest number.
- Registration is compared as is, without normalization.
- Time is UTC with one-second precision.
- A space is freed only after the charge is calculated.
