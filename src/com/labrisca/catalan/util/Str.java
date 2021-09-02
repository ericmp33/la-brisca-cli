package com.labrisca.catalan.util;

public class Str {
    Str() {}

    public static String upperFirstChar(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    // returns centered String inside lines
    public static String centerStr(int lnLength, String s) {
        int i = (lnLength - s.length()) / 2;
        return " ".repeat(i) + s;
    }
}
