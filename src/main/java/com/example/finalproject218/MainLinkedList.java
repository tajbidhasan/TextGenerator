package com.example.finalproject218;

import java.util.ArrayList;
import java.util.List;

public class MainLinkedList {
    private MainLink firstMainLink;

    public MainLinkedList() {
        firstMainLink = null;
    }

    public boolean isEmpty() {
        return firstMainLink == null;
    }

    public void insertFirst(String keyword, BabyLinkedList babyList) {
        MainLink newMainLink = new MainLink(keyword, babyList);
        newMainLink.next = firstMainLink;
        firstMainLink = newMainLink;
    }

    public MainLink findMainLink(String keyword) {
        MainLink currentMainLink = firstMainLink;
        while (currentMainLink != null) {
            if (currentMainLink.getKeyword().equals(keyword)) {
                return currentMainLink;
            }
            currentMainLink = currentMainLink.next;
        }
        return null;
    }

    public void displayList() {
        MainLink currentMainLink = firstMainLink;
        while (currentMainLink != null) {
            currentMainLink.displayMainLink();
            currentMainLink = currentMainLink.next;
        }
        System.out.println("");
    }

    public boolean containsKeyword(String keyword) {
        MainLink currentLink = firstMainLink;

        while (currentLink != null) {
            if (currentLink.getKeyword().equals(keyword)) {
                return true;
            }
            currentLink = currentLink.next;
        }

        return false;
    }

    public BabyLinkedList getBabyLinkedList(String keyword) {
        MainLink mainLink = findMainLink(keyword);
        return mainLink != null ? mainLink.getBabyList() : null;
    }
    public List<String> getAllKeywords() {
        List<String> keywords = new ArrayList<>();
        MainLink currentMainLink = firstMainLink;
        while (currentMainLink != null) {
            keywords.add(currentMainLink.getKeyword());
            currentMainLink = currentMainLink.next;
        }
        return keywords;
    }
}
