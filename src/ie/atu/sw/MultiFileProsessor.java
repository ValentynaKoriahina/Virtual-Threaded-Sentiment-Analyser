package ie.atu.sw;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class implements {@link ie.atu.sw.WorkflowExecutator} to process multiple
 * files.
 */
public class MultiFileProsessor implements WorkflowExecutator {

	private String inFile;
	private String outFile;
	private String lexicon;

	/**
	 * Default constructor creates a new object by initializing the paths with the
	 * values "not set yet", indicating that they have not yet been set.
	 */
	public MultiFileProsessor() {
		String defaultValue = "not set yet";
		this.inFile = defaultValue;
		this.outFile = defaultValue;
		this.lexicon = defaultValue;
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
	 * Lexicon variable setter
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
	 * Reading files O(n). Multi-threaded file processing O(n). Implementation of
	 * startMainProcess O(n). Overall complexity is also O(n) without taking into
	 * account constant factors and terms.
	 *
	 * @return The result of the sentiment analysis.
	 */
	public double startProcess() {
		boolean allDataSetted = checkEnteredData("chek all data setted");

		if (allDataSetted == false) {
			return 0.0;
		}

		File target = new File(inFile);
		File[] files = target.listFiles();

		ExecutorService pool = Executors.newFixedThreadPool(10);
		// Initiate iteration with all file and directory, nested directories are
		// ignored
		for (File file : files) {
			if (file.isFile()) {
				pool.submit(() -> startMainProcess(file.getAbsolutePath()));
			}
		}
		pool.shutdown();
		try {
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				System.out
						.println("There were difficulties terminating threads. Perhaps not all tasks were completed.");
				pool.shutdownNow();
			}
		} catch (InterruptedException e) {
			System.out.println("There were difficulties terminating threads. Perhaps not all tasks were completed.");
			pool.shutdownNow();
		}

		System.out.println("\nFiles have been processed and saved, result stored in: "
				+ FileAndDirectoryProcessor.getCanonicalPath(outFile) + "\n");

		return 0.0; // Return type is not used to test a multiple files version of a class
	}

	/**
	 * Runs sentiment analysis on a separate file. Results are written to the output
	 * file.
	 * 
	 * Since file reading and sentiment analysis are performed linearly, the overall
	 * complexity is also O(n) without taking into account constant factors and
	 * terms.
	 *
	 * @param file Path to the file to be processed.
	 */
	private void startMainProcess(String file) {
		Map<Integer, Double> mapLexicon = null;
		Collection<String> tweetWords = null;

		// Get lexicon
		mapLexicon = new FileParser().getMappingLexicon(lexicon);

		// Get tweet words list
		try {
			tweetWords = new FileParser().getWordsList(file);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		double result = new SentimentAnalyser(mapLexicon, tweetWords).analyse();

		// Data for generating results
		String inFileName = FileAndDirectoryProcessor.getFileName(file);
		String lexiconFileName = FileAndDirectoryProcessor.getFileName(lexicon);

		String content = "Result of semantic analysis: \n" + "- file name: " + inFileName + "\n" + "- lexicon: "
				+ lexiconFileName + "\n" + "- result: " + result;

		new FileAndDirectoryProcessor().appendFile(content, outFile);
	}
}
