package ie.atu.sw;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class is designed to analyze sentiment in texts (in this version of the
 * program in tweets).
 */
public class SentimentAnalyser {
	private Map<Integer, Double> mapLexicon;
	private Collection<String> tweetWords;
	private double scoreFromTotal;

	/**
	 * Creates a new object by initializing a lexicon map and a collection of words.
	 * 
	 * @param mapLexicon Map mapping words to their sentimental meanings.
	 * @param tweetWords A collection of words extracted from tweets for analysis.
	 */
	public SentimentAnalyser(Map<Integer, Double> mapLexicon, Collection<String> tweetWords) {
		this.mapLexicon = mapLexicon;
		this.tweetWords = tweetWords;
	}

	/**
	 * Analyzes sentiment based on provided words and vocabulary.
	 *
	 * Time complexity is O(n), tweetWords iteration.
	 * 
	 * @return The rounded value of the overall sentiment counter.
	 */
	public double analyse() {
		try {
			var pool = Executors.newVirtualThreadPerTaskExecutor();
			tweetWords.forEach(word -> pool.execute(() -> getScoreFromTotal(word)));
			pool.shutdown();
			boolean finished = pool.awaitTermination(60, TimeUnit.SECONDS);
			if (!finished) {
				System.out
						.println("There were difficulties terminating threads. Perhaps not all tasks were completed.");
				pool.shutdownNow();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return Math.round(scoreFromTotal * 10.0) / 10.0; // Round scoreFromTotal to one decimal place
	}

	/**
	 * Calculating the overall sentiment counter. Adds the value from the lexicon
	 * map to the total counter if the word is present in it.
	 * 
	 * Checking the presence and retrieving complexity - O(log n) for
	 * ConcurrentSkipListMap.
	 *
	 * @param word The word to analyze.
	 */
	private synchronized void getScoreFromTotal(String word) {
		if (mapLexicon.containsKey(word.hashCode())) {
			scoreFromTotal += (mapLexicon.get(word.hashCode()));
		}
	}
}