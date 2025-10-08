package code;

import java.util.*;

class Exam {
    private String date;
    private String time;
    private int totalMarks;
    private Course course;
    private List<Result> results = new ArrayList<>();

    public Exam(String date, String time, int totalMarks, Course course) {
        this.date = date;
        this.time = time;
        this.totalMarks = totalMarks;
        this.course = course;
        course.addExam(this);
    }

    public Result gradeExam(Student student, int marks, Notifier notifier) {
        System.out.println("[Exam] Grading " + student.getName() + " with " + marks + " marks");
        Result result = new Result(student, this, marks);
        results.add(result);

        result.notifyStudent(notifier);
        return result;
    }
}
