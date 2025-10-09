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
    final private static JSONDB db = JSONDB.getInstance();

    private enum StudentMenuReturnIntent {
        CONTINUE,
        QUIT
    }
    private enum StudentMenuReturnTypeOfChange {
        FIRST_NAME,
        LAST_NAME,
        QUIT
    }
    public static void runStudentAdministration(Student student){
        System.out.println("You are now administering student: " + student.getName());
        while (true) {
            StudentMenuReturnIntent menuReturnValue = InputManagementHandler.runMenuType(new LinkedHashMap<String, Callable<StudentMenuReturnIntent>>() {{
                put("Remove the student from the system", () -> {
                    System.out.println("Student have been removed");
                    db.removeStudent(student.getStudentID());
                    return StudentMenuReturnIntent.QUIT;
                });
                put("Print student details", () -> {
                    System.out.println(student);
                    return StudentMenuReturnIntent.CONTINUE;
                });
                put("Change student details", () -> {
                    while (true) {
                        StudentMenuReturnTypeOfChange typeOfChangeFromInput = InputManagementHandler.runMenuType(new LinkedHashMap<String, Callable<StudentMenuReturnTypeOfChange>>() {{
                            put("Change first name", () -> StudentMenuReturnTypeOfChange.FIRST_NAME);
                            put("Change last name", () -> StudentMenuReturnTypeOfChange.LAST_NAME);
                            put("Quit", () -> StudentMenuReturnTypeOfChange.QUIT);
                        }});
                        if (typeOfChangeFromInput == StudentMenuReturnTypeOfChange.FIRST_NAME) {
                            String input = InputManagementHandler.getLineAsString("First name").trim();
                            student.setFirstName(input);
                        }
                        if (typeOfChangeFromInput == StudentMenuReturnTypeOfChange.LAST_NAME) {
                            String input = InputManagementHandler.getLineAsString("Last name").trim();
                            student.setLastName(input);
                        }
                        if (typeOfChangeFromInput == StudentMenuReturnTypeOfChange.QUIT) {
                            break;
                        }
                    }
                    return StudentMenuReturnIntent.CONTINUE;
                });
                put("Quit", () -> StudentMenuReturnIntent.QUIT);
            }});
            if (menuReturnValue == StudentMenuReturnIntent.QUIT) {
                break;
            }
        }
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
                Student student = new Student(userInputAsHashMap.get("First name"), userInputAsHashMap.get("Last name"), userInputAsHashMap.get("Email"));
                db.addStudent(student.getStudentID(), student);
                /*
                String tempID = student.generatePersonID();
                while(true) {
                    final String finalTempID = tempID;
                    boolean exists = JSONDB.getInstance().getStudents().stream()
                            .anyMatch(studentMatch -> finalTempID.equals(studentMatch.getStudentID()));
                    if(!exists) break;
                    tempID = student.generatePersonID();
                }
                student.modifyStudentID(tempID);
                db.addStudent(student.getStudentID(), student);
                 */
            });
            put("Administer an existing student", () -> {
                selectAndRunStudentAdministration(db.getStudents());
            });
            put("Search and administer an existing student", () -> {
                String searchQuery = InputManagementHandler.getLineAsString("Search");
                Pattern compiledPatternForSearchQuery = Pattern.compile(searchQuery, Pattern.CASE_INSENSITIVE);
                List<Student> matches = db
                        .getStudents()
                        .stream()
                        .filter(s -> compiledPatternForSearchQuery.matcher(s.getName()).find())
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
    }
}
