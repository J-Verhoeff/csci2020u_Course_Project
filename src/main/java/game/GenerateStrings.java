package game;

import java.util.*;

// class to generate strings for typing game
public class GenerateStrings {

    private ArrayList<String> wordList; // arraylist to store words

    // contructor
    public GenerateStrings() {
        // fills array list with words
        wordList = new ArrayList<>();
        wordList.add(0,"abandon");
        wordList.add(1,"abate");
        wordList.add(2,"abbreviate");
        wordList.add(3,"abdicate");
        wordList.add(4,"aberration");
        wordList.add(5,"abhorrence");
        wordList.add(6,"ability");
        wordList.add(7,"ablaze");
        wordList.add(8,"abnormal");
        wordList.add(9,"aboard");
        wordList.add(10,"board");
        wordList.add(11,"coast");
        wordList.add(12,"driven");
        wordList.add(13,"establish");
        wordList.add(14,"for");
        wordList.add(15,"great");
        wordList.add(16,"help");
        wordList.add(17,"just");
        wordList.add(18,"kelp");
        wordList.add(19,"loop");
        wordList.add(20,"moon");
        wordList.add(21,"nose");
        wordList.add(22,"opal");
        wordList.add(23,"pool");
        wordList.add(24,"quest");
        wordList.add(25,"roast");
        wordList.add(26,"store");
        wordList.add(27,"toll");
        wordList.add(28,"upper");
        wordList.add(29,"vest");
        wordList.add(30,"west");
        wordList.add(31,"yes");
        wordList.add(32,"zebra");
    }

    /**
     * @return the wordList
     */
    public ArrayList<String> getWordList() {
        return wordList;
    }
}