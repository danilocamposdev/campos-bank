package com.danilocampos.controller;

import java.util.Scanner;

import com.danilocampos.model.Account;
import com.danilocampos.service.ActorService;
import com.danilocampos.service.InvestmentHoldingService;
import com.danilocampos.service.TransactionService;
import com.danilocampos.view.CommonViews;
import com.danilocampos.view.OptionsView;
import com.danilocampos.view.TableView;

public class AccountMenuController {

	private final TransactionService transactionService;
	private final ActorService actorService;
	private final InvestmentHoldingService investmentHoldingService;
	private final Scanner scanner;
	private final Account account;

	public AccountMenuController(
			final TransactionService transactionService,
			final ActorService actorService,
			final InvestmentHoldingService investmentHoldingService,
			final Scanner scanner,
			final Account account) {
		this.transactionService = transactionService;
		this.actorService = actorService;
		this.investmentHoldingService = investmentHoldingService;
		this.scanner = scanner;
		this.account = account;
	}

	public void openAccountMenu() {
		CommonViews.showLine();
		CommonViews.showMessage("Bem-vindo à sua conta " + account.getName() + "!");

		TransactionController transactionController = new TransactionController(transactionService, actorService,
				scanner);

		InvestmentController investmentController = new InvestmentController(investmentHoldingService,
				transactionService, scanner, account);

		OptionsView optionsView = new OptionsView();
		optionsView.addOption("Ver saldo");
		optionsView.addOption("Depositar");
		optionsView.addOption("Sacar");
		optionsView.addOption("Enviar PIX");
		optionsView.addOption("Ver extrato");
		optionsView.addOption("Investir");
		optionsView.addOption("Meus investimentos e rendimentos");
		optionsView.addOption("Resgatar meus investimentos");
		optionsView.addOption("Atualizar rendimentos dos investimentos");
		optionsView.addOption("Sair da conta");

		boolean exit = false;

		do {
			CommonViews.showLine();
			optionsView.show();
			int option = scanner.nextInt();
			scanner.nextLine();
			switch (option) {
				case 1 -> CommonViews.showMessage(account.getBalance().toString());
				case 2 -> transactionController.deposit(account);
				case 3 -> transactionController.withdraw(account);
				case 4 -> {
					if (actorService.list().size() < 2)
						CommonViews.showMessage("Para realizar uma transferência você deve cadastrar novas contas.");
					else {
						listOtherAccounts();
						transactionController.makeTransactionPix(account);
					}
				}
				case 5 -> transactionController.bankStatement(account);
				case 6 -> investmentController.invest();
				case 7 -> investmentController.listInvestmentsFromAccount();
				case 8 -> investmentController.redeem();
				case 9 -> investmentController.readjustAll();
				case 10 -> {
					CommonViews.showMessage("Saindo...");
					exit = true;
				}
				default -> CommonViews.showMessage("Opção invalida!");
			}
		} while (exit == false);

		CommonViews.title();
	}

	private void listOtherAccounts() {
		TableView<Account> accountsTable = new TableView<>(actorService.listOthers(account));
		accountsTable.addColumn("Criado em", Account::getFormatedCreatedAt);
		accountsTable.addColumn("Nome do Proprietário", Account::getName);
		accountsTable.addColumn("CPF", Account::getCpf);
		accountsTable.addColumn("Tipo", Account::getType);
		accountsTable.addColumn("Agência", Account::getAgency);
		accountsTable.addColumn("Conta", Account::getAccountNumber);
		accountsTable.addColumn("Saldo", Account::getBalance);
		accountsTable.show();
	}
}
