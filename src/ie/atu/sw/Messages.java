package ie.atu.sw;

/**
 * This class contains large messages output to the console. Created to relieve
 * the main code.
 */
public class Messages {

	/**
	 * Header for Class {@link ie.atu.sw.Menu}
	 * 
	 * <p>
	 * Prints program header output to the console
	 */
	public static void printHeader() {
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*             Virtual Threaded Sentiment Analyser          *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
	}

	/**
	 * Main Menu for Class {@link ie.atu.sw.Menu}
	 * 
	 * <p>
	 * Prints the main menu of the program
	 */
	public static void printMenu() {
		System.out.println("(1) Specify Input File or Directory");
		System.out.println("(2) Specify an Output File");
		System.out.println("(3) Configure Lexicons");
		System.out.println("(4) Execute, Analyse and Report");
		System.out.println("(0) Quit");

		System.out.print("Select Option [0-4]>");
		System.out.println();
	}

	/**
	 * Check-Entered-Data message for Class {@link ie.atu.sw.SingleFileProsessor}
	 * and {@link ie.atu.sw.MultiFileProsessor}
	 * 
	 * <p>
	 * Prints a message to validate entered paths
	 * 
	 * @param inFile  user-selected input path
	 * @param outFile user-selected output path
	 * @param lexicon user-selected lexicon
	 */
	public static void checkEnteredData(String inFile, String outFile, String lexicon) {
		System.out.println("\n===================================================================");
		System.out.println("Check seted data:");
		System.out.println("Input File Directory >> " + inFile);
		System.out.println("Output File Directory >> " + outFile);
		System.out.println("Lexicon >> " + lexicon);
		System.out.println("\n===================================================================\r\n");
	}

	/**
	 * Input-Path-Selection Menu for Class {@link ie.atu.sw.Menu}
	 * 
	 * <p>
	 * Prints options for selecting the input path
	 */
	public static void setInputPath() {
		System.out.println("\n===================================================================\r\n"
				+ "Selecting an input file or directory.\r\n"
				+ "===================================================================\r\n" + "Available options:\r\n"
				+ "enter your path\r\n" + "press Enter to launch the GUI for selecting file or directory");
		System.out.println("===================================================================\r\n");
		System.out.println(">>");

	}

	/**
	 * Selecting-An-Output-File message for Class {@link ie.atu.sw.Menu}
	 * 
	 * <p>
	 * Prints options for selecting the output path
	 */
	public static void setOutputPath() {
		System.out.println("\n===================================================================\r\n"
				+ "Selecting an output file.\r\n" + "Attention. The file you specified will be overwritten.\r\n"
				+ "===================================================================\r\n" + "Available options:\r\n"
				+ "press Enter to launch the GUI for selecting a file\r\n" + "enter your file path");
		System.out.println("===================================================================\r\n");
		System.out.println(">>");
	}

	/**
	 * Selecting-A-Lexicon message for Class {@link ie.atu.sw.Menu}
	 * 
	 * <p>
	 * Prints options for selecting the path to lexicon
	 */
	public static void setLexiconPath() {
		System.out.println("\n===================================================================\r\n"
				+ "Selecting a lexicon.\r\n" + "===================================================================\r\n"
				+ "Available options:\r\n" + "press Enter to launch the GUI for selecting a file\r\n"
				+ "enter your path");
		System.out.println("===================================================================\r\n");
		System.out.println(">>");
	}
}
