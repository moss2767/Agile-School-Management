package User;

public class Teacher extends Person {
    private int teacherID;
    private List<Course> coursesTaught;

    public Teacher(int teacherID, List<Course> coursesTaught) {
        this.teacherID = teacherID;
        this.coursesTaught = coursesTaught;
    }

    public Teacher(int teacherID) {
        this.teacherID = teacherID;
    }

    public Teacher(List<Course> coursesTaught) {
        this.coursesTaught = coursesTaught;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }

    public void setCoursesTaught(List<Course> coursesTaught) {
        this.coursesTaught = coursesTaught;
    }
}
