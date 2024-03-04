package ie.atu.sw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class is designed for parallel processing of text files. Uses multithreading
 * to speed up data processing.
 */
public class FileParser {
	private Map<Integer, Double> map = new ConcurrentSkipListMap<>();
	private Collection<String> words = new ConcurrentLinkedDeque<>();

	/**
	 * Reads and processes the lexicon file, creating a map.
	 * 
	 * Overall running time - O(n), depends on the number of lines in the file.
	 *
	 * @param lexicon Path to the lexicon file.
	 * @return A map where the key is a hash of the word and the value is the
	 *         numeric value of the word (sentiment).
	 */
	public Map<Integer, Double> getMappingLexicon(String lexicon) {
		try {
			var pool = Executors.newVirtualThreadPerTaskExecutor();
			Files.lines(Paths.get(lexicon)).forEach(line -> pool.execute(() -> processLexicon(line)));
			pool.shutdown();
			boolean finished = pool.awaitTermination(60, TimeUnit.SECONDS);
			if (!finished) {
				System.out
						.println("There were difficulties terminating threads. Perhaps not all tasks were completed.");
				pool.shutdownNow();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.map;
	}

	/**
	 * Reads and processes a file of tweets, creating a collection of words.
	 * 
	 * Overall running time - O(n), depends on the number of lines in the file.
	 *
	 * @param tweets Path to the tweets file.
	 * @return A collection of words extracted from tweets.
	 */
	public Collection<String> getWordsList(String tweets) {
		try {
			var pool = Executors.newVirtualThreadPerTaskExecutor();
			Files.lines(Paths.get(tweets)).forEach(line -> pool.execute(() -> processTweets(line)));
			pool.shutdown();
			boolean finished = pool.awaitTermination(60, TimeUnit.SECONDS);
			if (!finished) {
				System.out
						.println("There were difficulties terminating threads. Perhaps not all tasks were completed.");
				pool.shutdownNow();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.words;
	}

	/**
	 * Processes a string from a lexicon file. Splits a string into elements, where
	 * the first element is interpreted as a word, and the second - as a numerical
	 * value associated with this word.
	 * 
	 * Perform operations of fixed complexity - O(1).
	 *
	 * @param line Line from the lexicon file.
	 */
	private void processLexicon(String line) {
		String[] items = line.split(",");
		try {
			map.put(items[0].hashCode(), Double.parseDouble(items[1]));
		} catch (NumberFormatException e) {
			// Ignoring elements with format issues
		}
	}

	/**
	 * Processes a string from a tweets file. Splits a string into words, strips
	 * them of unwanted characters and converts them to lowercase. Processed words
	 * are added to the `words` collection.
	 * 
	 * Perform operations of fixed complexity - O(1).
	 *
	 * @param line Line from the tweets file.
	 */
	private void processTweets(String line) {
		Arrays.stream(line.split("\\s+")).forEach(w -> words.add(w.trim().replaceAll("[^a-zA-Z]", "").toLowerCase()));
	}
}