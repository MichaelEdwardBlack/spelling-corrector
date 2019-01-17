package spell;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
            word = scanner.next();
            this.trieWords.add(word);
        }
        scanner.close();
    }

    public String suggestSimilarWord(String inputWord) {
        //FIXME
        return "";
    }

    public void printAllWords() {
        System.out.println(this.trieWords.toString());
    }

}