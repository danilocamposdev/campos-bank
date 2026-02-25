package com.danilocampos.model;

public record CPF(String value) {

	public CPF {
		if (value == null || value.isEmpty())
			throw new IllegalArgumentException("O CPF não pode ser vazio");

		if(!value.matches("\\d+"))
		   throw new IllegalArgumentException("O CPF deve conter apenas números");
		
		if (value.length() != 11)
			throw new IllegalArgumentException("O CPF deve conter 11 números");
	}

	@Override
	public String toString() {
		return String.format("%s.%s.%s-%s", value.substring(0, 3), value.substring(3, 6), value.substring(6, 9),
				value.substring(9, 11));
	}

	@Override
	public boolean equals(Object obj) {
	    return obj.equals(value);
	}
}
