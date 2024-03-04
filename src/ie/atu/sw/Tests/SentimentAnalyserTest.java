package ie.atu.sw.Tests;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import ie.atu.sw.*;

class SentimentAnalyserTest {
	static SentimentAnalyser analyser;
	static Map<Integer, Double> mapLexicon;
	static Collection<String> tweetWords;
	double actualResult;


	/* A simple test to check the functionality of the Sentiment Analyser.
	 * Since this version of the program uses hush code as a key in map, the test results differ from the example in the task.
	 */
	@ParameterizedTest
	@CsvSource({ "./lexicons/afinn.txt, 3.0", "./lexicons/bingliu.txt, 4.0", "./lexicons/mpqa.txt, 1.0", "./lexicons/textblob.txt, 1.4", "./lexicons/vader.txt, 0.3"})
	void analyse(String lexicon, double expectedResult) throws IOException, InterruptedException {
		System.out.println(lexicon);
		mapLexicon = new FileParser().getMappingLexicon(lexicon);
	    tweetWords = new FileParser().getWordsList("./Napoleon.txt");
	    analyser = new SentimentAnalyser(mapLexicon, tweetWords);
    
	    actualResult = analyser.analyse();
   
	    System.out.println("Espected result: " + expectedResult);
	    System.out.println("Actual result: " + actualResult);
	    System.out.println();
	    assertEquals(expectedResult, actualResult);

	}
}
