package com.school.agileschool.user;

import com.school.agileschool.common.Course;

import java.util.List;

public class Teacher extends Person {
    private int teacherID;
    private List<Course> coursesTaught;

    public Teacher(String name, String email) {
        super(name,email);
    }

    public Teacher(String name, String email, int teacherID, List<Course> coursesTaught) {
        super(name,email);
        this.teacherID = teacherID;
        this.coursesTaught = coursesTaught;
    }

    public Teacher(String name, String email, int teacherID) {
        super(name,email);
        this.teacherID = teacherID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }

    public void setCoursesTaught(List<Course> coursesTaught) {
        this.coursesTaught = coursesTaught;
    }
}
