package com.danilocampos;

import java.util.Scanner;

import com.danilocampos.controller.AccountController;
import com.danilocampos.controller.InvestmentController;
import com.danilocampos.controller.TransactionController;
import com.danilocampos.model.Investment;
import com.danilocampos.model.Money;
import com.danilocampos.repository.ActorRepository;
import com.danilocampos.repository.InvestmentHoldingRepository;
import com.danilocampos.repository.InvestmentRepository;
import com.danilocampos.repository.TransactionRepository;
import com.danilocampos.service.ActorService;
import com.danilocampos.service.InvestmentHoldingService;
import com.danilocampos.service.TransactionService;
import com.danilocampos.view.CommonViews;
import com.danilocampos.view.OptionsView;

public class App {
	private static ActorService actorService;
	private static TransactionService transactionService;
	private static InvestmentHoldingService investmentService;
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		ActorRepository actorRepository = new ActorRepository();
		TransactionRepository transactionRepository = new TransactionRepository();
		InvestmentHoldingRepository investmentHoldingRepository = new InvestmentHoldingRepository();
		InvestmentRepository portfolio = new InvestmentRepository();


		actorService = new ActorService(actorRepository);
		transactionService = new TransactionService(
				transactionRepository);
		investmentService = new InvestmentHoldingService(investmentHoldingRepository, portfolio);

		AccountController accountController = new AccountController(actorService, transactionService, investmentService, scanner);

		portfolio.save(new Investment("CDB", 1, new Money(10000)));
		portfolio.save(new Investment("Tesouro Direto", 1, new Money(3000)));
		portfolio.save(new Investment("Fundo Imobiliário", 2, new Money(15000)));
		portfolio.save(new Investment("Fundo de Ações Dividendos", 3, new Money(10000)));

		CommonViews.title();

		OptionsView optionsView = new OptionsView();

		optionsView.addOption("Criar uma conta");
		optionsView.addOption("Acessar conta");
		optionsView.addOption("Listar contas");
		optionsView.addOption("Listar transações");
		optionsView.addOption("Listar investimentos disponíveis");
		optionsView.addOption("Listar investimentos aplicados");
		optionsView.addOption("Atualizar rendimentos dos investimentos");
		optionsView.addOption("Sair");

		boolean exit = false;
		do {
			CommonViews.showLine();
			optionsView.show();
			
			int option = scanner.nextInt();
			scanner.nextLine();
			switch (option) {
				case 1 -> accountController.createAccount();
				case 2 -> accountController.enterAccount();
				case 3 -> accountController.listAccounts();
				case 4 -> TransactionController.listTransactions(transactionService);
				case 5 -> InvestmentController.listPortfolio(investmentService);
				case 6 -> InvestmentController.listInvestmentHoldings(investmentService);
				case 7 -> InvestmentController.readjustAll(investmentService);
				case 8 -> {
					CommonViews.showMessage("Até a próxima!\n");
					exit = true;
				}
				default -> CommonViews.showMessage("Opção invalida!");
			}
		} while (exit == false);

	}
}
