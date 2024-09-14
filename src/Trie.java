import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Insert a word into the trie
    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
        }
        current.isEndOfWord = true;
    }

    // Search for a node that represents the end of a given prefix
    private TrieNode searchPrefix(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return null; // Prefix not found
            }
            current = current.children.get(c);
        }
        return current;
    }

    // Recursive function to collect all words from a given node
    private void collectWords(TrieNode node, String prefix, List<String> words) {
        if (node.isEndOfWord) {
            words.add(prefix);
        }

        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            collectWords(entry.getValue(), prefix + entry.getKey(), words);
        }
    }

    // Function to get autocomplete suggestions for a given prefix
    public List<String> autocomplete(String prefix) {
        List<String> result = new ArrayList<>();
        TrieNode node = searchPrefix(prefix);

        if (node != null) {
            collectWords(node, prefix, result);
        }

        return result;
    }
}
