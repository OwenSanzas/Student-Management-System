package file;

import people.Student;

import java.util.ArrayList;
import java.util.List;

public class Course {
    /**
     * Fields: description, courseNumber, courseName, instructor, days, startTime, endTime, maxCapacity, currentEnrollment, allStudents
     */

    private String description;
    private String courseNumber;

    private String courseName;

    private String instructor;

    private String days;

    private String startTime;

    private String endTime;

    private double maxCapacity;

    private int currentEnrollment;

    private List<Student> allStudents;

    // Constructor
    public Course(String description, String courseNumber, String courseName, String instructor, String days, String startTime, String endTime, double maxCapacity){
        this.description = description;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.instructor = instructor;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = 0;
        this.allStudents = new ArrayList<>();
    }

    /**
     * getters of all fields
     */

    public String getDescription(){
        return description;
    }

    public String getCourseNumber(){
        return courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getDays() {
        return days;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public double getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentEnrollment() {
        return currentEnrollment;
    }

    public List<Student> getAllStudents() {return allStudents;}

    /**
     * Add a new student into the course
     */
    public boolean addStudent(Student student) {
        // if the class is full, return false
        if (currentEnrollment >= maxCapacity) {
            return false;
        }

        // the student is already enrolled
        if (!this.allStudents.isEmpty() && this.allStudents.contains(student)) {
            return false;
        }

        // if the class is not full: add this student
        this.allStudents.add(student);
        currentEnrollment++;
        return true;
    }


    /**
     * delete a student into the course
     */
    public void deleteStudent(Student student) {
        // remove the student
        this.allStudents.remove(student);
        currentEnrollment--;
    }

    /**
     * print the student list of the course
     */
    public void showStudentList(){
        for (Student student : allStudents) {
            if (student == null) {
                System.out.println("No student enrolled.");
            }
            System.out.println(student.getID() + " " + student.getName());
        }
    }

}
