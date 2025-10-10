package com.school.agileschool.user;

import com.school.agileschool.common.Grade;

import com.school.agileschool.persistence.JSONDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Student extends Person {
    private final List<String> courses = new ArrayList<>();
    private final Map<String, Grade> grades = new HashMap<>();

    public Student(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public String getStudentID () {
        return getId();
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

    public void setGrade(String courseID, Grade grade) {
        grades.putIfAbsent(courseID, grade);
    }

    public void updateGrade(String courseID, Grade grade) {
        if (grades.containsKey(courseID)) {
            grades.put(courseID, grade);
        }
    }

    public String getGradesAsFormattedString() {
        StringBuilder sb = new StringBuilder();
        grades.forEach((course, grade) -> sb.append(String.format("%s: %s%n", course, grade)));
        return sb.toString();
    }

    public String getGradeFromCourse(String courseID) {
        if (grades.containsKey(courseID)) {
            return grades.get(courseID).toString();
        }
        return "Student has no grade in given course.";
    }

    public Map<String, Grade> getGrades() {
        return new HashMap<>(grades);
    }

    @Override
    boolean checkIfIDExistInPersistentStorage(String id) {
        return JSONDB.getInstance().getStudentByID(id).isPresent();
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
