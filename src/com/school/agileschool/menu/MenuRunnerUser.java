package com.school.agileschool.menu;

import com.school.agileschool.common.SchoolSystem;
import com.school.agileschool.persistence.JSONDB;
import com.school.agileschool.user.Person;
import com.school.agileschool.user.Student;
import com.school.agileschool.utilities.InputManagementHandler;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MenuRunnerUser {
    private static String studentFullName(Student student) {
        return student.getFirstName() + " " + student.getLastName();
    }
    public static void runStudentAdministration(Student student){
        System.out.println("You are now administering student: " + student.getName());
        InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
            put("Remove the student from the system", () -> {

            });
        }});
    }
    public static void selectAndRunStudentAdministration(List<Student> students){
        System.out.println("Select a student");
        Map<String, Callable<Student>> menu = students.stream()
                .collect(
                        Collectors.toMap(
                                Person::getName,
                                student -> () -> student,
                                (existing, replacement) -> existing, // merge function in case of duplicate keys
                                HashMap::new       // supplier for the HashMap
                        )
                );
        runStudentAdministration(InputManagementHandler.runMenuType(menu));
    }
    public static void run() {
        InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
            put("Enroll a student to the school", () -> {
                List<String> keys = Arrays.asList("First name", "Last name", "Email");
                Map<String, String> userInputAsHashMap = InputManagementHandler.fillHashMapWithScan(keys);
                Student student = new Student(userInputAsHashMap.get("First name"), userInputAsHashMap.get("Last name"), userInputAsHashMap.get("Email"));student.modifyStudentID(userInputAsHashMap.get("ID"));
                student.modifyStudentID(student.generatePersonID());
                JSONDB.getInstance()
                        .addStudent(student.getStudentID(), student);
            });
            put("Administer an existing student", () -> {
                InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
                    put("Select from all students", () -> {
                        selectAndRunStudentAdministration(JSONDB.getInstance().getStudents());
                    });
                    put("Filter and select students", () -> {
                        String searchInput = InputManagementHandler.getLineAsString("Search");
                        Pattern matchingPattern = Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE);
                        List<Student> matches = JSONDB.getInstance()
                                .getStudents()
                                .stream()
                                .filter(s -> matchingPattern.matcher(studentFullName(s)).find())
                                .collect(Collectors.toUnmodifiableList());
                        if (matches.isEmpty()) {
                            System.out.println("No matches found");
                        }
                        else if (matches.size() == 1) {
                            runStudentAdministration(matches.get(0));
                        }
                        else {
                            selectAndRunStudentAdministration(matches);
                        }
                    });
                }});

            });
        }});
    }
}
