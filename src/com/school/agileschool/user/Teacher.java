
package com.school.agileschool.user;

import java.util.ArrayList;

public class Teacher extends Person {
    private String teacherID;
    private ArrayList<String> coursesTaught;

    public Teacher(String name, String email) {
        super(name,email);
    }

    public Teacher(String name, String email, String teacherID, ArrayList<String> coursesTaught) {
        super(name,email);
        this.teacherID = teacherID;
        this.coursesTaught = coursesTaught;
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

    public void setCoursesTaught(ArrayList<String> coursesTaught) {
        this.coursesTaught = coursesTaught;
    }
}

