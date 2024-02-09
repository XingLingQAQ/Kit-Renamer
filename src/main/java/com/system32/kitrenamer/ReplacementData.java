package com.system32.kitrenamer;

public class ReplacementData {
    String regex;
    String replacement;
    public ReplacementData(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    public String getRegex() {
        return regex;
    }

    public String getReplacement() {
        return replacement;
    }
}
