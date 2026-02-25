package com.danilocampos.service;

import java.util.List;

import com.danilocampos.model.Account;
import com.danilocampos.model.Investment;
import com.danilocampos.model.InvestmentHolding;
import com.danilocampos.model.Money;
import com.danilocampos.repository.InvestmentHoldingRepository;
import com.danilocampos.repository.InvestmentRepository;

public class InvestmentHoldingService {

	private final InvestmentHoldingRepository repository;
	private final InvestmentRepository portfolio;

	public InvestmentHoldingService(final InvestmentHoldingRepository repository,
			final InvestmentRepository portfolio) {
		this.repository = repository;
		this.portfolio = portfolio;
	}

	public List<Investment> listPortfolio() {
		return portfolio.findAll();
	}

	public List<InvestmentHolding> listInvestmentHoldings() {
		return repository.findAll();
	}

	public List<InvestmentHolding> listInvestmentHoldingsByAccount(final Account account) {
		return repository.findByAccount(account);
	}

	public void create(
			final TransactionService transactionService,
			final Account account,
			final Investment investment,
			Money initialInvestment) {

		if (initialInvestment.valueInCents() < investment.minimunAmount().valueInCents())
			throw new IllegalArgumentException(
					"O valor mínimo para o investimento é de " + investment.minimunAmount());

		transactionService.executeInvestment(account, initialInvestment);

		InvestmentHolding investmentHolding = new InvestmentHolding(account, investment, initialInvestment);

		repository.save(investmentHolding);
	}

	public Money sumInvestmentHoldingsBalances(final Account account) {
		return repository.sumAllBalancesByAccount(account);
	}

	public void redeem(final TransactionService transactionService, final Account account) {

		Money amount = repository.sumAllBalancesByAccount(account);
		repository.clearByAccount(account);
		transactionService.redeemInvestment(account, amount);
	}

	public void readjustAll() {
		repository.readjustAll();
	}
}
