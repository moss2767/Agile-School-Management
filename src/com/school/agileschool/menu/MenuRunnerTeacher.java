package com.school.agileschool.menu;

import com.school.agileschool.common.SchoolSystem;
import com.school.agileschool.persistence.JSONDB;
import com.school.agileschool.user.Person;
import com.school.agileschool.user.Teacher;
import com.school.agileschool.utilities.InputManagementHandler;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MenuRunnerTeacher {
    final private static JSONDB db = JSONDB.getInstance();

    private enum TeacherMenuReturnIntent {
        CONTINUE,
        QUIT
    }
    private enum TeacherMenuReturnTypeOfChange {
        FIRST_NAME,
        LAST_NAME,
        QUIT
    }

    public static void run() {
        InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
            put("Add a teacher to the school", () -> {
                List<String> keys = Arrays.asList("First name", "Last name", "Email");
                Map<String, String> userInputAsHashMap = InputManagementHandler.fillHashMapWithScan(keys);
                Teacher teacher = new Teacher(userInputAsHashMap.get("First name"), userInputAsHashMap.get("Last name"), userInputAsHashMap.get("Email"));
                db.addTeacher(teacher.getTeacherID(), teacher);
                /*
                String tempID = teacher.generatePersonID();
                while(true) {
                    final String finalTempID = tempID;
                    boolean exists = JSONDB.getInstance().getTeachers().stream()
                            .anyMatch(teacherMatch -> finalTempID.equals(teacherMatch.getTeacherID()));
                    if(!exists) break;
                    tempID = teacher.generatePersonID();
                }
                teacher.modifyTeacherID(tempID);
                */
            });
            put("Administer an existing teacher", () -> {
                selectTeacherAndRunAdministration(db.getTeachers());
            });
            put("Search and administer an existing student", () -> {
                String searchQuery = InputManagementHandler.getLineAsString("Search");
                Pattern compiledPatternForSearchQuery = Pattern.compile(searchQuery, Pattern.CASE_INSENSITIVE);
                List<Teacher> matches = db
                        .getTeachers()
                        .stream()
                        .filter(s -> compiledPatternForSearchQuery.matcher(s.getName()).find())
                        .collect(Collectors.toUnmodifiableList());
                if (matches.isEmpty()) {
                    System.out.println("No matches found");
                }
                else if (matches.size() == 1) {
                    runTeacherAdministration(matches.get(0));
                }
                else {
                    selectTeacherAndRunAdministration(matches);
                }
            });
        }});
    }

    public static void runTeacherAdministration(Teacher teacher){
        System.out.println("You are now administering teacher: " + teacher.getName());
        while (true) {
            MenuRunnerTeacher.TeacherMenuReturnIntent menuReturnValue = InputManagementHandler.runMenuType(new LinkedHashMap<String, Callable<MenuRunnerTeacher.TeacherMenuReturnIntent>>() {{
                put("Remove the teacher from the system", () -> {
                    System.out.println("Teacher has been removed");
                    db.removeTeacher(teacher.getTeacherID());
                    return MenuRunnerTeacher.TeacherMenuReturnIntent.QUIT;
                });
                put("Print teacher details", () -> {
                    System.out.println(teacher);
                    return MenuRunnerTeacher.TeacherMenuReturnIntent.CONTINUE;
                });
                put("Change teacher details", () -> {
                    while (true) {
                        MenuRunnerTeacher.TeacherMenuReturnTypeOfChange typeOfChangeFromInput = InputManagementHandler.runMenuType(new LinkedHashMap<String, Callable<MenuRunnerTeacher.TeacherMenuReturnTypeOfChange>>() {{
                            put("Change first name", () -> MenuRunnerTeacher.TeacherMenuReturnTypeOfChange.FIRST_NAME);
                            put("Change last name", () -> MenuRunnerTeacher.TeacherMenuReturnTypeOfChange.LAST_NAME);
                            put("Quit", () -> MenuRunnerTeacher.TeacherMenuReturnTypeOfChange.QUIT);
                        }});
                        if (typeOfChangeFromInput == MenuRunnerTeacher.TeacherMenuReturnTypeOfChange.FIRST_NAME) {
                            String input = InputManagementHandler.getLineAsString("First name").trim();
                            teacher.setFirstName(input);
                        }
                        if (typeOfChangeFromInput == MenuRunnerTeacher.TeacherMenuReturnTypeOfChange.LAST_NAME) {
                            String input = InputManagementHandler.getLineAsString("Last name").trim();
                            teacher.setLastName(input);
                        }
                        if (typeOfChangeFromInput == MenuRunnerTeacher.TeacherMenuReturnTypeOfChange.QUIT) {
                            break;
                        }
                    }
                    return MenuRunnerTeacher.TeacherMenuReturnIntent.CONTINUE;
                });
                put("Quit", () -> MenuRunnerTeacher.TeacherMenuReturnIntent.QUIT);
            }});
            if (menuReturnValue == MenuRunnerTeacher.TeacherMenuReturnIntent.QUIT) {
                break;
            }
        }
    }
    public static void selectTeacherAndRunAdministration(List<Teacher> teachers){
        System.out.println("Select a teacher");
        Map<String, Callable<Teacher>> menu = teachers.stream()
                .collect(
                        Collectors.toMap(
                                Person::getName,
                                teacher -> () -> teacher,
                                (existing, replacement) -> existing, // merge function in case of duplicate keys
                                HashMap::new       // supplier for the HashMap
                        )
                );
        runTeacherAdministration(InputManagementHandler.runMenuType(menu));
    }
}