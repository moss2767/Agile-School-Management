package User;

import java.util.ArrayList;

public class Student extends Person {
    private int studentID;
    private ArrayList<String> courses;

    public Student(String name, String email) {
        super(name, email);
    }

    public Student(String name, String email, int studentID, ArrayList<String> courses) {
        super(name, email);
        this.studentID = studentID;
        this.courses = courses;
    }

    public int getStudentID () {
        return studentID;
    }

    public void setStudentID (int studentID) {
        this.studentID = studentID;
    }

    public ArrayList<String> getCourses () {
        return courses;
    }

    public void setCourses (ArrayList<String> courses) {
        this.courses = courses;
    }
}
