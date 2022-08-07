package com.example.searchEngine;

import java.util.HashMap;
import java.util.Map;

public class PrefixTreeNode {
    Map<Character, PrefixTreeNode> children;
    int endOfWord;

    PrefixTreeNode() {
        children = new HashMap<Character, PrefixTreeNode>();
        endOfWord = -1;
    }
}

