package com.danilocampos.model;

import java.util.concurrent.ThreadLocalRandom;

public class Agency {

	private final String agencyNumber;

	public Agency() {
		this.agencyNumber = generateAgency();
	}

	private String generateAgency() {
		int number = ThreadLocalRandom.current().nextInt(1, 5);
		StringBuilder stringNumber = new StringBuilder("0".repeat(3));
		stringNumber.append(number);
		return stringNumber.toString();
	}

	public Agency(final String agencyNumber) {
		if (agencyNumber == null || agencyNumber.isEmpty())
			throw new IllegalArgumentException("O número da agência não pode ser vazio");

		if (!agencyNumber.matches("\\d+"))
			throw new IllegalArgumentException("O número da agência deve conter apenas números");

		if (agencyNumber.length() != 4)
			throw new IllegalArgumentException("O número da agência deve ter 4 digitos");

		this.agencyNumber = agencyNumber;
	}

	public String get() {
		return agencyNumber;
	}

	@Override
	public String toString() {
		return agencyNumber;
	}

	@Override
	public boolean equals(Object obj) {
	    return obj.equals(agencyNumber);
	}
}
