package com.school.agileschool.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Student extends Person {
    private String studentID;
    private final List<String> courses = new ArrayList<>();

    public Student(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
        this.studentID = String.format("S-%s", generatePersonID());
    }

    public String getStudentID () {
        return studentID;
    }

    public void modifyStudentID (String studentID) {
        this.studentID = studentID;
    }

    public List<String> getCourses () {
        return courses;
    }

    public void addCourse (String course) {
        this.courses.add(course);
    }

    public void addCourses (List<String> courses) {
        this.courses.addAll(courses);
    }

    public void removeCourse (String course) {
        courses.remove(course);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Student first name: %s%n", this.getFirstName()));
        sb.append(String.format("Student last name: %s%n", this.getLastName()));
        sb.append(String.format("Student ID: %s%n", this.getStudentID()));
        sb.append(String.format("Student e-mail: %s%n", this.getEmail()));
        return sb.toString();
    }

}
