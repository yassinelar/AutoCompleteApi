package com.example.searchEngine;
import java.util.HashMap;
import java.util.Map;

public class PrefixTree {
    private PrefixTreeNode root;

    public PrefixTree() {
        root = new PrefixTreeNode();
    }

    public void addSentenceTree(String s, Integer count) {   //insert sentence into the tree character by character. The last node contains the count value (number of time the query was typed
        PrefixTreeNode current = root;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            PrefixTreeNode node = current.children.get(ch);
            if (node == null) {
                node = new PrefixTreeNode();
                current.children.put(ch, node);
            }
            current = node;
        }
        current.endOfSentence = count;
    }

    public Map<String, Integer> search(String prefix) {
        Map<String, Integer> completeSentences = new HashMap();
        PrefixTreeNode currentNode = root;
        for(int i = 0; i < prefix.length(); i++) {
            currentNode = currentNode.children.get(prefix.charAt(i));
            if(currentNode==null) {
                return completeSentences;
            }
        }
        searchSentence(currentNode, completeSentences, prefix);
        return completeSentences;
    }

    private void searchSentence(PrefixTreeNode currentNode, Map<String, Integer> autoCompWords, String word) {
        if(currentNode == null) {
            return;
        }
        if(currentNode.endOfSentence != -1) {
            autoCompWords.put(word, currentNode.endOfSentence);
        }
        Map<Character,PrefixTreeNode> map = currentNode.children;
        for(Character c:map.keySet()) {
            searchSentence(map.get(c), autoCompWords, word+String.valueOf(c));
        }
    }
}
