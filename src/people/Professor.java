package people;

import file.Course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Professor extends User {

    // Fields: courseList
    private List<Course> courseList;

    // constructor
    public Professor(String id, String name, String userName, String passWord){
        super(id, name, userName, passWord);
        courseList = new ArrayList<>();
    }


    /**
     * Get user type
     */
    @Override
    public String getUserType() {
        return "professor";
    }

    public List<Course> getCourseList(){
        return courseList;
    }


    /**
     * Add a course to the prof
     * @param course: a new course to be taught by this prof
     * return 0 if this course is already in the list
     * return 1 if the course has time conflict with some course in the list
     * return 2 if the course is added into the list
     */
    @Override
    public int addCourse (Course course) throws ParseException {
        // if the list is empty, add it:
        if (courseList.isEmpty()) {
            courseList.add(course);
            return 1;
        }

        // if the course is already in the list:
        for (Course course1 : courseList) {
            if (course1.getCourseNumber().equals(course.getCourseNumber())) {
                return 0;
            }
        }

        // else, add this course
        courseList.add(course);
        return 1;
    }


    /**
     * Print all courses of this prof
     */
    public void showAllCourses(){
        for (Course course : courseList) {
            System.out.println(course.getDescription());
        }
    }


}
