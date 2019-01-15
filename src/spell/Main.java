package spell;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {
		String dictionaryFileName = "";
		String inputWord = "";

		try {
			dictionaryFileName = args[0];
			inputWord = args[1];
		} catch (Exception e) {
			System.err.println("Invalid number of arguments");
		}
		/*
		ISpellCorrector corrector = null;
		
		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}
		
		System.out.println("Suggestion is: " + suggestion);
		*/
		ISpellCorrector.SpellCorrector corrector = null;
		try {
			corrector = new ISpellCorrector.SpellCorrector();
			corrector.useDictionary(dictionaryFileName);
		} catch (Exception e) {
			System.out.println("Scanner problem\n");
		}
		try {
			System.out.println(corrector.getAllWords());
		} catch (Exception e) {
			System.out.println("print words problem");
		}
	}

}
