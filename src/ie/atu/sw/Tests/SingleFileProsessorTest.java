package ie.atu.sw.Tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ie.atu.sw.SingleFileProsessor;

class SingleFileProsessorTest {
	static SingleFileProsessor workflow;
	static Map<Integer, Double> mapLexicon;
	static Collection<String> tweetWords;
	double actualResult;
	
	static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of("./100-twitter-users/Anime81.txt", "./result-1", "./lexicons/afinn.txt"),
                Arguments.of("./100-twitter-users/Anime81.txt", "./result-2", "./lexicons/bingliu.txt"),
                Arguments.of("./100-twitter-users/Anime81.txt", "./result-3", "./lexicons/mpqa.txt"),
                Arguments.of("./100-twitter-users/Anime81.txt", "./result-4", "./lexicons/textblob.txt"),
                Arguments.of("./100-twitter-users/Anime81.txt", "./result-5", "./lexicons/vader.txt")
        );
    }
	
    // A simple test for checking the accuracy of code results in different lexicons.
    @ParameterizedTest
    @MethodSource("parameters")
    void testAllResultsAreEqual(String inputFile, String outputFile, String lexiconFile) throws IOException, InterruptedException {
        ArrayList<Double> results = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            workflow = new SingleFileProsessor(inputFile, outputFile, lexiconFile);
            double actualResult = workflow.startProcess();
            results.add(actualResult);
        }

        boolean allEqual = true;
        double firstResult = results.get(0);

        for (int i = 1; i < results.size(); i++) {
            double currentResult = results.get(i);
            if (Math.abs(currentResult - firstResult) > 1e-6) {
                allEqual = false;
                break;
            }
        }

        assertTrue(allEqual, "All elements in the ArrayList should be equal");
    }
}


