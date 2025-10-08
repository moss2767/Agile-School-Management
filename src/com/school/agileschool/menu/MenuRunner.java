package com.school.agileschool.menu;

import com.school.agileschool.utilities.InputManagementHandler;

import java.util.LinkedHashMap;

public class MenuRunner {
//    Scanner scanner = new Scanner(System.in);
    static MenuRunner singleObject;

    public void run() {
        InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
            System.out.println("Welcome to school");
            put("User Menu", MenuRunnerUser::run);
            put("Course Menu", CourseMenu::run);
        }});
    }

    public static MenuRunner getInstance(){
        if(singleObject == null){
            singleObject = new MenuRunner();
        }
        return singleObject;
    }
}