package com.github.florent37.materialviewpager.sample.model;

/**
 * Created by Asus on 22-Apr-2018.
 */

public class Word {

    private String word;
    private String definition;
    private String pronounce;
    private String example;

    private boolean learned;
    private long timestamp;

    public Word() {
    }

    public Word(String mWord, String mDefinition, String mPronounce, String mExample) {
        this.word = mWord;
        this.definition = mDefinition;
        this.pronounce = mPronounce;
        this.example = mExample;

        learned = false;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String mWord) {
        this.word = mWord;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String mDefinition) {
        this.definition = mDefinition;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String mPronounce) {
        this.pronounce = mPronounce;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String mExample) {
        this.example = mExample;
    }

    public boolean isLearned() {
        return learned;
    }

    public void setLearned(boolean mLearned) {
        this.learned = mLearned;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long mTimestamp) {
        this.timestamp = mTimestamp;
    }
}
