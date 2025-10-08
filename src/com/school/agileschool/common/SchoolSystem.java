package com.school.agileschool.common;

import com.school.agileschool.persistence.JSONDB;
import com.school.agileschool.user.Person;
import com.school.agileschool.user.Student;
import com.school.agileschool.user.Teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SchoolSystem {
    private static SchoolSystem instance;

    private SchoolSystem(){}

    public Optional<List<Student>> getStudentsEnrolledInCourse(String courseId) {
        Optional<Course> course = JSONDB.getInstance().getCourseById(courseId);
        if (course.isPresent()) {
            return Optional.of(
                    course.get().getEnrolledStudentsByID()
                            .stream()
                            .map( (studentID) -> {
                                return JSONDB.getInstance().getStudentByID(studentID);
                            })
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toUnmodifiableList())
            );
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<List<Course>> getCoursesStudentIsEnrolledIn(String studentID) {
        Optional<Student> student = JSONDB.getInstance().getStudentByID(studentID);
        if (student.isPresent()) {
            return Optional.of(
                    student.get().getCourses()
                            .stream()
                            .map( (courseID) -> {
                                return JSONDB.getInstance().getCourseById(courseID);
                            })
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toUnmodifiableList())
            );
        }
        else {
            return Optional.empty();
        }
    }

    public boolean enrollStudentToCourse(String studentID, String courseID){
        Optional<Course> course = JSONDB.getInstance().getCourseById(courseID);
        Optional<Student> student = JSONDB.getInstance().getStudentByID(studentID);
        if (course.isPresent() && student.isPresent()) {
            if (course.get().getEnrolledStudentsByID().contains(studentID)
                    || student.get().getCourses().contains(courseID)) {
                return false;
            }
            student.get().addCourse(courseID);
            course.get().enrollStudentByID(studentID);
            return true;
        }
        return false;
    }

    public boolean unenrollStudentFromCourse(String studentID, String courseID){
        Optional<Course> course = JSONDB.getInstance().getCourseById(courseID);
        Optional<Student> student = JSONDB.getInstance().getStudentByID(studentID);
        if (course.isPresent() && course.get().getEnrolledStudentsByID().contains(studentID)
                && student.isPresent() && student.get().getCourses().contains(courseID)) {
            student.get().removeCourse(courseID);
            course.get().unenrollStudentByID(studentID);
            return true;
        }
        return false;
    }

    public boolean assignTeacherToCourse(String teacherID, String courseID){
        return false;
        /*
        if (!coursesByID.containsKey(courseID) || !peopleByID.containsKey(teacherID)){
            return false;
        }
        Course course = coursesByID.get(courseID);
        if (course.getAssignedTeacherID().equals(teacherID)) {
            return false;
        }
        if (peopleByID.get(teacherID) instanceof Teacher teacher) {
            if (teacher.getCoursesTaught().contains(courseID)) {
                return false;
            }
            if (peopleByID.containsKey(course.getAssignedTeacherID())) {
                if (peopleByID.get(course.getAssignedTeacherID()) instanceof Teacher t) {
                    t.removeCourseTaught(courseID);
                }
            }
            teacher.addCourseTaught(courseID);
            course.setAssignedTeacherID(teacherID);
            return true;
        }
        return false;
         */
    }

    public static SchoolSystem getInstance() {
        if (instance == null) {
            instance = new SchoolSystem();
        }
        return instance;
    }
}
