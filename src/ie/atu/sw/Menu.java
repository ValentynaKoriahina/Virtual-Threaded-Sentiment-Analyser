package ie.atu.sw;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class contains menu and basic elements for user control of the program.
 */
public class Menu {
	private boolean keepRunning = true;
	private Scanner scanner;
	private WorkflowExecutator workflow;

	private String inFile;
	private String outFile;
	private String lexicon;

	/**
	 * Creates a new object by initializing a new scanner for reading user input.
	 */
	public Menu() {
		scanner = new Scanner(System.in);
	}

	/**
	 * Launches menus and processes user input. Provides various options such as
	 * selecting paths and running procedures.
	 *
	 * @throws IOException          Error during input or output operations
	 * @throws InterruptedException If the thread is interrupted during execution
	 */
	public void start() throws IOException, InterruptedException {
		// Display Header for app
		Messages.printHeader();

		while (keepRunning) {
			// Display Main Menu
			Messages.printMenu();

			int choice = -1;

			// Accepting user input
			try {
				choice = Integer.parseInt(scanner.next());
			} catch (NumberFormatException e) {
				// pass
			}

			switch (choice) {
			case 1 -> pathInput("Input");
			case 2 -> pathInput("Output");
			case 3 -> setLexicon();
			case 4 -> execute();
			case 0 -> keepRunning = false;
			default -> System.out.println("\nPlease input only numbers 0 - 4\n");
			}

			// Added as a stopping point to make the output easier for the user to read
			if (keepRunning != false && choice != -1) {
				System.out.println("\nPress ENTER to continue");

				String next = "_";
				while (!next.equals("")) {
					next = scanner.nextLine();
				}
			} else if (keepRunning == false) {
				System.out.println("\nGood bye");
			}
		}
	}

	/**
	 * Selecting the path to an in- or out- file or directory
	 *
	 * @param direction The direction of the path selection ("Input" or "Output").
	 * @throws IOException Error during input or output operations
	 */
	private void pathInput(String direction) throws IOException {
		// Clears the input buffer from the newline character
		scanner.nextLine();

		String choice = null;

		if (direction.equals("Input")) {
			// Display Input-Path-Selection Menu
			Messages.setInputPath();

			choice = scanner.nextLine();

			// Opening a graphic window in case of Enter was inputed
			if (choice == "") {
				this.inFile = GUIPathProcessor.openFileOrDirectory();
			} else {
				// Handling the case when the path was entered textually
				this.inFile = choice;
			}
		}

		// This logic is similar to the previous part of the code
		if (direction.equals("Output")) {
			// Display Output-Path-Selection Menu
			Messages.setOutputPath();

			choice = scanner.nextLine();

			// Opening a graphic window in case of Enter was inputed
			if (choice == "") {
				this.outFile = GUIPathProcessor.openFile();
			} else {
				// Handling the case when the path was entered textually
				this.outFile = choice;
			}
		}
	}

	/**
	 * Allows user to select a lexicon file through the GUI or enter the path
	 * manually.
	 */
	private void setLexicon() {
		// Clears the input buffer from the newline character
		scanner.nextLine();

		String choice = null;

		// Display Output-Path-Selection Menu
		Messages.setLexiconPath();

		choice = scanner.nextLine();
		System.out.println(choice);

		// Opening a graphic window in case of Enter was inputed
		if (choice == "") {
			this.lexicon = GUIPathProcessor.openFile();
		} else {
			// Handling the case when the path was entered textually
			this.lexicon = choice;
		}
	}

	/**
	 * Starts execution of the main data processing.
	 *
	 * @throws IOException Error during input or output operations.
	 * @throws InterruptedException If the thread is interrupted during execution.
	 */
	private void execute() throws IOException, InterruptedException {
		File target = new File(this.inFile);
		// Check if the specified path is a directory
		if (target.isDirectory()) {
			workflow = new MultiFileProsessor();
			workflow.setFile(this.inFile, "Input");
			workflow.setFile(this.outFile, "Output");
			workflow.setLexicon(this.lexicon);
			workflow.startProcess();
		} else {
			workflow = new SingleFileProsessor();
			workflow.setFile(this.inFile, "Input");
			workflow.setFile(this.outFile, "Output");
			workflow.setLexicon(this.lexicon);
			workflow.startProcess();
		}
	}
}
