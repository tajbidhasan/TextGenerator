package com.example.finalproject218;


public class MainLink {
     String keyword;
    BabyLinkedList babyList;
     MainLink next;

    public MainLink(String keyword, BabyLinkedList babyList) {
        this.keyword = keyword;
        this.babyList = babyList;
        this.next = null;
    }

    public String getKeyword() {
        return keyword;
    }

    public BabyLinkedList getBabyLinkedList() {
        return babyList;
    }
    public BabyLinkedList getBabyList() {
        return babyList;
    }
    public void displayMainLink() {
        System.out.println("Keyword: " + keyword + ", Baby List: " + babyList);
    }
}



