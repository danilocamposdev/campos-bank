package com.danilocampos.view;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TableView<T> {

	private class Column {
		private final String header;
		private final Function<T, Object> extractor;
		private final int width;

		public Column(final String header, final Function<T, Object> extractor) {
			this.header = header;
			this.extractor = extractor;
			this.width = calculateWidth(this);
		}
	}

	private final List<T> data;
	private final List<Column> columns = new ArrayList<>();

	public TableView(final List<T> data) {
		this.data = data;
	}

	private int calculateWidth(final Column column) {
		int maxWidth = column.header.length();

		for (T line : data) {
			int valueWidth = column.extractor.apply(line).toString().length();
			if (valueWidth > maxWidth)
				maxWidth = valueWidth;
		}

		int limit = 30;

		return maxWidth < limit ? maxWidth + 1 : limit;
	}

	public void addColumn(final String header, final Function<T, Object> extractor) {
		Column column = new Column(header, extractor);
		columns.add(column);
	}

	private void headerView() {
		if (columns.isEmpty()) {
			System.out.println("[Tabela sem colunas]");
			return;
		}

		for (int i = 0; i < columns.size(); i++) {
			if (i == 0)
				System.out.print("┏");

			StringBuilder line = new StringBuilder("━".repeat(columns.get(i).width));
			System.out.print(line);

			if (columns.size() > 1 && i < columns.size() - 1)
				System.out.print("┳");

			if (i == columns.size() - 1)
				System.out.print("┓\n");
		}
 
		for (int i = 0; i < columns.size(); i++) {

			int maxWidth = columns.get(i).width;
			StringBuilder headerString = new StringBuilder(" ".repeat(columns.get(i).width));

			String header = columns.get(i).header.toUpperCase();
			int headerLenght = header.length() > columns.get(i).width ? columns.get(i).width : header.length();

			String ajustedHeader = header.length() > maxWidth ? header.substring(0, maxWidth - 4) + "..." : header;

			headerString.replace(0, headerLenght, ajustedHeader);
			System.out.print("┃" + headerString);

			if (i == columns.size() - 1)
				System.out.print("┃\n");
		}
		

		for (int i = 0; i < columns.size(); i++) {
			if (i == 0)
				System.out.print("┡");

			int maxWidth = columns.get(i).width;
			StringBuilder line = new StringBuilder("━".repeat(maxWidth));
			System.out.print(line);

			if (columns.size() > 1 && i < columns.size() - 1)
				System.out.print("╇");

			if (i == columns.size() - 1)
				System.out.print("┩\n");
		}
	}

	private void bodyView() {
		int i = 0;
		do {
			boolean isLastLine = i == data.size() - 1 || data.size() == 0;
			
			for (int ii = 0; ii < columns.size(); ii++) {
				int maxWidth = columns.get(ii).width;
				StringBuilder valueString = new StringBuilder(" ".repeat(maxWidth));

				String value;
				
				if(data.size() > 0)
					value = columns.get(ii).extractor.apply(data.get(i)).toString();
				else
					value = " ".repeat(maxWidth);

				String ajustedValue = value.length() > maxWidth ? value.substring(0, maxWidth - 3) + "..." : value;

				valueString.replace(0, ajustedValue.length(), ajustedValue);
				System.out.print("│" + valueString);

				if (ii == columns.size() - 1)
					System.out.print("│\n");
			}

			if (isLastLine)
				System.out.print("└");
			else
				System.out.print("├");

			for (int ii = 0; ii < columns.size(); ii++) {
				int maxWidth = columns.get(ii).width;
				StringBuilder line = new StringBuilder("─".repeat(maxWidth));
				System.out.print(line);
				if (columns.size() > 1 && ii < columns.size() - 1)
					if (isLastLine)
						System.out.print("┴");
					else
						System.out.print("┼");
			}

			if (isLastLine)
				System.out.print("┘\n");
			else
				System.out.print("┤\n");

			i++;
		} while (i < data.size());

	}

	public void show() {
		System.out.print("\n");
		headerView();
		bodyView();
	}
}
