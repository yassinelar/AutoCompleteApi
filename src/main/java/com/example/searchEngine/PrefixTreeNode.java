package com.example.searchEngine;

import java.util.HashMap;
import java.util.Map;

public class PrefixTreeNode {
    Map<Character, PrefixTreeNode> children;
    int endOfSentence;

    PrefixTreeNode() {
        children = new HashMap<Character, PrefixTreeNode>();
        endOfSentence = -1;
    }
}

