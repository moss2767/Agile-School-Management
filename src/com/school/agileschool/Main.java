package com.school.agileschool;

import com.school.agileschool.menu.MenuRunnerUser;
import com.school.agileschool.persistence.JSONDB;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        try {
            JSONDB.getInstanceFromDisk();
        } catch(Exception e) {
            throw new RuntimeException("Runtime error", e);
        }
        // MenuRunner.getInstance().run();
        MenuRunnerUser.run();
    }
}