import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Main class to handle word insertion, search, and multithreading
public class AutoCompleteService {
    private static final int TOTAL_WORDS = 100_000;
    private static final int TOTAL_SEARCHES = 10_000;
    private static final int THREAD_COUNT = 10;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Initialize Trie and ExecutorService for parallel execution
        Trie trie = new Trie();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        // Generate random words and insert into the Trie
        List<String> wordList = generateRandomWords(TOTAL_WORDS, 100);
        for (String word : wordList) {
            trie.insert(word);
        }
        System.out.println("Inserted " + TOTAL_WORDS + " words into the Trie.");

        // Generate random prefixes for search
        List<String> searchPrefixes = generateRandomWords(TOTAL_SEARCHES,7);
        Date startDate = new Date();
        // Submit search tasks to the thread pool
        List<Future<List<String>>> futures = new ArrayList<>();
        int searchPerThread = TOTAL_SEARCHES / THREAD_COUNT;

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int start = i * searchPerThread;
            final int end = (i + 1) * searchPerThread;

            futures.add(executor.submit(() -> {
                List<String> results = new ArrayList<>();
                for (int j = start; j < end; j++) {
                    List<String> foundWords = trie.autocomplete(searchPrefixes.get(j));
                    if(!foundWords.isEmpty()) {
                        System.out.println(searchPrefixes.get(j) + " found in " + foundWords);
                    }
                    results.addAll(foundWords);
                }
                return results;
            }));
        }

        // Collect and display the search results from all threads
        int totalResults = 0;
        for (Future<List<String>> future : futures) {
            List<String> result = future.get(); // Wait for each thread to finish
            totalResults += result.size();
        }
        Date end = new Date();
        System.out.println("Total words found: " + totalResults+ " in ms "+(end.getTime()-startDate.getTime()));

        // Shutdown the executor service
        executor.shutdown();
    }

    // Helper function to generate random words
    private static List<String> generateRandomWords(int count, int maxLength) {
        List<String> words = new ArrayList<>(count);
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            words.add(generateRandomWord(random, 3 + random.nextInt(maxLength))); // Word length between 3 and 7
        }
        return words;
    }

    // Helper function to generate a random word of given length
    private static String generateRandomWord(Random random, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + random.nextInt(26))); // Generate random lowercase letter
        }
        return sb.toString();
    }
}