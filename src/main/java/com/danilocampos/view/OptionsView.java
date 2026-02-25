package com.danilocampos.view;

import java.util.ArrayList;
import java.util.List;

public class OptionsView {

	private final List<String> options = new ArrayList<>();

	public void addOption(String option) {
		options.add(option);
	}

	public void show() {

		System.out.print("\nSelecione uma opção:\n\n");
		String line = "━".repeat(4);

		int maxSize = options.stream()
				.map(String::length)
				.max(Integer::compare)
				.orElse(0);

		for (int i = 1; i <= options.size(); i++) {

			StringBuilder numberField = new StringBuilder();

			System.out.print("┏");

			System.out.print(line);

			System.out.print("┱");

			System.out.print("─".repeat(maxSize + 2) + "┐\n");

			numberField.append(i);

			if (i < 10)
				numberField.append(" ");

			System.out.print("┃ " + numberField + " ┃");

			System.out.print(" " + options.get(i - 1) + " ".repeat(maxSize - options.get(i - 1).length()) + " │\n");

			System.out.print("┗");

			System.out.print(line);

			System.out.print("┹");

			System.out.print("─".repeat(maxSize + 2) + "┘\n");
		}

		System.out.print("\n");

		CommonViews.input();
	}
}
