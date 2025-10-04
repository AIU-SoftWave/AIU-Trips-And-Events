package code;
import java.util.*;

class Student {
    private String id;
    private String name;
    private String email;
    private List<Course> courses = new ArrayList<>();

    public Student(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void enroll(Course course) {
        System.out.println("[Student] " + name + " enrolled in course " + course.getTitle());
        courses.add(course);
        course.addStudent(this);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
