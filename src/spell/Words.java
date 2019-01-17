package spell;

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
            if (currentNode.hasChild(currentLetter)) {
                currentNode = currentNode.nodes[index];
            }
            else {
                currentNode.nodes[index] = new WordNode();
                currentNode = currentNode.nodes[index];
                this.numNodes++;
            }
        }
        currentNode.setValue(word);
        currentNode.incrementCount();
        this.numWords++;
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
                // Return a null WordNode
                return new WordNode();
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

    public WordNode getRoot() {
        return this.root;
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
    private String value;
    private int count;
    private boolean isWord;
    static final int ALPHABET_SIZE = 26;

    // Default Constructor
    public WordNode() {
        nodes = new WordNode[ALPHABET_SIZE];
        count = 0;
        isWord = false;
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            nodes[i] = null;
        }
    }

    // Getters and Setters
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
        isWord = true;
    }

    public int getCount() {
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
        return this.isWord;
    }

    public boolean equals(WordNode node) {
        if (this.value != node.getValue()) {
            return false;
        }
        if (this.count != node.getCount()) {
            return false;
        }
        boolean equals = true;
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (this.nodes[i].equals(node.nodes[i]) == false) {
                equals = false;
            }
        }
        return equals;
    }

    public String toString() {
        String result = "";

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (this.nodes[i] == null) {
                // do nothing;
            }
            else {
                if (this.nodes[i].isWord()) {
                    result += this.nodes[i].getValue() + "\n";
                }
                result += this.nodes[i].toString();
            }
        }
        return result;
    }

}