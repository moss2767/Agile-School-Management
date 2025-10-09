package com.school.agileschool.menu;

import com.school.agileschool.common.Course;
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
            put("Remove Course", CourseMenu::removeCourseFlow);
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
            String newId = InputManagementHandler.getLineAsString("Enter new id (leave blank to keep old)").toUpperCase();
            String name = InputManagementHandler.getLineAsString("Enter new name (leave blank to keep old)");
            String assignedTeacherID = InputManagementHandler.getLineAsString("Enter ID of teacher you wish to assign (leave blank for old)");
            String studentIDtoEnroll = InputManagementHandler.getLineAsString("Enter ID of student you wish to enroll (or leave blank)");
            String studentIDtoUnenroll = InputManagementHandler.getLineAsString("Enter ID of student you wish to UNenroll (or leave blank)");

            if(!newId.isEmpty()) {
                //creates a new course object with the provided id and desired name
                String newName = name.isEmpty() ? course.getName() : name;
                Course oldCourse = course;
                course = new Course(newId, newName);
                course.setAssignedTeacherID(oldCourse.getAssignedTeacherID());
                for( String enrolledStudentID : oldCourse.getEnrolledStudentsByID()) {
                    course.enrollStudentByID(enrolledStudentID);
                }
            }
            if(!name.isEmpty()) {
                course.setName(name);
            }
            if(!assignedTeacherID.isEmpty()) {
                course.setAssignedTeacherID(assignedTeacherID);
            }
            if(!studentIDtoEnroll.isEmpty()) {
                course.enrollStudentByID(studentIDtoEnroll);
            }
            if(!studentIDtoUnenroll.isEmpty()) {
                course.unenrollStudentByID(studentIDtoUnenroll);
            }

            db.updateCourse(id, course);


        } else {
            System.out.println("No course by that ID exists");
        }
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
