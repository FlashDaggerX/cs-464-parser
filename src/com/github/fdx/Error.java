package com.github.fdx;

public class Error {
	public Error(String message, int line) {
		System.err.println("Line " + line + ": " + message);
		System.exit(0);
	}
}
