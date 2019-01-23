package spell;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    private Words trieWords = new Words();

    public Words getTrie() {
        return this.trieWords;
    }

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
        ArrayList<String> candidates = createCandidates(inputWord);
        ArrayList<String> candidates2 = new ArrayList<>();
        ArrayList<String> matches = new ArrayList<>();

        if (trieWords.find(inputWord) == null) {
            return null;
        }

        if (trieWords.find(inputWord).isWord()) {
            return inputWord;
        }

        for (int i = 0; i < candidates.size(); i++) {
            if (trieWords.find(candidates.get(i)) != null
                    && trieWords.find(candidates.get(i)).isWord()) {
                matches.add(candidates.get(i));
            }
        }

        if (matches.size() == 0) {
            for (int i = 0; i < candidates.size(); i++) {
                candidates2.add(createCandidates(candidates.get(i)).toString());
            }
            matches = candidates2;
        }

        if (matches.size() > 1) {
            matches = getHighestCounts(matches);
            if (matches.size() > 1) {
                return firstAlphabeticalWord(matches);
            }
        }

        return matches.toString();
    }

    private ArrayList<String> createCandidates(String inputWord) {
        ArrayList<String> candidates = new ArrayList<>();
        int wordLength = inputWord.length();
        char insertLetter;

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
        return candidates;
    }

    private ArrayList<String> getHighestCounts(ArrayList<String> matches) {
        int maxCount = 0;
        int currentCount;
        for (int i = 0; i < matches.size(); i++) {
            currentCount = trieWords.find(matches.get(i)).getValue();
            maxCount = currentCount > maxCount ? currentCount : maxCount;
        }
        for (int i = 0; i < matches.size(); i++) {
            if (trieWords.find(matches.get(i)).getValue() < maxCount) {
                matches.remove(i);
            }
        }
        return matches;
    }

    private String firstAlphabeticalWord(ArrayList<String> matches) {
        String firstAlphabeticalWord = "zzz";
        for (int i = 0; i < matches.size(); i++) {
            if (matches.get(i).compareTo(firstAlphabeticalWord) < 0) {
                firstAlphabeticalWord = matches.get(i);
            }
        }
        return firstAlphabeticalWord;
    }

    // used for testing
    public void printAllWords() {
        System.out.println(this.trieWords.toString());
    }

}