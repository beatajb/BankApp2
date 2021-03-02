package com.example.bank.exceptions;

import lombok.NonNull;

public class AccountDoesNotExist extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public AccountDoesNotExist(String name, @NonNull String string) {
		super("Either the account: " + name + " or the account: "+string+" does not exist.");
	}
}