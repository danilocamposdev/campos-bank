package com.danilocampos.model;

public class InvestmentHolding {

	private final Account account;
	private final Investment investment;
	private Money balance;

	public InvestmentHolding(
			final Account account,
			final Investment investment,
			final Money initialInvestment) {
		this.account = account;
		this.investment = investment;
		this.balance = initialInvestment;
	}

	public Account getAccount() {
		return account;
	}

	public Investment getInvestment() {
		return investment;
	}

	public Money getBalance() {
		return balance;
	}

	public long getTax() {
		return investment.tax();
	}

	public String getFormattedTax() {
		return investment.tax() + "%";
	}

	public void readjustBalance() {
		balance = new Money(balance.valueInCents() + (balance.valueInCents() * investment.tax() / 100));
	}

	public void clearBalance() {
		balance = new Money(0);
	}
}
