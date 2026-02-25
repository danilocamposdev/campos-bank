package com.danilocampos.model;

import java.util.UUID;

public abstract class Actor {
	private final UUID id;
	private final String name;

	public Actor(String name) {
		this.id = UUID.randomUUID();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public UUID getId() {
		return id;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
	    return obj.equals(id);
	}
	
}
