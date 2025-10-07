package com.school.agileschool.menu;

import com.school.agileschool.utilities.InputManagementHandler;

import java.util.LinkedHashMap;

public class MenuRunnerUser {

    public static void run() {
        InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
            put("Enroll a student to the school", () -> {
                String firstName = InputManagementHandler.getLineAsString("First name");
                String lastName = InputManagementHandler.getLineAsString("Last name");
                String email = InputManagementHandler.getLineAsString("Email");
            });
            put("Administer a student", () -> {
                String query = InputManagementHandler.getLineAsString("Search");

            });
        }});
    }
}
