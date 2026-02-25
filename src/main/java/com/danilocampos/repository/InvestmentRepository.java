package com.danilocampos.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.danilocampos.model.Investment;

public class InvestmentRepository {
	private final List<Investment> investments = new ArrayList<>();

	public void save(final Investment newInvestment) {
		investments.add(newInvestment);
	}

	public List<Investment> findAll() {
		return Collections.unmodifiableList(investments);
	}
}
