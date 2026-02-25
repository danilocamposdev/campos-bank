package com.danilocampos.model;

public record Investment(
						 String name,
						 int tax,
						 Money minimunAmount
) {
	public Investment {
		if (tax <= 0)
			throw new IllegalArgumentException("A taxa deve ser um valor inteiro maior que 0");
	}

	public String getFormattedTax() {
		return tax + "%";
	}

	@Override
	public String toString() {
	    return name;
	}
}
