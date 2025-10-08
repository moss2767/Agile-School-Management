package com.school.agileschool.menu;

import com.school.agileschool.common.Course;
import com.school.agileschool.common.SchoolSystem;
import com.school.agileschool.persistence.JSONDB;
import com.school.agileschool.utilities.InputManagementHandler;

import java.util.*;

public class CourseMenu {
    static JSONDB db = JSONDB.getInstance();
    public static void run() {
        InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
            put("Show all courses", CourseMenu::showAllCourses);
            put("Create course", CourseMenu::createCourseFlow);
            put("Show course", CourseMenu::showCourseFlow);
            put("Update Course", CourseMenu::updateCourseFlow);
            put("Remove Course", CourseMenu::removeCourseFlow);
        }});
    }

    static void createCourseFlow() {
        System.out.println("----Creating a new course----");
        String id = InputManagementHandler.getLineAsString("Course ID").toUpperCase();
        String name = InputManagementHandler.getLineAsString("Name");
        Course createdCourse = new Course(id, name);
    }

    static void showCourseFlow() {
        System.out.println("----Showing a course----");
        String id = InputManagementHandler.getLineAsString("Enter Course ID").toUpperCase();
        Optional<Course> course = db.getCourseById(id);
        if (course.isPresent()){
            System.out.println(course.get());
        } else {
            System.out.println("No course by that id");
        }
    }

    static void updateCourseFlow() {
        System.out.println("----Updating an existing course----");
        String id = InputManagementHandler.getLineAsString("Enter Course ID").toUpperCase();
        Optional<Course> course = db.getCourseById(id);
    }

    static void removeCourseFlow() {
        System.out.println("----Removing a course----");
        String id = InputManagementHandler.getLineAsString("Enter Course ID (exit to quit)").toUpperCase();
        if (id.equals("EXIT")) {
            return;
        }
        Optional<Course> course = db.getCourseById(id);
        if (course.isPresent()){
            System.out.printf("Course found: %s%n", course.get());
            String response = InputManagementHandler.getLineAsString("Remove this course? (y/n/exit)").toLowerCase();
            switch (response) {
                case "y":
                    db.removeCourse(id);
                    break;
                case "n":
                    removeCourseFlow();
                    break;
                case "exit":
                    System.out.println("exiting...");
                    break;
            }
        } else {
            System.out.println("course not found.");
            removeCourseFlow();
        }
    }

    public static void showAllCourses(){
        System.out.println("Here are the currently available courses:");
        List<Course> courseList = db.getCourses();
        for (Course course : courseList) {
            System.out.println(course.toString());
        }
    }
}
