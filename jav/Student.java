package jav;

class Student {
    private String studentId;
    private String firstName;
    private String lastName;

    public Student(String studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    @Override
    public String toString() {
        return String.format("ID: %-10s | Name: %s %s", studentId, firstName, lastName);
    }
}
