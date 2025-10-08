package code;

import java.util.*;

class Course {
    private String code;
    private String title;
    private List<Student> students = new ArrayList<>();
    private List<Exam> exams = new ArrayList<>();

    public Course(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public void addStudent(Student student) {
        System.out.println("[Course] Adding student " + student.getName() + " to " + title);
        students.add(student);
    }

    public void addExam(Exam exam) {
        System.out.println("[Course] Adding exam to " + title);
        exams.add(exam);
    }

    public String getTitle() {
        return title;
    }
}
