package com.danilocampos.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.danilocampos.model.Account;
import com.danilocampos.model.AccountNumber;
import com.danilocampos.model.Actor;
import com.danilocampos.model.Agency;
import com.danilocampos.model.CPF;

public class ActorRepository {
	private final List<Actor> actors = new ArrayList<>();

	public void save(final Actor actor) {
		actors.add(actor);
	}

	public Optional<Account> findByCPF(final CPF cpf) {
		return actors.stream()
				.filter(actor -> actor instanceof Account)
				.map(actor -> (Account) actor)
				.filter(a -> a.getCpf().equals(cpf))
				.findFirst();
	}

	public List<Actor> findByName(final String name) {
		return actors.stream()
				.filter(actor -> actor.getName().equals(name))
				.collect(Collectors.toList());
	}

	public Optional<Account> findAccount(final Agency agency, final AccountNumber accountNumber) {
		return actors.stream()
				.filter(actor -> actor instanceof Account)
				.map(actor -> (Account) actor)
				.filter(a -> a.getAgency().equals(agency) && a.getAccountNumber().equals(accountNumber))
				.findFirst();
	}

	public List<Actor> findAll() {
		return Collections.unmodifiableList(actors);
	}

	public List<Account> findAllAccounts() {
		return actors.stream()
				.filter(actor -> actor instanceof Account)
				.map(actor -> (Account) actor)
				.collect(Collectors.toList());
	}
}
