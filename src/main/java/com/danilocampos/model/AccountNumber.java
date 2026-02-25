package com.danilocampos.model;

import java.util.concurrent.ThreadLocalRandom;

public class AccountNumber {

	private final String value;

	public AccountNumber() {
		this.value = generateAccountNumber();
	}

	private String generateAccountNumber() {
		int number = ThreadLocalRandom.current().nextInt(1, 100000);
		return String.format("%05d", number);
	}

	public AccountNumber(final String value) {
		if (value == null || value.isEmpty())
			throw new IllegalArgumentException("O número da conta não pode ser vazio");

		if (!value.matches("\\d+"))
			throw new IllegalArgumentException("O número da conta deve conter apenas números");

		if (value.length() != 5)
			throw new IllegalArgumentException("O número da conta deve ter 5 digitos");

		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
	    return obj.equals(value);
	}
}
