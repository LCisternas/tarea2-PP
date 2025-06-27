package com.miempresa.bloom.backend.model;

import java.util.List;

public class Item {
    private String id;
    private String bloomLevel;
    private ItemType itemType;
    private String question;
    private List<String> options;
    private int correctAnswer;
    private int estimatedTimeSec;

    public Item() { }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getBloomLevel() {
        return bloomLevel;
    }
    public void setBloomLevel(String bloomLevel) {
        this.bloomLevel = bloomLevel;
    }

    public ItemType getItemType() {
        return itemType;
    }
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }
    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getEstimatedTimeSec() {
        return estimatedTimeSec;
    }
    public void setEstimatedTimeSec(int estimatedTimeSec) {
        this.estimatedTimeSec = estimatedTimeSec;
    }
}
