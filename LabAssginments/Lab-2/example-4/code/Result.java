package code;

class Result {
    private Student student;
    private Exam exam;
    private int marks;

    public Result(Student student, Exam exam, int marks) {
        this.student = student;
        this.exam = exam;
        this.marks = marks;
    }

    public void notifyStudent(Notifier notifier) {
        String message = "[Result] Student " + student.getName()
                + " got " + marks + " marks.";
        notifier.send(message, student);
    }
}
