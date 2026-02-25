package com.danilocampos.service;

import java.util.List;

import com.danilocampos.model.Account;
import com.danilocampos.model.AccountNumber;
import com.danilocampos.model.AccountType;
import com.danilocampos.model.Agency;
import com.danilocampos.model.CPF;
import com.danilocampos.repository.ActorRepository;

public class ActorService {
	private final ActorRepository repository;

	public ActorService(final ActorRepository repository) {
		this.repository = repository;
	}

	public Account createAccount(
			final String name,
			final AccountType type,
			final CPF cpf) {
		if (repository.findByCPF(cpf).isPresent())
			throw new IllegalArgumentException("CPF já cadastrado");

		Account account = new Account(new Agency(), new AccountNumber(), name, type, cpf);
		repository.save(account);
		return account;
	}

	public List<Account> list() {
		return repository.findAllAccounts();
	}

	public List<Account> listOthers(final Account account) {
		List<Account> accounts = repository.findAllAccounts();
		accounts.remove(account);
		return accounts;
	}

	public Account findAccount(final Agency agency, final AccountNumber accountNumber) {
		Account account = repository.findAccount(agency, accountNumber).orElse(null);

		if (account == null)
			throw new IllegalArgumentException("Conta não encontrada");

		return account;
	}

	public Account findAccountByCPF(final CPF cpf) {

		Account account = repository.findByCPF(cpf).orElse(null);

		if (account == null)
			throw new IllegalArgumentException("Conta não encontrada");

		return account;

	}
}
