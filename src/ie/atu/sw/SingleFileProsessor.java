package ie.atu.sw;

import java.util.Collection;
import java.util.Map;

/**
 * Class implements {@link ie.atu.sw.WorkflowExecutator} to process single
 * files.
 */
public class SingleFileProsessor implements WorkflowExecutator {

	private String inFile;
	private String outFile;
	private String lexicon;

	/**
	 * Default constructor creates a new object by initializing the paths with the
	 * values "not set yet", indicating that they have not yet been set.
	 */
	public SingleFileProsessor() {
		String defaultValue = "not set yet";
		this.inFile = defaultValue;
		this.outFile = defaultValue;
		this.lexicon = defaultValue;
	}
	
	/**
	  * Creates a new object by initializing the paths with the given specific values.
	  * Used for testing.
	  *
	  * @param inFile Path to the input file.
	  * @param outFile Path to the output file.
	  * @param lexicon Path to the lexicon file.
	  */
	public SingleFileProsessor(String inFile, String outFile, String lexicon) {
		this.inFile = inFile;
		this.outFile = outFile;
		this.lexicon = lexicon;
	}

	/**
	 * inFile/outFile variable setter
	 *
	 * @param file      Path to the file.
	 * @param direction Specifies whether the path should be set to "Input" or
	 *                  "Output".
	 */
	public void setFile(String file, String direction) {
		boolean fileExists; // Pre-check if the specified path exists

		switch (direction) {
		case "Input" -> {
			fileExists = FileAndDirectoryProcessor.checkPath(file);
			if (fileExists) {
				this.inFile = FileAndDirectoryProcessor.getCanonicalPath(file);
			} else {
				System.out.println(
						"Important! The path you specified does not exist, check the data entered and try again.");
				System.out.println(file);
			}
		}
		case "Output" -> {
			fileExists = FileAndDirectoryProcessor.checkPath(file);
			if (fileExists) {
				this.outFile = FileAndDirectoryProcessor.getCanonicalPath(file);
				FileAndDirectoryProcessor.writeFile("", this.outFile); // Clearing the file
			} else {
				this.outFile = FileAndDirectoryProcessor.getCanonicalPath(file);
			}
		}
		default -> System.out.println("Invalid input.");
		}
	}

	/**
	 * Lexicon variable setter.
	 *
	 * @param file Path to the lexicon file.
	 */
	public void setLexicon(String file) {
		boolean fileExists = FileAndDirectoryProcessor.checkPath(file); // Pre-check if the specified path exists

		if (fileExists) {
			this.lexicon = FileAndDirectoryProcessor.getCanonicalPath(file);
		} else {
			System.out.println(
					"Important! The path you specified does not exist," + " check the data entered and try again.");
			System.out.println(file);
		}
	}

	/**
	 * Checks whether all necessary data has been installed to start the process.
	 *
	 * @param action Action to execute, the program is designed for admission only
	 *               "check all data set".
	 * @return Returns true if all data has been set, false otherwise.
	 */
	public boolean checkEnteredData(String action) {
		boolean allDataSetted = false;

		// Check whether all data required to start the process have been entered
		if (!(this.inFile.equals("not set yet")) && !(this.outFile.equals("not set yet"))
				&& !(this.lexicon.equals("not set yet"))) {
			allDataSetted = true;
		}

		// Processing a request to check the entered data
		if (action.equals("chek all data setted")) {
			if (allDataSetted == false) {
				System.out.print("\nAttention! Set up all the necessary data before starting");
				Messages.checkEnteredData(this.inFile, this.outFile, this.lexicon);
				return false;
			} else {
				// Printing Check-Entered-Data message
				Messages.checkEnteredData(this.inFile, this.outFile, this.lexicon);
				return true;
			}
		} else {
			// Printing Check-Entered-Data message
			Messages.checkEnteredData(this.inFile, this.outFile, this.lexicon);
		}

		return allDataSetted;
	}

	/**
	 * Basic analysis workflow. Manages the process of sentiment analysis on input
	 * data using a lexicon.
	 * 
	 * The overall time complexity will depend on the most complex operation in its
	 * implementation. Since file reading and sentiment analysis are performed
	 * linearly, the overall complexity is also O(n) without taking into account
	 * constant factors and terms.
	 *
	 * @return The result of the sentiment analysis.
	 */
	public double startProcess() {

		boolean allDataSetted = checkEnteredData("chek all data setted");
		Map<Integer, Double> mapLexicon = null;
		Collection<String> tweetWords = null;

		if (allDataSetted == false) {
			return 0.0;
		}
		
		// Get lexicon
		mapLexicon = new FileParser().getMappingLexicon(lexicon);
		
		// Get tweet words list
		try {
			tweetWords = new FileParser().getWordsList(inFile);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		double result = new SentimentAnalyser(mapLexicon, tweetWords).analyse();
		
		// Data for generating results
		String inFileName = FileAndDirectoryProcessor.getFileName(inFile);
		String lexiconFileName = FileAndDirectoryProcessor.getFileName(lexicon);

		String content = "Result of semantic analysis: \n" + "- file name: " + inFileName + "\n" + "- lexicon: "
				+ lexiconFileName + "\n" + "- result: " + result;

		new FileAndDirectoryProcessor();
		FileAndDirectoryProcessor.writeFile(content, outFile);
		
		System.out.println("\nThe file have been processed and saved, result stored in: "
				+ FileAndDirectoryProcessor.getCanonicalPath(outFile) + "\n");

		return result; // for testing
	}
}
