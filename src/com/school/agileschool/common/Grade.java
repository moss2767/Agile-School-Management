package com.school.agileschool.common;

public class Grade {
    public enum Mark {
        F, E, D, C, B, A
    }

    private final String studentID, courseID;
    private Mark mark;

    public Grade(Mark mark, String studentID, String courseID){
        this.mark = mark;
        this.studentID = studentID;
        this.courseID = courseID;
    }

    public String getStudentID(){
        return  studentID;
    }

    public String getCourseID(){
        return courseID;
    }

    public Mark getMark(){
        return mark;
    }

    public String getMarkAsString(){
        return mark.toString();
    }

    public int getMarkAsInt(){
        return mark.ordinal();
    }

}
