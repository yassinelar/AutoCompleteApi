package com.example.searchEngine;
import java.util.HashMap;
import java.util.Map;

public class PrefixTree {
    private PrefixTreeNode root;

    public PrefixTree() {
        root = new PrefixTreeNode();
    }

    public void addWordTree(String s, Integer count) {
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
        current.endOfWord = count;
    }

    public Map<String, Integer> search(String prefix){
        Map<String, Integer> autoCompWords = new HashMap();

        PrefixTreeNode currentNode = root;

        for(int i = 0; i < prefix.length(); i++) {
            currentNode = currentNode.children.get(prefix.charAt(i));
            if(currentNode==null) {
                return autoCompWords;
            }
        }

        searchWords(currentNode, autoCompWords, prefix);
        return autoCompWords;
    }

    private void searchWords(PrefixTreeNode currentNode, Map<String, Integer> autoCompWords, String word) {
        if(currentNode == null) {
            return;
        }

        if(currentNode.endOfWord != -1) {
            autoCompWords.put(word, currentNode.endOfWord);
        }

        Map<Character,PrefixTreeNode> map = currentNode.children;
        for(Character c:map.keySet()) {
            searchWords(map.get(c),autoCompWords, word+String.valueOf(c));
        }
    }
}
