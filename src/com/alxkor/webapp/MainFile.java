package com.alxkor.webapp;

import java.io.File;

public class MainFile {
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
                    System.out.println("Dir \\" + f.getName() + ":");
                    printFiles(f);
                } else {
                    System.out.println("\t" + f.getName());
                }
            }
        }
    }
}
