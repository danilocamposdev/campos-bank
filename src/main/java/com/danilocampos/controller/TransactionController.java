package com.danilocampos.controller;

import java.util.Scanner;

import com.danilocampos.model.Account;
import com.danilocampos.model.CPF;
import com.danilocampos.model.Money;
import com.danilocampos.model.Transaction;
import com.danilocampos.service.ActorService;
import com.danilocampos.service.TransactionService;
import com.danilocampos.view.CommonViews;
import com.danilocampos.view.TableView;

public class TransactionController {

	private final ActorService actorsService;
	private final TransactionService transactionService;
	private final Scanner scanner;

	public TransactionController(
			final TransactionService transactionService,
			final ActorService actorsService,
			final Scanner scanner) {
		this.transactionService = transactionService;
		this.actorsService = actorsService;
		this.scanner = scanner;
	}

	public static void listTransactions(final TransactionService transactionService) {
		TableView<Transaction> transactionsTable = new TableView<>(transactionService.getHistory());
		transactionsTable.addColumn("Horário da Transação", Transaction::getFormatedCreatedAt);
		transactionsTable.addColumn("Tipo", Transaction::getType);
		transactionsTable.addColumn("Origem", Transaction::getOrigin);
		transactionsTable.addColumn("Destino", Transaction::getDestination);
		transactionsTable.addColumn("Valor", Transaction::getAmount);

		transactionsTable.show();
	}

	public void makeTransactionPix(final Account account) {
		Account destinationAccount = null;

		do {
			CPF cpfPix = null;
			do {
				try {
					CommonViews.askUser("Informe a chave pix da conta que receberá a sua transferência (CPF):");
					String cpfPixString = scanner.nextLine();
					cpfPix = new CPF(cpfPixString);
					if (cpfPix.equals(account.getCpf())) {
						cpfPix = null;
						throw new IllegalArgumentException("Você não pode enviar um pix para si próprio.");
					}
					cpfPix = new CPF(cpfPixString);
				} catch (IllegalArgumentException e) {
					CommonViews.showMessage(e.getMessage());
				}
			} while (cpfPix == null);

			try {
				destinationAccount = actorsService.findAccountByCPF(cpfPix);
			} catch (IllegalArgumentException e) {
				CommonViews.showMessage(e.getMessage());
			}
		} while (destinationAccount == null);

		Money ammount = null;
		do {
			try {
				CommonViews.askUser("Informe o valor a ser transferido para " + destinationAccount.getName()
						+ " (em centavos e sem vírgula):");
				long ammountLong = scanner.nextLong();
				ammount = new Money(ammountLong);
			} catch (IllegalArgumentException e) {
				CommonViews.showMessage(e.getMessage());
			}
		} while (ammount == null);

		try {
			transactionService.executePix(account, destinationAccount, ammount);
			CommonViews.showMessage("Pix realizado com sucesso!");
		} catch (IllegalArgumentException e) {
			CommonViews.showMessage(e.getMessage());
		}
	}

	public void deposit(final Account account) {
		Money ammount = null;
		do {
			try {
				CommonViews.askUser("Informe o valor a ser depositado (em centavos e sem virgula)");
				long ammountLong = scanner.nextLong();
				ammount = new Money(ammountLong);
			} catch (IllegalArgumentException e) {
				CommonViews.showMessage(e.getMessage());
			}
		} while (ammount == null);

		try {
			transactionService.deposit(account, ammount);
			;
			CommonViews.showMessage("Deposito realizado com sucesso!");
		} catch (IllegalArgumentException e) {
			CommonViews.showMessage(e.getMessage());
		}
	}

	public void withdraw(final Account account) {
		Money ammount = null;
		do {
			try {
				CommonViews.askUser("Informe o valor a ser sacado (em centavos e sem virgula)");
				long ammountLong = scanner.nextLong();
				ammount = new Money(ammountLong);
			} catch (IllegalArgumentException e) {
				CommonViews.showMessage(e.getMessage());
			}
		} while (ammount == null);

		try {
			transactionService.withdrawal(account, ammount);
			CommonViews.showMessage("saque realizado com sucesso!");
		} catch (IllegalArgumentException e) {
			CommonViews.showMessage(e.getMessage());
		}
	}

	public void listTransactions() {
		listTransactions(transactionService);
	}

	public void bankStatement(final Account account) {
		
		TableView<Transaction> transactionsTable = new TableView<>(transactionService.getAccountHistory(account));
		transactionsTable.addColumn("Horário da Transação", Transaction::getFormatedCreatedAt);
		transactionsTable.addColumn("Tipo", Transaction::getType);
		transactionsTable.addColumn("Origem", Transaction::getOrigin);
		transactionsTable.addColumn("Destino", Transaction::getDestination);
		transactionsTable.addColumn("Valor", Transaction::getAmount);

		transactionsTable.show();
	}

}
