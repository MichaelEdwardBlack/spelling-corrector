package spell;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    Words trieWords = new Words();

    public void useDictionary(String dictionaryFileName) throws IOException {
        File inputFile = new File(dictionaryFileName);
        FileInputStream fis = new FileInputStream(inputFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        Scanner scanner = new Scanner(bis);
        String word;

        while (scanner.hasNext()) {
            word = scanner.next().toLowerCase();
            this.trieWords.add(word);
        }
        scanner.close();
    }

    public String suggestSimilarWord(String inputWord) {
        //FIXME
        ArrayList candidates = new ArrayList();
        ArrayList matches = new ArrayList();
        int wordLength = inputWord.length();
        char insertLetter = 'a';

        if (trieWords.find(inputWord).isWord()) {
            return inputWord;
        }

        // Add Deletion Candidates
        for (int i = 0; i < wordLength; i++) {
            candidates.add(inputWord.substring(0, i) + inputWord.substring(i + 1, wordLength));

            // Add Alteration Candidates
            for (int j = 0; j < 26; j++) {
                insertLetter = (char) ('a' + j);
                candidates.add(inputWord.substring(0, i) + insertLetter + inputWord.substring(i + 1, wordLength));
            }
        }

        // Add Insertion Candidates
        for (int i = 0; i <= wordLength; i++) {
            for (int j = 0; j < 26; j++) {
                insertLetter = (char) ('a' + j);
                candidates.add(inputWord.substring(0, i) + insertLetter + inputWord.substring(i, wordLength));
            }
        }

        // Add Transposition Candidates
        for (int i = 0; i < wordLength - 1; i++) {
            candidates.add(inputWord.substring(0, i) + inputWord.substring(i+1,i+2) + inputWord.substring(i,i+1)
                    + inputWord.substring(i+2, wordLength));
        }


        return "";
    }

    public void printAllWords() {
        System.out.println(this.trieWords.toString());
    }

}