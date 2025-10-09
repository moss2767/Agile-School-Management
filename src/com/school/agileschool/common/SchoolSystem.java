package com.school.agileschool.common;

import com.school.agileschool.persistence.JSONDB;
import com.school.agileschool.user.Student;
import com.school.agileschool.user.Teacher;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SchoolSystem {
    private static SchoolSystem instance;
    private final JSONDB db;

    private SchoolSystem(){
        db = JSONDB.getInstance();
    }

    public List<Student> getStudentsEnrolledInCourse(String courseId) {
        Optional<Course> course = db.getCourseById(courseId);
        return course.map(c -> c.getEnrolledStudentsByID()
                .stream()
                .map(db::getStudentByID)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList()).orElseGet(List::of);
    }

    public List<Course> getCoursesStudentIsEnrolledIn(String studentID) {
        Optional<Student> student = db.getStudentByID(studentID);
        return student.map(s -> s.getCourses()
                .stream()
                .map(db::getCourseById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList()).orElseGet(List::of);
    }

    public List<Course> getCoursesHeldByTeacher(String teacherID) {
        Optional<Teacher> teacher = db.getTeacherById(teacherID);
        return teacher.map(t -> t.getCoursesTaught()
                .stream()
                .map(db::getCourseById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList()).orElseGet(List::of);
    }

    public boolean enrollStudentToCourse(String studentID, String courseID){
        Optional<Course> course = db.getCourseById(courseID);
        Optional<Student> student = db.getStudentByID(studentID);
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
        Optional<Course> course = db.getCourseById(courseID);
        Optional<Student> student = db.getStudentByID(studentID);
        if (course.isPresent() && course.get().getEnrolledStudentsByID().contains(studentID)
                && student.isPresent() && student.get().getCourses().contains(courseID)) {
            student.get().removeCourse(courseID);
            course.get().unenrollStudentByID(studentID);
            return true;
        }
        return false;
    }

    public boolean assignTeacherToCourse(String teacherID, String courseID){
        Optional<Course> course = db.getCourseById(courseID);
        Optional<Teacher> teacher = db.getTeacherById(teacherID);
        if (course.isPresent() && teacher.isPresent()) {
            if (course.get().getAssignedTeacherID().equals(teacherID)
                    || teacher.get().getCoursesTaught().contains(courseID)) {
                return false;
            }
            if (!course.get().getAssignedTeacherID().isEmpty()) {
                Optional<Teacher> oldTeacher = db.getTeacherById(course.get().getAssignedTeacherID());
                oldTeacher.ifPresent(t -> t.removeCourseTaught(courseID));
            }
            course.get().setAssignedTeacherID(teacherID);
            teacher.get().addCourseTaught(courseID);
            return true;
        }
        return false;
    }

    public boolean removeTeacherFromCourse(String teacherID, String courseID){
        Optional<Course> course = db.getCourseById(courseID);
        Optional<Teacher> teacher = db.getTeacherById(teacherID);
        if (course.isPresent() && course.get().getAssignedTeacherID().equals(teacherID)
                && teacher.isPresent() && teacher.get().getCoursesTaught().contains(courseID)) {
            course.get().setAssignedTeacherID("");
            teacher.get().removeCourseTaught(courseID);
            return true;
        }
        return false;
    }

    public boolean assignGradeToStudentByCourse(Grade grade, String studentID, String courseID) {
        Optional<Student> student = db.getStudentByID(studentID);
        Optional<Course> course = db.getCourseById(courseID);
        if (course.isPresent() && student.isPresent()
                && !student.get().getGrades().containsKey(courseID)) {
            student.get().setGrade(courseID, grade);
            return true;
        }
        return false;
    }

    public boolean assignGradeToStudentByCourse(int grade, String studentID, String courseID) {
        if (grade >= Grade.values().length) {
            return false;
        }
        return assignGradeToStudentByCourse(Grade.values()[grade], studentID, courseID);
    }

    public boolean assignGradeToStudentByCourse(String grade, String studentID, String courseID) {
        if (Arrays.stream(Grade.values()).noneMatch(g -> g.toString().equals(grade))) {
            return false;
        }
        return assignGradeToStudentByCourse(Grade.valueOf(grade), studentID, courseID);
    }

    public boolean updateGradeOfStudentByCourse(Grade grade, String studentID, String courseID) {
        Optional<Student> student = db.getStudentByID(studentID);
        Optional<Course> course = db.getCourseById(courseID);
        if (course.isPresent() && student.isPresent()
                && student.get().getGrades().containsKey(courseID)) {
            student.get().updateGrade(courseID, grade);
            return true;
        }
        return false;
    }

    public boolean updateGradeOfStudentByCourse(int grade, String studentID, String courseID) {
        if (grade >= Grade.values().length) {
            return false;
        }
        return updateGradeOfStudentByCourse(Grade.values()[grade], studentID, courseID);
    }

    public boolean updateGradeOfStudentByCourse(String grade, String studentID, String courseID) {
        if (Arrays.stream(Grade.values()).noneMatch(g -> g.toString().equals(grade))) {
            return false;
        }
        return updateGradeOfStudentByCourse(Grade.valueOf(grade), studentID, courseID);
    }

    public static SchoolSystem getInstance() {
        if (instance == null) {
            instance = new SchoolSystem();
        }
        return instance;
    }
}
