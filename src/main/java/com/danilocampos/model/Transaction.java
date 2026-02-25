package com.danilocampos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {
	private final UUID id;
	private final LocalDateTime createdAt;
	private final Actor origin;
	private final Actor destination;
	private final TransactionType type;
	private final Money amount;

	public Transaction(
			Actor origin,
			Actor destination,
			TransactionType type,
			Money amount
	) {
		this.id = UUID.randomUUID();
		this.origin = origin;
		this.destination = destination;
		this.type = type;
		this.amount = amount;
		this.createdAt = LocalDateTime.now();

	}

	public UUID getId() {
		return id;
	}

	public Actor getOrigin() {
		return this.origin;
	}

	public Actor getDestination() {
		return this.destination;
	}

	public TransactionType getType() {
		return type;
	}

	public Money getAmount() {
		return amount;
	}

	public String getFormatedCreatedAt() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		return createdAt.format(formatter);
	}
	
}
