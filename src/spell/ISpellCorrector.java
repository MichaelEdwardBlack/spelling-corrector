package spell;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public interface ISpellCorrector {

	/**
	 * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
	 * for generating suggestions.
	 * @param dictionaryFileName File containing the words to be used
	 * @throws IOException If the file cannot be read
	 */
	public void useDictionary(String dictionaryFileName) throws IOException;

	/**
	 * Suggest a word from the dictionary that most closely matches
	 * <code>inputWord</code>
	 * @param inputWord
	 * @return The suggestion or null if there is no similar word in the dictionary
	 */
	public String suggestSimilarWord(String inputWord);

	// Class to implement the ISpellCorrector Interface
	public class SpellCorrector implements ISpellCorrector {
		ITrie.Words trieWords = new ITrie.Words();

		public void useDictionary(String dictionaryFileName) throws IOException {
			File inputFile = new File(dictionaryFileName);
			FileInputStream fis = new FileInputStream(inputFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			Scanner scanner = new Scanner(bis);
			String word;

			while (scanner.hasNext()) {
				word = scanner.next();
				this.trieWords.add(word);
			}
			scanner.close();
		}

		public String suggestSimilarWord(String inputWord) {
			//FIXME
			return "";
		}

		public String getAllWords() {
			return this.trieWords.toString();
		}

	}

}

