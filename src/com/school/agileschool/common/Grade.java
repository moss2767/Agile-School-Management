package com.school.agileschool.common;

public class Grade {
    public enum Mark {
        F, E, D, C, B, A
    }

    private final String courseID;
    private Mark mark;

    public Grade(Mark mark, String courseID){
        this.mark = mark;
        this.courseID = courseID;
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
