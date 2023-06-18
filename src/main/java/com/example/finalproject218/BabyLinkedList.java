package com.example.finalproject218;

import java.util.ArrayList;

public class BabyLinkedList {
    private ArrayList<BabyLink> babyLinks;

    public BabyLinkedList() {
        babyLinks = new ArrayList<>();
    }

    public void insert(String word) {
        BabyLink babyLink = new BabyLink(word);
        babyLinks.add(babyLink);
    }

    public String getRandomWord() {
        int randomIndex = (int) (Math.random() * babyLinks.size());
        return babyLinks.get(randomIndex).getWord();
    }
    public boolean isEmpty() {
        return babyLinks == null;
    }

}
