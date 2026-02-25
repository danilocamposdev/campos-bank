package com.danilocampos.view;

public class CommonViews {

	public static void input() {
		System.out.print(" ↪ ");
	}

	public static void title() {

		String logo = """
				  /$$$$$$                                                              /$$$$$$$                      /$$
				 /$$__  $$                                                            | $$__  $$                    | $$
				| $$  \\__/  /$$$$$$  /$$$$$$/$$$$   /$$$$$$   /$$$$$$   /$$$$$$$      | $$  \\ $$  /$$$$$$  /$$$$$$$ | $$   /$$
				| $$       |____  $$| $$_  $$_  $$ /$$__  $$ /$$__  $$ /$$_____/      | $$$$$$$  |____  $$| $$__  $$| $$  /$$/
				| $$        /$$$$$$$| $$ \\ $$ \\ $$| $$  \\ $$| $$  \\ $$|  $$$$$$       | $$__  $$  /$$$$$$$| $$  \\ $$| $$$$$$/
				| $$    $$ /$$__  $$| $$ | $$ | $$| $$  | $$| $$  | $$ \\____  $$      | $$  \\ $$ /$$__  $$| $$  | $$| $$_  $$
				|  $$$$$$/|  $$$$$$$| $$ | $$ | $$| $$$$$$$/|  $$$$$$/ /$$$$$$$/      | $$$$$$$/|  $$$$$$$| $$  | $$| $$ \\  $$
				 \\______/  \\_______/|__/ |__/ |__/| $$____/  \\______/ |_______/       |_______/  \\_______/|__/  |__/|__/  \\__/
				                                  | $$
				                                  | $$
				                                  |__/
				    """;
		System.out.print("\n\n" + logo + "\n");
	}

	public static void askUser(String question) {
		System.out.print("\n" + question + "\n");
		input();
	}

	public static void showMessage(String message) {
		System.out.print("\n" + message + "\n");
	}

	public static void showLine() {
		String line = "\n" + "═".repeat(80) + "\n";
		System.out.print(line);
	}
	
}


