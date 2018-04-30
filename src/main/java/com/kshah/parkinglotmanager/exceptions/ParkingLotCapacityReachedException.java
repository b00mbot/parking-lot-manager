package com.kshah.parkinglotmanager.exceptions;

public class ParkingLotCapacityReachedException extends RuntimeException {

    public ParkingLotCapacityReachedException(String message) {
        super(message);
    }

    public ParkingLotCapacityReachedException(String message, Throwable cause) {
        super(message, cause);
    }
}
