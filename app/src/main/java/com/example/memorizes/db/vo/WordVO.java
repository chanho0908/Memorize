package com.example.memorizes.db.vo;

import android.os.Parcel;

public class WordVO {
    private String word;
    private String mean;
    private String group;

    public WordVO(String word, String mean, String group) {
        this.word = word;
        this.mean = mean;
        this.group = group;
    }

    protected WordVO(Parcel in) {
        word = in.readString();
        mean = in.readString();
        group = in.readString();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

}
