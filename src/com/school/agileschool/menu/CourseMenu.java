package com.school.agileschool.menu;

import com.school.agileschool.common.Course;
import com.school.agileschool.persistence.JSONDB;
import com.school.agileschool.utilities.InputManagementHandler;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class CourseMenu {
    static JSONDB db = JSONDB.getInstance();
    public static void run() {
        InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
            put("Show all courses", CourseMenu::showAllCourses);
            put("Create course", () -> {});
            put("Show course", () -> {});
            put("Update Course", () -> {});
            put("Remove Course", () -> {});
        }});
    }

    void createCourseFlow() {

    }

    void showCourseFlow() {}
    void updateCourseFlow() {}
    void removeCourseFlow() {}

    public static void showAllCourses(){
        System.out.println("Here are the currently available courses:");
        List<Course> courseList = db.getCourses();
        for (Course course : courseList) {
            System.out.println(course.toString());
        }
//        System.out.println(Arrays.toString(courseList.toArray()));
    }
}
