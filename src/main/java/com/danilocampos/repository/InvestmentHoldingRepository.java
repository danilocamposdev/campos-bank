package com.danilocampos.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.danilocampos.model.Account;
import com.danilocampos.model.InvestmentHolding;
import com.danilocampos.model.Money;

public class InvestmentHoldingRepository {

	List<InvestmentHolding> investmentsHoldings = new ArrayList<>();

	public void save(final InvestmentHolding investmentHolding) {
		investmentsHoldings.add(investmentHolding);
	}

	public List<InvestmentHolding> findAll() {
		return Collections.unmodifiableList(investmentsHoldings);
	}

	public List<InvestmentHolding> findByAccount(final Account account) {
		return investmentsHoldings.stream()
				.filter(item -> item.getAccount()
						.equals(account))
				.toList();
	}

	public void readjustAll() {
		investmentsHoldings.forEach(investmentsHolding -> investmentsHolding.readjustBalance());
	}

	public Money sumAllBalancesByAccount(final Account account) {
		long totalInCents = findByAccount(account).stream()
			.mapToLong(investmentsHolding -> investmentsHolding.getBalance().valueInCents())
				.sum();

		return new Money(totalInCents);
	}

	public void clearByAccount(final Account account) {
		investmentsHoldings.removeAll(findByAccount(account));
	}
}
