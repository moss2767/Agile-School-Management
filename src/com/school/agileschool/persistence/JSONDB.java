package com.school.agileschool.persistence;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.school.agileschool.common.Course;
import com.school.agileschool.user.Student;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JSONDB {
    private static JSONDB instance;

    @Expose
    private List<Course> courses = new ArrayList<>();

    @Expose
    private List<Student> students = new ArrayList<>();

    private static String FILENAME = "persistence/db.json";
    JSONDB () {

    }

    public static JSONDB initializeFromDisk() throws IOException {
        File file = new File(FILENAME);
        Gson gson = new Gson();
        if (file.exists()) {
            try (FileReader r =  new FileReader(file)) {
                JSONDB db = gson.fromJson(r, JSONDB.class);
                return db;
            }
        }
        else {
            String str = gson.toJson(new JSONDB(), JSONDB.class);
            try (FileWriter writer = new FileWriter(FILENAME)) {
                writer.write(str);
            }
            return initializeFromDisk();
        }
    }
    public boolean writeToDisk() {
        return true;
    }

    public static JSONDB getInstance() {
        if (instance == null) {
            instance = new JSONDB();
        }
        return instance;
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

    public Optional<List<Course>> getCoursesStudentIsEnrolledIn(String studentID) {
        Optional<Student> student = getStudentByID(studentID);
        if (student.isPresent()) {
            List<Course> list = student.get().getCourses()
                    .stream()
                    .map(id -> getCourseById(id))
                    .filter(optional -> optional.isPresent())
                    .map(Optional::get)
                    .toList();
            return Optional.of(list);
        }
        else {
            return Optional.empty();
        }
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }
}
