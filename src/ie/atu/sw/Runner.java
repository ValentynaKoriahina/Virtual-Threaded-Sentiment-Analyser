package ie.atu.sw;

/**
 * This class contains the entry point into the program
 */
public class Runner {

	/**
	 * Runs the program.
	 *
	 * @param args Command line arguments not used in this program.
	 * @throws Exception May occur during program execution, including those related
	 *                   to file handling, user input handling, or any other runtime
	 *                   exceptions that may be thrown by methods
	 */
	public static void main(String[] args) throws Exception {
		new Menu().start();
	}
}