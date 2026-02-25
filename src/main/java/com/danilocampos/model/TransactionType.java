package com.danilocampos.model;

public enum TransactionType {
	PIX,
	WITHDRAWAL,
	DEPOSIT,
	INVESTMENT_DEPOSIT,
	REDEMPTION;

	@Override
	public String toString() {
		return switch(this) {
			case PIX -> "Transferência PIX";
			case WITHDRAWAL -> "Saque";
			case DEPOSIT -> "Depósito";
			case INVESTMENT_DEPOSIT -> "Aplicação de investimento";
			case REDEMPTION -> "Resgate de investimento";
		default -> super.toString();
		};
	}
}
