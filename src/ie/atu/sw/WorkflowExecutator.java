package ie.atu.sw;

/**
 * Interface defines the main methods for classes,
 * implementing the data processing workflow.
 * 
 * @author Valentyna Koriahina
 * @version 1.0
 */
public interface WorkflowExecutator {

	/**
     * inFile/outFile variable setter
     *
     * @param file Path to the file.
     * @param direction Specifies whether the path should be set to "Input" or "Output".
     */
	public void setFile(String file, String direction);

	/**
     * Lexicon variable setter
     *
     * @param file Path to the lexicon file.
     */
	public void setLexicon(String file);

	/**
     * Checks whether all necessary data has been installed to start the process.
     *
     * @param action Action to execute, the program is designed for admission only "check all data set".
     * @return Returns true if all data has been set, false otherwise.
     */
	public boolean checkEnteredData(String action);

	/**
     * Basic analysis workflow.
     * Manages the process of sentiment analysis on input data using a lexicon.
     *
     * @return The result of the sentiment analysis.
     */
	public double startProcess();
}
