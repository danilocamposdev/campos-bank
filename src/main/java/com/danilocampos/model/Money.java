package com.danilocampos.model;

public record Money (long valueInCents){

	public Money sum(final Money newValue) {
		return new Money(valueInCents + newValue.valueInCents());
	}

	public Money subtract(final Money newValue) {
		return new Money(valueInCents - newValue.valueInCents());
	}

	public static Money sum(final Money x, final Money y) {
		return x.sum(y);
	}

	public static Money subtract(final Money x, final Money y) {
		return x.subtract(y);
	}

	@Override
	public String toString() {
		String sign = valueInCents < 0 ? "-" : "";
		long absoluteValueInCents = Math.abs(valueInCents);
		long reais = absoluteValueInCents / 100;
		long cents = absoluteValueInCents % 100;
		return String.format("%sR$ %d,%02d", sign, reais, cents);
	}
}
