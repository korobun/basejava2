package com.alxkor.webapp;

import java.io.File;

public class MainFile {
    public static StringBuilder tab = new StringBuilder("");

    public static void main(String[] args) {
        String path = ".\\src";
        File dir = new File(path);

        printFiles(dir);
    }

    public static void printFiles(File file) {
        File[] list = file.listFiles();
        if (list != null) {
            for (File f : list) {
                if (f.isDirectory()) {
                    System.out.println(tab.toString() + "\\" + f.getName() + ":");
                    tab.append("\t");
                    printFiles(f);
                    tab.delete(tab.length() - 1, tab.length());
                } else {
                    System.out.println(tab.toString() + f.getName());
                }
            }
        }
    }
}
