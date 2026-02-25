package com.danilocampos.model;

public enum AccountType {
	SAVINGS,
	CHECKING;

	@Override
	public String toString() {
		return switch (this) {
			case SAVINGS -> "Poupança";
			case CHECKING -> "Corrente";
			default -> super.toString();
		};
	}
}
