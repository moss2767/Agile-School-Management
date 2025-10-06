import java.util.ArrayList;
import java.util.List;

public class Course {
    private final String courseID;
    private String assignedTeacherID = "";
    private final List<String> enrolledStudentsByID = new ArrayList<>();

    public Course(String courseID){
        this.courseID = courseID;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getAssignedTeacherID() {
        return assignedTeacherID;
    }

    public void setAssignedTeacherID(String assignedTeacherID) {
        this.assignedTeacherID = assignedTeacherID;
    }

    public List<String> getEnrolledStudentsByID() {
        return List.copyOf(enrolledStudentsByID);
    }

    public void enrollStudentByID(String studentID){
        enrolledStudentsByID.add(studentID);
    }
}
