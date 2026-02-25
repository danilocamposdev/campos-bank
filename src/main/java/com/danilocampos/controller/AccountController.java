
package com.danilocampos.controller;

import java.util.List;
import java.util.Scanner;

import com.danilocampos.model.Account;
import com.danilocampos.model.AccountNumber;
import com.danilocampos.model.AccountType;
import com.danilocampos.model.Agency;
import com.danilocampos.model.CPF;
import com.danilocampos.service.ActorService;
import com.danilocampos.service.InvestmentHoldingService;
import com.danilocampos.service.TransactionService;
import com.danilocampos.view.CommonViews;
import com.danilocampos.view.TableView;

public class AccountController {

	private final ActorService actorsService;
	private final TransactionService transactionService;
	private final InvestmentHoldingService investmentService;
	private final Scanner scanner;

	public AccountController(
			final ActorService actorsService,
			final TransactionService transactionService,
			final InvestmentHoldingService investmentService,
			final Scanner scanner) {
		this.actorsService = actorsService;
		this.transactionService = transactionService;
		this.investmentService = investmentService;
		this.scanner = scanner;
	}

	public void createAccount() {
		CommonViews.askUser("Informe o nome completo do titular da conta:");
		String name = scanner.nextLine();

		CPF cpf = null;
		do {
			try {
				CommonViews.askUser("Informe o cpf do titular da conta (somente digitos):");
				String cpfString = scanner.next();
				cpf = new CPF(cpfString);
			} catch (IllegalArgumentException e) {
				CommonViews.showMessage(e.getMessage());
			}
		} while (cpf == null);

		AccountType accountType;
		do {
			int accountTypeOption;
			CommonViews.askUser("Escolha o tipo da conta (1 = Poupança, 2 = Corrente):");
			accountTypeOption = scanner.nextInt();
			accountType = switch (accountTypeOption) {
				case 1 -> AccountType.SAVINGS;
				case 2 -> AccountType.CHECKING;
				default -> {
					CommonViews.showMessage("Opção invalida.");
					yield null;
				}
			};
		} while (accountType == null);

		try {
			Account newAccount = actorsService.createAccount(name, accountType, cpf);
			CommonViews.showMessage("Conta criada com sucesso!");
			TableView<Account> accountView = new TableView<>(List.of(newAccount));
			accountView.addColumn("Nome do Proprietário", Account::getName);
			accountView.addColumn("CPF", Account::getCpf);
			accountView.addColumn("Tipo", Account::getType);
			accountView.addColumn("Agência", Account::getAgency);
			accountView.addColumn("Conta", Account::getAccountNumber);
			accountView.addColumn("Saldo", Account::getBalance);
			accountView.show();

		} catch (RuntimeException e) {
			CommonViews.showMessage("Erro ao criar conta: " + e.getMessage() + " Tente novamente.");
		}
	}

	public void listAccounts() {
		TableView<Account> accountsTable = new TableView<>(actorsService.list());
		accountsTable.addColumn("Criado em", Account::getFormatedCreatedAt);
		accountsTable.addColumn("Nome do Proprietário", Account::getName);
		accountsTable.addColumn("CPF", Account::getCpf);
		accountsTable.addColumn("Tipo", Account::getType);
		accountsTable.addColumn("Agência", Account::getAgency);
		accountsTable.addColumn("Conta", Account::getAccountNumber);
		accountsTable.addColumn("Saldo", Account::getBalance);
		accountsTable.show();
	}

	public void enterAccount() {
		if (actorsService.list().size() == 0) {
			CommonViews.showMessage("É preciso criar no mínimo uma conta antes de fazer login");
			return;
		}

		listAccounts();
		Account account = null;
		do {
			Agency agency = null;
			do {
				try {
					CommonViews.askUser("Informe a agência (4 dígitos):");
					String agencyString = scanner.nextLine();
					agency = new Agency(agencyString);
				} catch (IllegalArgumentException e) {
					CommonViews.showMessage(e.getMessage());
				}
			} while (agency == null);

			AccountNumber accountNumber = null;
			do {
				try {
					CommonViews.askUser("Informe o número da conta (5 dígitos):");
					String accountNumberString = scanner.nextLine();
					accountNumber = new AccountNumber(accountNumberString);
				} catch (IllegalArgumentException e) {
					CommonViews.showMessage(e.getMessage());
				}
			} while (accountNumber == null);

			try {
				account = actorsService.findAccount(agency, accountNumber);
			} catch (IllegalArgumentException e) {
				CommonViews.showMessage(e.getMessage());
			}
		} while (account == null);

		AccountMenuController accountMenuController = new AccountMenuController(
				transactionService,
				actorsService,
				investmentService,
				scanner,
				account);

		accountMenuController.openAccountMenu();
	}
}
