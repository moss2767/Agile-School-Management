
package com.school.agileschool.user;

import java.util.ArrayList;

public class Teacher extends Person {
    private String teacherID;
    private final ArrayList<String> coursesTaught = new ArrayList<>();

    public Teacher(String name, String email) {
        super(name,email);
    }

    public Teacher(String name, String email, String teacherID) {
        super(name,email);
        this.teacherID = teacherID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
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
}