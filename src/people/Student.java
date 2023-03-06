package people;

import file.Course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Student extends User{

    // initialize database:

    /**
     * Fields: courseList and grades
     */

    // List of this student's current courses:
    private List<Course> courseList;

    // grades of this student
    private Map<String, String> grades;


    // Constructor:
    public Student(String id, String name, String userName, String passWord) {
        super(id, name, userName, passWord);
        this.courseList = new ArrayList<>();
        this.grades = new HashMap<String, String>();
    }

    /**
     * Set the map of the grades
     * @param course: the name of the course
     * @param grade: the grades of courses already taken by student
     */
    public void setGrades(String course, String grade) {
        this.grades.put(course, grade);
    }


    /**
     * Get the map of the grades
     */
    public Map<String, String> getGrades() {
        return this.grades;
    }


    /**
     * Get the list of the course
     */
    public List<Course> getCourseList(){
        return this.courseList;
    }


    /**
     * Get user type
     */
    @Override
    public String getUserType() {
        return "student";
    }


    /**
     * Add courses to the list. This method returns nothing.
     * @param course: A course ready to be added
     */
    @Override
    public int addCourse(Course course) throws ParseException {

        // check if this course is already taken, return 0
        if (this.grades.containsKey(course.getCourseNumber())) {
            return 0;
        }

        // check if this course is already added, return 1
        if (this.courseList.contains(course)) {
            return 1;
        }

        // if the course is full, return 2:
        if (!course.addStudent(this)) {
            return 2;
        }

        // if there is a conflict on time
        if (!this.checkCourseTime(this.getCourseList(), course)) {
            return 3;
        }

        // everything is good, add this course
        this.courseList.add(course);
        return 4;
    }


    /**
     * Return true if no conflict among all courses:
     * Return false if there is a conflict
     */
    private boolean checkCourseTime(List<Course> courseList, Course newCourse) throws ParseException {
        // if the list is empty, return true
        if (courseList.isEmpty()) {
            return true;
        }

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date newStartTime = df.parse(newCourse.getStartTime());
        Date newEndTime = df.parse(newCourse.getEndTime());
        Set<Character> days = new HashSet<>();

        for (char chr : newCourse.getDays().toCharArray()) {
            days.add(chr);
        }

        // check the time of every course
        for (Course course : courseList) {
            for (char chr : course.getDays().toCharArray()) {
                if (days.contains(chr)) {
                    Date startTime = df.parse(course.getStartTime());
                    Date endTime = df.parse(course.getEndTime());
                    boolean noStartConflict = newStartTime.before(startTime) && !newEndTime.after(startTime);
                    boolean noEndConflict = !newStartTime.before(endTime) && newEndTime.after(endTime);
                    if (!(noEndConflict ||noStartConflict)) {
                        System.out.println("The course you selected has time conflict with " + course.getCourseNumber()
                                + " " + course.getCourseName());
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * Print all description of courses in the list
     */
    public void viewSelectedCourses () {
        // corner case: empty list
        if (this.getCourseList().isEmpty()) {
            System.out.println("Your list is empty!");
        }

        for (Course course : this.getCourseList()) {
            if (course == null) {break;}
            System.out.println(course.getDescription());
        }
    }


    /**
     * Drop a specific course
     * @param courseNumber : the ID of the course which is going to be removed
     */
    public boolean dropCourse(String courseNumber) {
        // corner case: if the list is empty
        if (this.getCourseList().isEmpty()) {
            return false;
        }

        // drop the course if it is in the list:
        for (Course course : this.getCourseList()) {
            if (course.getCourseNumber().equals(courseNumber)) {
                this.getCourseList().remove(course);
                course.deleteStudent(this);
                return true;
            }
        }
        return false;
    }


}
