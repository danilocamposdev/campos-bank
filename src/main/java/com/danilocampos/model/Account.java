package com.danilocampos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Account extends Actor {
	private final Agency agency;
	private final AccountNumber accountNumber;
	private final CPF cpf;
	private final AccountType type;
	private Money balance = new Money(0);

	private final LocalDateTime createdAt;

	public Account(
			final Agency agency,
			final AccountNumber accountNumber,
			final String name,
			final AccountType type,
			final CPF cpf) {
		super(name);
		this.agency = agency;
		this.accountNumber = accountNumber;
		this.type = type;
		this.cpf = cpf;
		this.createdAt = LocalDateTime.now();
	}

	public CPF getCpf() {
		return cpf;
	}

	public Money getBalance() {
		return balance;
	}

	public void deposit(final Money amount) {
		balance = balance.sum(amount);
	}

	public void withdraw(final Money amount) {
		balance = balance.subtract(amount);
	}

	public AccountType getType() {
		return type;
	}

	public Agency getAgency() {
		return agency;
	}

	public AccountNumber getAccountNumber() {
		return accountNumber;
	}

	public String getFormatedCreatedAt() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		return createdAt.format(formatter);
	}

	@Override
	public String toString() {
		return getName() + " " + getCpf();
	}
}
