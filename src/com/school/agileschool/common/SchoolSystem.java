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

    public boolean assignTeacherToCourse(String teacherID, String courseID){
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
    }

    public SchoolSystem getInstance() {
        if (instance == null) {
            instance = new SchoolSystem();
        }
        return instance;
    }
}
