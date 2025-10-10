package com.school.agileschool.persistence;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.school.agileschool.common.Course;
import com.school.agileschool.common.Grade;
import com.school.agileschool.user.Student;
import com.school.agileschool.user.Teacher;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JSONDB {
    private static JSONDB instance;

    @Expose
    final private List<Course> courses = new ArrayList<>();

    @Expose
    final private List<Student> students = new ArrayList<>();

    final private List<Teacher> teachers = new ArrayList<>();

    final private static String FILENAME = "persistence/db.json";

    JSONDB () {

    }

    public void writeToDisk() throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(this, JSONDB.class);
        try (FileWriter writer = new FileWriter(FILENAME)) {
            writer.write(str);
        }
    }

    public Optional<Student> getStudentByID(String id) {
        return students
                .stream()
                .filter(student -> id.equals(student.getStudentID()))
                .findFirst();
    }

    public Optional<Course> getCourseById(String id) {
        return courses
                .stream()
                .filter(course -> id.equals(course.getCourseID()))
                .findFirst();
    }


    public Optional<Teacher> getTeacherById(String id) {
        return teachers
                .stream()
                .filter(teacher -> id.equals(teacher.getTeacherID()))
                .findFirst();
    }

    public boolean addCourse(String courseId, Course course) {
        Optional<Course> courseOptional = getCourseById(courseId);
        if (courseOptional.isPresent()) {
            return false;
        }
        courses.add(course);
        return true;
    }

    public boolean removeCourse(String courseId) {
        Optional<Course> courseOptional = getCourseById(courseId);
        if (courseOptional.isPresent()) {
            courses.remove(courseOptional.get());
            return true;
        }
        return false;
    }

    public boolean addStudent(String studentId, Student student) {
        Optional<Student> studentOptional = getStudentByID(studentId);
        if (studentOptional.isPresent()) {
            return false;
        }
        students.add(student);
        return true;
    }

    public boolean removeStudent(String studentId) {
        Optional<Student> studentOptional = getStudentByID(studentId);
        if (studentOptional.isPresent()) {
            students.remove(studentOptional.get());
            return true;
        }
        return false;
    }


    public boolean addTeacher(String teacherId, Teacher teacher) {
        Optional<Teacher> teacherOptional = getTeacherById(teacherId);
        if (teacherOptional.isPresent()) {
            return false;
        }
        teachers.add(teacher);
        return true;
    }

    public boolean removeTeacher(String teacherId) {
        Optional<Teacher> teacherOptional = getTeacherById(teacherId);
        if (teacherOptional.isPresent()) {
            students.remove(teacherOptional.get());
            return true;
        }
        return false;
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public List<Teacher> getTeachers() {
        return Collections.unmodifiableList(teachers);
    }

    private static JSONDB instanceFromDisk() throws IOException {
        File file = new File(FILENAME);
        Gson gson = new Gson();
        if (file.exists()) {
            try (FileReader r =  new FileReader(file)) {
                return gson.fromJson(r, JSONDB.class);
            }
        }
        else {
            String str = gson.toJson(new JSONDB(), JSONDB.class);
            try (FileWriter writer = new FileWriter(FILENAME)) {
                writer.write(str);
            }
            return instanceFromDisk();
        }
    }

    public static JSONDB getInstance() {
        if (instance == null) {
            instance = new JSONDB();
        }
        return instance;
    }

    public static JSONDB getInstanceFromDisk() throws IOException {
        if (instance == null) {
            instance = instanceFromDisk();
        }
        return instance;
    }
}
