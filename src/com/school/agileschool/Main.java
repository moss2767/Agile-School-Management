package com.school.agileschool;

import com.school.agileschool.menu.MenuRunner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        MenuRunner.getInstance().run();
    }
}