package com.school.agileschool.user;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String studentID;
    private final List<String> courses = new ArrayList<>();

    public Student(String name, String email) {
        super(name, email);
    }

    public Student(String name, String email, String studentID, List<String> courses) {
        super(name, email);
        this.studentID = studentID;
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

    public void addCourse (String course) {
        this.courses.add(course);
    }

    public void addCourses (List<String> courses) {
        this.courses.addAll(courses);
    }

    public void removeCourse (String course) {
        courses.remove(course);
    }
}
