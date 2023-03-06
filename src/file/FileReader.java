package file;

import people.Admin;
import people.Professor;
import people.Student;

import java.io.BufferedReader;
import java.io.File;
import java.util.*;

/**
 * This is the database of all information about people and courses
 * Loads people and courses data from two given data files.
 */
public class FileReader {

    // List of students information
    private List<Student> studentsInfo = new ArrayList<>();

    // List of professors information:
    private List<Professor> professorsInfo = new ArrayList<>();

    // List of admins information
    private List<Admin> adminsInfo = new ArrayList<>();

    // List of all courses
    private List<Course> courseInfo = new ArrayList<>();

    /**
     * Load all data from .txt files
     *
     * @param studentInfo: files of students
     * @
     */
    public void setUp(String studentInfo, String profInfo, String adminInfo, String courseInfo) {
        // load all people files
        try {
            // new files
            File studentFile = new File(studentInfo);
            File profFile = new File(profInfo);
            File adminFile = new File(adminInfo);

            // new file readers
            java.io.FileReader studentReader = new java.io.FileReader(studentFile);
            java.io.FileReader profReader = new java.io.FileReader(profFile);
            java.io.FileReader adminReader = new java.io.FileReader(adminFile);

            BufferedReader studentBr = new BufferedReader(studentReader);
            BufferedReader profBr = new BufferedReader(profReader);
            BufferedReader adminBr = new BufferedReader(adminReader);

            // load student file:
            while (true) {
                String line = studentBr.readLine();
                if (line == null) {
                    break;
                }

                String[] array = line.trim().split(";");
                String id = array[0].trim();
                String name = array[1].trim();
                String userName = array[2].trim();
                String passWord = array[3].trim();

                // load id, name, userName and passWord
                Student newStudent = new Student(id, name, userName, passWord);

                // load grades
                if (array.length > 4) {
                    // case 1: if this student has some course which is taken, add it
                    String[] courseGrade = array[4].trim().split(",");

                    for (int i = 0; i < courseGrade.length; i++) {
                        String course = courseGrade[i].split(":")[0].trim();
                        String grade = courseGrade[i].split(":")[1].trim();
                        newStudent.setGrades(course, grade);
                    }
                }
                // case 2: if this student is a new student without courses taken, return
                this.studentsInfo.add(newStudent);
            }

            // close file readers
            studentReader.close();
            studentBr.close();


            // load prof file:
            while (true) {
                String line = profBr.readLine();
                if (line == null) {
                    break;
                }

                String[] array = line.trim().split(";");
                String id = array[1].trim();
                String name = array[0].trim();
                String userName = array[2].trim();
                String passWord = array[3].trim();

                // load id, name, userName and passWord
                Professor prof = new Professor(id, name, userName, passWord);

                // add the prof into the list
                this.professorsInfo.add(prof);
            }

            // close file reader
            profReader.close();
            profBr.close();


            // load admins file:
            while (true) {
                String line = adminBr.readLine();
                if (line == null) {
                    break;
                }

                String[] array = line.trim().split(";");
                String id = array[0].trim();
                String name = array[1].trim();
                String userName = array[2].trim();
                String passWord = array[3].trim();

                // load id, name, userName and passWord
                Admin admin = new Admin(id, name, userName, passWord);

                // add the prof into the list
                this.adminsInfo.add(admin);
            }

            // load admins file:
            adminReader.close();
            adminBr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        // load all courses
        try {
            File courseFile = new File(courseInfo);
            java.io.FileReader courseReader = new java.io.FileReader(courseFile);
            BufferedReader courseBr = new BufferedReader(courseReader);
            while (true) {
                String line = courseBr.readLine();
                if (line == null) {
                    break;
                }

                String[] array = line.trim().split(";");
                String courseID = array[0].trim();
                String courseName = array[1].trim();
                String instructor = array[2].trim();
                String days = array[3].trim();
                String time1 = array[4].trim();
                String time2 = array[5].trim();
                int maxCapacity = findCapacity(array[6]);

                // load id, name, userName and passWord
                Course newCourse = new Course(line, courseID, courseName, instructor, days, time1, time2, maxCapacity);

                // case 1: if this is a new course
                this.courseInfo.add(newCourse);

                // case 2: add course to instructors
                for (Professor prof : professorsInfo) {
                    if (prof.getName().equals(instructor)) {
                        prof.addCourse(newCourse);
                    }
                }
            }
            courseReader.close();
            courseBr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Student login
     * @param userName: input userName
     * @param password: password
     */
    public Student studentLogin(String userName, String password) {
        // check whether it is a valid account
        for (Student student : studentsInfo) {
            if (student.getUserName().equals(userName) && student.getPassWord().equals(password)) {
                return student;
            }

        }
        return null;
    }


    /**
     * Professor login
     * @param userName: input userName
     * @param password: password
     */
    public Professor professorLogin(String userName, String password) {
        for (Professor professor : professorsInfo) {
            if (professor.getUserName().equals(userName) && professor.getPassWord().equals(password)) {
                return professor;
            }
        }
        return null;
    }


    /**
     * Admin login
     * @param userName: input userName
     * @param password: password
     */
    public Admin adminLogin(String userName, String password) {
        for (Admin admin : adminsInfo) {
            if (admin.getUserName().equals(userName) && admin.getPassWord().equals(password)) {
                return admin;
            }
        }
        return null;
    }


    /**
     * Given is a string like "100" and return 100 which is an integer
     *
     * @param str: input digital string
     * @return maxCapacity: an int of the max capacity of a course
     */
    public int findCapacity(String str) {
        str = str.trim();
        if (str.length() == 0) {
            return 0;
        }

        char[] array = str.toCharArray();
        int maxCapacity = 0;
        int length = array.length;

        // determine the max capacity:
        for (char chr : array) {
            maxCapacity += (chr - '0') * Math.pow(10, length - 1);
            length--;
        }
        return maxCapacity;
    }

    /**
     * Find and return a specific course:
     */
    public Course findCourse(String courseID) {
        for (Course course : courseInfo) {
            if (course.getCourseNumber().equals(courseID)) {
                return course;
            }
        }
        return null;
    }


    /**
     * Add a new student:
     */
    public boolean addNewStudent(String id, String name, String userName, String passWord) {
        Student student = new Student(id, name, userName, passWord);
        studentsInfo.add(student);
        System.out.println("Successfully added student: " + name + ", " + id);
        return true;
    }


    /**
     * Add a new professor
     */
    public boolean addNewProf(String id, String name, String userName, String passWord) {
        // check if the id is already in the system:
        for (Professor professor : professorsInfo) {
            if (professor.getID().equals(id)) {
                System.out.println("This ID is already used");
                return false;
            }
        }

        // check if the username is already used
        for (Professor professor : professorsInfo) {
            if (professor.getUserName().equals(userName)) {
                System.out.println("This Username is already used");
                return false;
            }
        }

        // else create a new prof in the database:
        Professor newProf = new Professor(id, name, userName, passWord);
        this.professorsInfo.add(newProf);
        System.out.println("Successfully added the new professor: " + id + " " + name);
        return true;

    }



    /**
     * Print all courses information:
     */
    public void showAllCourses() {
        if (courseInfo.isEmpty()) {
            System.out.println("The course list is empty.");
        }

        for (Course course : courseInfo) {
            System.out.println(course.getDescription());
        }
    }


    /**
     * Get students information.
     *
     * @return list of students
     */
    public List<Student> getStudentsInfo() {
        return studentsInfo;
    }


    /**
     * Get prof information.
     *
     * @return list of profs
     */
    public List<Professor> getProfessorsInfo() {
        return professorsInfo;
    }


    /**
     * Get admin information.
     *
     * @return list of admins
     */
    public List<Admin> getAdminsInfo() {
        return adminsInfo;
    }


    /**
     * Get course information.
     * @return list of admins
     */
    public List<Course> getCourseInfo() {
        return courseInfo;
    }
}
