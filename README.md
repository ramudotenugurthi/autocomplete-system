***Trie-Based Autocomplete with Parallel Search***

This Java project implements an efficient autocomplete system using a Trie (Prefix Tree) data structure. The system is capable of handling 1 lakh random word entries and performing 10,000 search operations in parallel using 10 threads. The multithreading aspect significantly improves performance when searching large datasets.

***Features***<br>
**Trie Data Structure**: Efficient for prefix-based searches and word insertions.
Random Word Generation: Generates random words for testing purposes.<br>
**Multithreading**: Uses Java's ExecutorService to perform searches in parallel across multiple threads.<br>
**Scalability**: Handles large datasets with high performance due to parallel processing.<br>

***Getting Started***<br>
Prerequisites<br>
Java 8 or higher<br>
A working Java development environment (e.g., IntelliJ IDEA, Eclipse, or any other IDE)

****Running the Program****<br>
javac AutoCompleteService.java<br>
java AutoCompleteService<br>

****How It Works****<br>
**Trie Insertion**: A list of 1 lakh random words is generated and inserted into the Trie, where each character of a word corresponds to a node. This ensures efficient storage and retrieval of words based on prefixes.<br>
**Parallel Search**: A set of 10,000 random search prefixes is generated. Using ExecutorService, the program divides the search workload into 10 threads, each responsible for searching 1,000 prefixes in the Trie.<br>
**Thread Execution**: Each thread runs in parallel, collects the results, and the program aggregates the search results from all threads.
