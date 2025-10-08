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
}
