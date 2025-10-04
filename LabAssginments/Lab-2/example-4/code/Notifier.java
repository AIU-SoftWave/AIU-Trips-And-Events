package code;

interface Notifier {
    void send(String message, Student student);
}

class EmailNotifier implements Notifier {
    @Override
    public void send(String message, Student student) {
        System.out.println("[EmailNotifier] Sending email to " + student.getEmail()
                + " with message: " + message);
    }
}

class SMSNotifier implements Notifier {
    @Override
    public void send(String message, Student student) {
        System.out.println("[SMSNotifier] Sending SMS to " + student.getName()
                + " with message: " + message);
    }
}
