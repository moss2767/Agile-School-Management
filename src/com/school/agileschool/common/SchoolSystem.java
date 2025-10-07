package com.school.agileschool.common;

import com.school.agileschool.user.Person;
import com.school.agileschool.user.Student;
import com.school.agileschool.user.Teacher;

import java.util.HashMap;
import java.util.Map;

public class SchoolSystem {
    private SchoolSystem instance;
    private final Map<String, Course> coursesByID = new HashMap<>();
    private final Map<String, Person> peopleByID = new HashMap<>();

    private SchoolSystem(){}

    public void addStudent(Student student){
        peopleByID.put(student.getStudentID(), student);
    }

    public void addTeacher(Teacher teacher){
        peopleByID.put(teacher.getTeacherID(), teacher);
    }

    public void addCourse(Course course){
        coursesByID.put(course.getCourseID(), course);
    }

    public boolean enrollStudentToCourse(String studentID, String courseID){
        if (!coursesByID.containsKey(courseID) || !peopleByID.containsKey(studentID)){
            return false;
        }
        Course course = coursesByID.get(courseID);
        if (course.getEnrolledStudentsByID().contains(studentID)){
            return false;
        }
        if (peopleByID.get(studentID) instanceof Student student) {
            if (student.getCourses().contains(courseID)) {
                return false;
            }
            course.enrollStudentByID(studentID);
            student.addCourse(courseID);
            return true;
        }
        return false;
    }

    public SchoolSystem getInstance() {
        if (instance == null) {
            instance = new SchoolSystem();
        }
        return instance;
    }
}
