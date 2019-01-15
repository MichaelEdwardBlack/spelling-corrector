package spell;

/**
 * Your trie class should implement the ITrie interface
 */
public interface ITrie {

	/**
	 * Adds the specified word to the trie (if necessary) and increments the word's frequency count
	 * 
	 * @param word The word being added to the trie
	 */
	public void add(String word);
	
	/**
	 * Searches the trie for the specified word
	 * 
	 * @param word The word being searched for
	 * 
	 * @return A reference to the trie node that represents the word,
	 * 			or null if the word is not in the trie
	 */
	public INode find(String word);
	
	/**
	 * Returns the number of unique words in the trie
	 * 
	 * @return The number of unique words in the trie
	 */
	public int getWordCount();

	/**
	 * Returns the number of nodes in the trie
	 *
	 * @return The number of nodes in the trie
	 */
	public int getNodeCount();

	/**
	 * The toString specification is as follows:
	 * For each word, in alphabetical order:
	 * <word>\n
	 */
	@Override
	public String toString();

	@Override
	public int hashCode();
	
	@Override
	public boolean equals(Object o);

	/**
	 * Your trie node class should implement the ITrie.INode interface
	 */
	public interface INode {
	
		/**
		 * Returns the frequency count for the word represented by the node
		 * 
		 * @return The frequency count for the word represented by the node
		 */
		public int getCount();
	}
	
	public class Words implements ITrie {
		private int numNodes = 1; // includes the root node
		private int numWords = 0;
		private WordNode root = new WordNode();
		private WordNode iNode = root; // iterating node for the toString function

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

		/*
	 	@Override
		public int hashCode() {
			//FIXME
			return -1;
		}

		@Override
		public boolean equals(Object trie) {
			//FIXME
			return false;
		}
		*/

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
			String ret = "";

			for (int i = 0; i < ALPHABET_SIZE; i++) {
				if (this.nodes[i] == null) {
					// do nothing;
				}
				else {
					if (this.nodes[i].isWord()) {
						ret += this.nodes[i].getValue() + "\n";
					}
					ret += this.nodes[i].toString();
				}
			}
			return ret;
		}

	}
}
