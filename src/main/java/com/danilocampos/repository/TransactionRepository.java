package com.danilocampos.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.danilocampos.model.Actor;
import com.danilocampos.model.Transaction;

public class TransactionRepository {
	private final List<Transaction> transactions = new ArrayList<>();

	public void save(final Transaction newTransaction) {
		transactions.add(newTransaction);
	}

	public void saveAll(final List<Transaction> newTransactions) {
		transactions.addAll(newTransactions);
	}

	public List<Transaction> findAll() {
		return Collections.unmodifiableList(transactions);
	}

	public List<Transaction> findByActor(Actor actor) {
		return transactions.stream()
			.filter(t -> t.getOrigin().equals(actor) || t.getDestination().equals(actor))
				.collect(Collectors.toList());
	}
}
