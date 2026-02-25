package com.danilocampos.controller;

import java.util.List;
import java.util.Scanner;

import com.danilocampos.model.Account;
import com.danilocampos.model.Investment;
import com.danilocampos.model.InvestmentHolding;
import com.danilocampos.model.Money;
import com.danilocampos.service.InvestmentHoldingService;
import com.danilocampos.service.TransactionService;
import com.danilocampos.view.CommonViews;
import com.danilocampos.view.OptionsView;
import com.danilocampos.view.TableView;

public class InvestmentController {

	private InvestmentHoldingService investmentHoldingService;
	private TransactionService transactionService;
	private Account account;
	private Scanner scanner;

	public InvestmentController(
			final InvestmentHoldingService investmentHoldingService,
			final TransactionService transactionService,
			final Scanner scanner,
			Account account) {
		this.investmentHoldingService = investmentHoldingService;
		this.account = account;
		this.transactionService = transactionService;
		this.scanner = scanner;
	}

	public static void listPortfolio(final InvestmentHoldingService investmentService) {
		TableView<Investment> table = new TableView<>(investmentService.listPortfolio());

		table.addColumn("Investimento", Investment::name);
		table.addColumn("Taxa de rendimento", Investment::getFormattedTax);
		table.addColumn("Valor mínimo", Investment::minimunAmount);

		table.show();
	}

	public static void listInvestmentHoldings(final InvestmentHoldingService investmentHoldingService) {
		TableView<InvestmentHolding> tableView = new TableView<>(investmentHoldingService.listInvestmentHoldings());

		tableView.addColumn("Conta proprietária", InvestmentHolding::getAccount);
		tableView.addColumn("Investimento", InvestmentHolding::getInvestment);
		tableView.addColumn("Taxa", InvestmentHolding::getFormattedTax);
		tableView.addColumn("Saldo", InvestmentHolding::getBalance);

		tableView.show();
	}

	public static void readjustAll(final InvestmentHoldingService investmentHoldingService) {
		investmentHoldingService.readjustAll();
		CommonViews.showMessage("Os rendimentos de todos os investimentos foram atualizados com sucesso!");
	}

	public void listPortfolio() {
		listPortfolio(investmentHoldingService);
	}

	public void listInvestmentHoldings() {
		listInvestmentHoldings(investmentHoldingService);
	}

	public void readjustAll() {
		readjustAll(investmentHoldingService);
	}

	public void listInvestmentsFromAccount() {
		TableView<InvestmentHolding> tableView = new TableView<>(
				investmentHoldingService.listInvestmentHoldingsByAccount(account));

		tableView.addColumn("Investimento", InvestmentHolding::getInvestment);
		tableView.addColumn("Taxa", InvestmentHolding::getFormattedTax);
		tableView.addColumn("Saldo", InvestmentHolding::getBalance);

		tableView.show();
	}

	public void invest() {
		Investment investment = null;
		do {
			List<Investment> portfolio = investmentHoldingService.listPortfolio();
			int investmentInt;
			do {
				CommonViews.showMessage("Selecione um investimento:");
				OptionsView optionsView = new OptionsView();
				portfolio.forEach(item -> optionsView.addOption(item.name() + " (" + item.getFormattedTax() + ")"));
				optionsView.show();
				investmentInt = scanner.nextInt();
			} while (investmentInt < 1 || investmentInt > portfolio.size());
			investment = portfolio.get(investmentInt - 1);
		} while (investment == null);

		Money ammount = null;
		try {
			do {
				try {
					CommonViews.askUser("Informe o valor a ser investido (acima de " + investment.minimunAmount()
							+ ", em centavos e sem vírgula):");
					long ammountLong = scanner.nextLong();

					if (ammountLong < investment.minimunAmount().valueInCents()) {
						throw new IllegalArgumentException(
								"O valor investido deve ser maior que o valor mínimo exigido");
					}

					ammount = new Money(ammountLong);

				} catch (IllegalArgumentException e) {
					CommonViews.showMessage(e.getMessage());
				}
			} while (ammount == null);

			investmentHoldingService.create(transactionService, account, investment, ammount);
			CommonViews.showMessage("Investimento realizado com sucesso!");

		} catch (IllegalArgumentException e) {
			CommonViews.showMessage(e.getMessage());
		}
	}

	public void redeem() {
		try {
			Money amount = investmentHoldingService.sumInvestmentHoldingsBalances(account);
			investmentHoldingService.redeem(transactionService, account);
			CommonViews.showMessage(amount + " resgatados com sucesso!");
		} catch (IllegalArgumentException e) {
			CommonViews.showMessage(e.getMessage());
		}
	}
}
