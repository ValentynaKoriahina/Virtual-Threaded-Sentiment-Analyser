# Virtual Threaded Sentiment Analyser

## Author: Valentyna Koriahina
### Version: Java 19

#### Description
This is a console-based Java application designed for sentiment analysis of tweets using virtual threads. The program compares individual words from tweets to a lexicon that have specific sentiment values, total score is calculated using Score and Sum.

#### To Run
From the console: `java -cp ./oop.jar ie.atu.sw.Runner`. Then navigate the console options. If some meaningful inputs were not specified or the source was determined to not exist, you will receive a corresponding notification.

#### Features:
1. **Specify INPUT FILE/DIRECTORY path** via GUI or manually (including extension). For directories, all files inside will be processed in multi-threaded mode.
2. **Specify OUTPUT FILE path** via GUI or manually. In case of processing multiple files, the results for each source file will be added to the one output file. The program creates a file if it does not exist and overwrites an existing file.
3. **Specify LEXICON path** via GUI or manually. The lexicon format should list words and their sentiment values, e.g., `accessible,1` or `acclaim,1.5`.
4. **Initiate ANALYSE process**. Uses multithreading to prepare the lexicon, source file, and for analysis itself.

#### Note
The application has been successfully tested for use with the lexicons Afinn, Bingliu, Vader; the use of other lexicons may lead to inaccuracies in the results, which, however, do not qualify as significant.

#### Testing
- **SentimentAnalyserTest**
  - **Purpose:** Testing the functionality of the SentimentAnalyser.
  - **Method:** Using different lexicons to analyze text.
  - **Description:** The consistency between expected and actual results of the analysis is checked.

- **SingleFileProcessorTest**
  - **Purpose:** Checking the stability of work when processing single files.
  - **Method:** Multiple runs of the process on the same input data.
  - **Description:** Perform a series of tests to confirm that the results of sentiment analysis are consistent when the same lexicons and data are used repeatedly.
