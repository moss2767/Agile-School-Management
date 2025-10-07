package com.school.agileschool.user;

import java.util.List;

public class Student extends Person {
    private String studentID;
    private List<String> courses;

    public Student(String name, String email) {
        super(name, email);
    }

    public Student(String name, String email, String studentID, List<String> courses) {
        super(name, email);
        this.studentID = studentID;
        this.courses = courses;
    }

    public String getStudentID () {
        return studentID;
    }

    public void setStudentID (String studentID) {
        this.studentID = studentID;
    }

    public List<String> getCourses () {
        return courses;
    }

    public void setCourses (List<String> courses) {
        this.courses = courses;
    }
}
