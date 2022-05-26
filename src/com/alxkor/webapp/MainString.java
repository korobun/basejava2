package com.alxkor.webapp;

public class MainString {
    public static void main(String[] args) {
        String[] strArray = {"1", "2", "3", "4", "5", "6", "7"};
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str).append(", ");
        }
        System.out.println(sb.toString());

        String str1 = "abc";
        String str2 = "ab";
        String str3 = str2 + "c";
        System.out.println(str1 == str3.intern());
    }
}
