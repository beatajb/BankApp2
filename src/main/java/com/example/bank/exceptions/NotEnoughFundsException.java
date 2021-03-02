package com.example.bank.exceptions;

public class NotEnoughFundsException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public NotEnoughFundsException(String name) {
		super("The account: " + name + " does not have enought funds to conduct this transaction.");
	}
}