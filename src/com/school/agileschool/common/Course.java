package com.school.agileschool.common;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private final String courseID;
    private String name, assignedTeacherID = "";
    private final List<String> enrolledStudentsByID = new ArrayList<>();

    public Course(String courseID, String name){
        this.courseID = courseID;
        this.name = name;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignedTeacherID() {
        return assignedTeacherID;
    }

    public void setAssignedTeacherID(String assignedTeacherID) {
        this.assignedTeacherID = assignedTeacherID;
    }

    public List<String> getEnrolledStudentsByID() {
        return List.copyOf(enrolledStudentsByID);
    }

    public void enrollStudentByID(String studentID){
        enrolledStudentsByID.add(studentID);
    }

    public void unenrollStudentByID(String studentID){
        enrolledStudentsByID.remove(studentID);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Course ID: %s%n", this.getCourseID()));
        sb.append(String.format("Course Name: %s%n", this.getName()));
        sb.append(String.format("Assigned Teacher ID: %s%n", this.getAssignedTeacherID()));
        sb.append(String.format("Enrolled students by ID: %s%n", this.getEnrolledStudentsByID()));
        return sb.toString();
    }
}
