package com.example.searchEngine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class util {
    public static int regex(String sentence, String stringRegex) {
        Pattern pattern = Pattern.compile(stringRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sentence);
        boolean matchFound = matcher.find();
        if (matchFound) {
            return matcher.start();
        }
        return -1;
    }
}
