package com.sahil.smart_parking_project.globalException;

public class UnauthorizedActionException extends RuntimeException {
	public UnauthorizedActionException(String message) {
		super(message);
	}
}