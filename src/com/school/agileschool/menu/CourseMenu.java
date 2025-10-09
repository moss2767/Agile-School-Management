package com.school.agileschool.menu;

import com.school.agileschool.common.Course;
import com.school.agileschool.common.SchoolSystem;
import com.school.agileschool.persistence.JSONDB;
import com.school.agileschool.utilities.InputManagementHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class CourseMenu {
    static JSONDB db = JSONDB.getInstance();
    public static void run() {
        InputManagementHandler.runMenuUntilQuit(new LinkedHashMap<>() {{
            put("Show all courses", CourseMenu::showAllCourses);
            put("Create course", CourseMenu::createCourseFlow);
            put("Show course", CourseMenu::showCourseFlow);
            put("Update Course", CourseMenu::updateCourseFlow);
        }});
    }

    static void createCourseFlow() {
        System.out.println("----Creating a new course----");
        String id = InputManagementHandler.getLineAsString("Course ID").toUpperCase();
        String name = InputManagementHandler.getLineAsString("Name");
        Course createdCourse = new Course(id, name);
        db.addCourse(createdCourse.getCourseID(), createdCourse);
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
        Optional<Course> courseOptional = db.getCourseById(id);
        if(courseOptional.isPresent()) {
            Course course = courseOptional.get();
            String name = InputManagementHandler.getLineAsString("Enter new name (leave blank to keep old)");
            String assignedTeacherID = InputManagementHandler.getLineAsString("Enter ID of teacher you wish to assign (leave blank for old)");
            String studentIDtoEnroll = InputManagementHandler.getLineAsString("Enter ID of student you wish to enroll (or leave blank)");
            String studentIDtoUnenroll = InputManagementHandler.getLineAsString("Enter ID of student you wish to UNenroll (or leave blank)");

            if(!name.isEmpty()) {
                course.setName(name);
            }
            if(!assignedTeacherID.isEmpty()) {
                //TODO Remove this line once assignTeacherToCourse is implemented
                course.setAssignedTeacherID(assignedTeacherID);
                SchoolSystem.getInstance().assignTeacherToCourse(assignedTeacherID, id);
            }
            if(!studentIDtoEnroll.isEmpty()) {
                SchoolSystem.getInstance().enrollStudentToCourse(studentIDtoEnroll, id);
            }
            if(!studentIDtoUnenroll.isEmpty()) {
                SchoolSystem.getInstance().unenrollStudentFromCourse(studentIDtoUnenroll, id);
            }
        } else {
            System.out.println("No course by that ID exists");
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
