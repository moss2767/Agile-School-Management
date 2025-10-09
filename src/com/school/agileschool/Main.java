package com.school.agileschool;

import com.school.agileschool.menu.MenuRunner;
import com.school.agileschool.persistence.JSONDB;

public class Main {
    private static JSONDB db;
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        try {
            db = JSONDB.getInstanceFromDisk();
        } catch(Exception e) {
            throw new RuntimeException("Runtime error", e);
        }
        MenuRunner.getInstance().run();
        try {
            db.writeToDisk();
        } catch(Exception e) {
            throw new RuntimeException("Runtime error", e);
        }
    }
}
