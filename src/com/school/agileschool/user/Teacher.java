
package com.school.agileschool.user;

import java.util.ArrayList;

public class Teacher extends Person {
    private String teacherID;
    private final ArrayList<String> coursesTaught = new ArrayList<>();

    public Teacher(String firstName, String lastName, String email) {
        super(firstName,lastName,email);
        this.teacherID = String.format("T-%s",generatePersonID());
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void modifyTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public ArrayList<String> getCoursesTaught() {
        return coursesTaught;
    }

    public void addCourseTaught(String course) {
        this.coursesTaught.add(course);
    }

    public void removeCourseTaught(String course){
        this.coursesTaught.remove(course);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Teacher first name: %s%n", this.getFirstName()));
        sb.append(String.format("Teacher last name: %s%n", this.getLastName()));
        sb.append(String.format("Teacher email: %s%n", this.getEmail()));
        sb.append(String.format("TeacherID: %s%n", teacherID));
        return sb.toString();
    }
}