package code;

public class Main {
    public static void main(String[] args) {
        // Create students
        Student s1 = new Student("S001", "Ali", "ali@student.edu");
        Student s2 = new Student("S002", "Sara", "sara@student.edu");

        // Create course
        Course c1 = new Course("CS101", "Intro to Computer Science");

        // Enroll students
        s1.enroll(c1);
        s2.enroll(c1);

        // Create exam
        Exam midterm = new Exam("2025-10-03", "10:00 AM", 100, c1);

        // Create notifiers
        Notifier email = new EmailNotifier();
        Notifier sms = new SMSNotifier();

        // Grade students (just logging, no logic)
        midterm.gradeExam(s1, 80, email);
        midterm.gradeExam(s2, 45, sms);
    }
}
