package com.school.agileschool.menu;

import com.school.agileschool.persistence.JSONDB;
import com.school.agileschool.user.Student;
import com.school.agileschool.utilities.InputManagementHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MenuRunnerUser {
    public static void run() {
        InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
            put("Enroll a student to the school", () -> {
                String firstName = InputManagementHandler.getLineAsString("First name");
                String lastName = InputManagementHandler.getLineAsString("Last name");
                String email = InputManagementHandler.getLineAsString("Email");
            });
            put("Administer an existing student", () -> {
                String searchInput = InputManagementHandler.getLineAsString("Search");
                Pattern matchingPattern = Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE);
                List<Student> matches = JSONDB.getInstance()
                        .getStudents()
                        .stream()
                        .filter(s -> matchingPattern.matcher(s.getName()).find())
                        .collect(Collectors.toUnmodifiableList());
                if (matches.isEmpty()) {
                    System.out.println("No matches found");
                }
                Map<String, Callable<Student>> menu = matches.stream()
                        .collect(
                                Collectors.toMap(
                                        Student::getName,
                                        student -> () -> student,
                                        (existing, replacement) -> existing, // merge function in case of duplicate keys
                                        HashMap::new       // supplier for the HashMap
                                )
                        );
                Student selectStudent = InputManagementHandler.runMenuType(menu);
                System.out.println(selectStudent.getName());
            });
        }});
    }
}
