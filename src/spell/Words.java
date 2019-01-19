package spell;

import java.util.Arrays;
import java.util.Objects;

public class Words implements ITrie {
    private int numNodes = 1; // includes the root node
    private int numWords = 0;
    private WordNode root = new WordNode();

    public void add(String word) {
        WordNode currentNode = this.root;
        char currentLetter;
        int index;

        for (int i = 0; i < word.length(); i++) {
            currentLetter = word.charAt(i);
            index = currentLetter - 'a';
            if (!(currentNode.hasChild(currentLetter))) {
                currentNode.nodes[index] = new WordNode();
                this.numNodes++;
            }
            currentNode = currentNode.nodes[index];
        }
        // currentNode.setValue(word);
        if (currentNode.getValue() == 0) {
            this.numWords++;
        }
        currentNode.incrementCount();
    }

    public WordNode find(String word) {
        WordNode currentNode = this.root;
        char currentLetter;
        int index;

        for (int i = 0; i < word.length(); i++) {
            currentLetter = word.charAt(i);
            index = currentLetter - 'a';
            if (currentNode.hasChild(currentLetter)) {
                currentNode = currentNode.nodes[index];
            }
            else {
                // Return a null
                return null;
            }
        }
        return currentNode;
    }

    public int getWordCount() {
        return this.numWords;
    }

    public int getNodeCount() {
        return this.numNodes;
    }

    @Override
    public String toString() {
        return this.root.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Words words = (Words) o;
        return numNodes == words.numNodes &&
                numWords == words.numWords &&
                root.equals(words.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numNodes, numWords, root);
    }
}

class WordNode implements ITrie.INode {
    public WordNode[] nodes;
    private int count;
    static final int ALPHABET_SIZE = 26;

    // Default Constructor
    public WordNode() {
        nodes = new WordNode[ALPHABET_SIZE];
        count = 0;
    }

    public int getValue() {
        return this.count;
    }

    public void incrementCount() {
        this.count++;
    }

    public boolean hasChild(Character letter) {
        int index = letter - 'a';
        if (this.nodes[index] != null) {
            return true;
        }
        return false;
    }

    public boolean isWord() {
        return (this.count > 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordNode wordNode = (WordNode) o;
        return count == wordNode.count &&
                Arrays.equals(nodes, wordNode.nodes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(count);
        result = 31 * result + Arrays.hashCode(nodes);
        return result;
    }

    public String toString() {
        int level = 0;
        char letter;
        StringBuilder builder = new StringBuilder();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            letter = (char) (i + 'a');
            if (this.nodes[i] != null) {
                builder.append(letter);
                if (this.nodes[i].isWord()) {
                    result.append(builder);
                    result.append("\n");
                }
                result.append(this.nodes[i].toString(builder, this.nodes[i], level));
                builder.deleteCharAt(level);
            }
        }
        return result.toString();
    }

    private String toString(StringBuilder builder, WordNode node, int level) {
        char letter;
        StringBuilder result = new StringBuilder();

        level++;
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            letter = (char) (i + 'a');
            if (node.nodes[i] != null) {
                builder.append(letter);
                if (node.nodes[i].isWord()) {
                    result.append(builder);
                    result.append("\n");
                }
                result.append(node.nodes[i].toString(builder, node.nodes[i], level));
                builder.deleteCharAt(level);
            }
        }
        return result.toString();
    }
}