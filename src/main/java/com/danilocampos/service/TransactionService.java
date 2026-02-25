package com.danilocampos.service;

import java.util.List;

import com.danilocampos.model.Account;
import com.danilocampos.model.Actor;
import com.danilocampos.model.Atm;
import com.danilocampos.model.InvestmentManager;
import com.danilocampos.model.Money;
import com.danilocampos.model.Transaction;
import com.danilocampos.model.TransactionType;
import com.danilocampos.repository.TransactionRepository;

public class TransactionService {
	private final TransactionRepository repository;
	private final Atm atm = new Atm();
	private final InvestmentManager investmentManager = new InvestmentManager();

	public TransactionService(final TransactionRepository repository) {
		this.repository = repository;
	}

	public void executePix(
			final Account origin,
			final Account destination,
			final Money amount) {

		executeTransaction(origin, destination, TransactionType.PIX, amount);
	}

	public void deposit(final Account account, final Money amount) {

		executeTransaction(atm, account, TransactionType.DEPOSIT, amount);
	}

	public void withdrawal(final Account account, final Money amount) {
		executeTransaction(account, atm, TransactionType.WITHDRAWAL, amount);
	}

	public void executeInvestment(final Account account, final Money ammount) {
		executeTransaction(account, investmentManager, TransactionType.INVESTMENT_DEPOSIT, ammount);
	}

	public void redeemInvestment(final Account account, final Money ammount) {
		executeTransaction(investmentManager, account, TransactionType.REDEMPTION, ammount);
	}

	private void executeTransaction(
			final Actor origin,
			final Actor destination,
			final TransactionType type,
			final Money amount) {

		if (amount.valueInCents() < 0)
			throw new IllegalArgumentException("A quantia da transação não pode ser negativa");

		if (origin instanceof Account originAccount) {
			if (originAccount.getBalance().valueInCents() < amount.valueInCents())
				throw new IllegalArgumentException("Saldo insuficiente para a transação");
			originAccount.withdraw(amount);
		}

		if (destination instanceof Account destinationAccount) {
			destinationAccount.deposit(amount);
		}

		Transaction transaction = new Transaction(origin, destination, type, amount);
		repository.save(transaction);
	}

	public List<Transaction> getHistory() {
		return repository.findAll();
	}

	public List<Transaction> getAccountHistory(final Account account) {
		return repository.findByActor(account);
	}

}
