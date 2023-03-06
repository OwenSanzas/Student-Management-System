package people;

import file.Course;
import file.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {

    // constructor
    public Admin(String id, String name, String userName, String passWord) {
        super(id, name, userName, passWord);
    }

    @Override
    public String getUserType() {
        return "admin";
    }

    @Override
    public int addCourse(Course course) {
        return 0;
    }

    ;


    public int deleteStudent(FileReader database, String mode) {
        Scanner sc = new Scanner(System.in);
        Student student = null;
        // enter ID
        String studentID = "";
        while (true) {
            if (mode.equals("0")) {
                System.out.println("Please enter the Student's ID, or type 'q' to quit. eg. '001' ");
                studentID = sc.next().trim();
            } else {
                studentID = "001";
            }

            if (studentID.equals("q") || studentID.equals("Q")) {
                return 0;
            }

            // Try to find this student
            boolean flag = true;
            for (Student student1 : database.getStudentsInfo()) {
                if (student1.getID().equals(studentID)) {
                    student = student1;
                }
            }
            if (student == null) {
                // if the ID does not exist:
                System.out.println("This ID does not exist. Please try another one.");
                continue;
            } else {
                // if the ID exists:
                // 1. delete the student from all courses he chose
                List<Course> list = new ArrayList<>();
                for (Course course : student.getCourseList()) {
                    list.add(course);
                }
                for (Course course : list) {
                    course.getAllStudents().remove(student);
                }

                // 2. deleted the student from the database
                database.getStudentsInfo().remove(student);
                System.out.println("Successfully deleted student: " + student.getName() + ", " + student.getID());
                return 1;
            }
        }

    }

    public int addNewStudent(FileReader database, String mode) {
        Scanner sc = new Scanner(System.in);

        while (true) {

            // enter ID
            String studentID = "";
            while (true) {
                if (mode.equals("0")) {
                    System.out.println("Please enter the Student's ID, or type 'q' to quit. eg. '001' ");
                    studentID = sc.next().trim();
                } else {
                    studentID = "008";
                }

                if (studentID.equals("q") || studentID.equals("Q")) {
                    return 0;
                }

                // Try to find this prof
                boolean flag = true;
                for (Student student1 : database.getStudentsInfo()) {
                    if (student1.getID().equals(studentID)) {
                        System.out.println("This ID is already used by: " + student1.getName());
                        flag = false;
                    }
                }
                if (!flag) {
                    continue;
                } else {
                    break;
                }
            }


            // enter name
            String studentName = "";
            while (true) {
                if (mode.equals("0")) {
                    System.out.println("Please enter the Student's name, or type 'q' to quit. eg. 'jack' ");
                    studentName = sc.next().trim();
                } else {
                    studentName = "J";
                }

                if (studentName.equals("q") || studentName.equals("Q")) {
                    return 0;
                }

                // enter username
                String studentUserName = "";
                while (true) {
                    if (mode.equals("0")) {
                        System.out.println("Please enter the Student's username, or type 'q' to quit. eg. 'jack' ");
                        studentUserName = sc.next().trim();
                    } else {
                        studentUserName = "JJ";
                    }


                    if (studentUserName.equals("q") || studentUserName.equals("Q")) {
                        return 0;
                    }
                    boolean flag = true;
                    for (Student student1 : database.getStudentsInfo()) {
                        if (student1.getUserName().equals(studentUserName)) {
                            flag = false;
                            System.out.println("This username is already used by: " + student1.getName());
                            break;
                        }
                    }
                    if (!flag) {
                        continue;
                    } else {
                        break;
                    }
                }

                // enter password
                String studentPassword = "";
                if (mode.equals("0")) {
                    System.out.println("Please enter the Student's password, or type 'q' to quit. eg. 'jack123' ");
                    studentPassword = sc.next().trim();
                } else {
                    studentPassword = "00";
                }
                if (studentPassword.equals("q") || studentPassword.equals("Q")) {
                    System.out.println("Goodbye!");
                    return 0;
                }

                // create a new student
                database.addNewStudent(studentID, studentName, studentUserName, studentPassword);
                return 1;
            }
        }

    }


    public int deleteProf(FileReader database, String mode) {
        // find the prof and delete
        Professor prof = null;
        Scanner sc = new Scanner(System.in);

        while (true) {
            // enter ID
            String profID = "";
            while (true) {
                if (mode.equals("0")) {
                    System.out.println("Please enter the professor's ID, or type 'q' to quit. eg. '001' ");
                    profID = sc.next().trim();
                } else {
                    profID = "001";
                }
                if (profID.equals("q") || profID.equals("Q")) {
                    return 0;
                }

                // Try to find this prof
                boolean flag = true;
                for (Professor professor : database.getProfessorsInfo()) {
                    if (professor.getID().equals(profID)) {
                        prof = professor;
                    }
                }
                if (prof == null) {
                    // if the ID does not exist:
                    System.out.println("This ID does not exist. Please try another one.");
                    continue;
                } else {
                    // if the ID exists:
                    // 1. delete the prof's courses
                    List<Course> list = new ArrayList<>();
                    for (Course course : prof.getCourseList()) {
                        list.add(course);
                    }

                    for (Course course : list) {
                        deleteCourse(database, database.getStudentsInfo(), prof.getName(), course);
                    }

                    // 2. delete the prof in database:
                    database.getProfessorsInfo().remove(prof);

                    // return 2 if succeed
                    System.out.println("Successfully deleted: " + prof.getName() + ", " + profID);
                    return 1;
                }
            }
        }

    }


    /**
     *
     */
    public Professor addNewProf(FileReader database, String mode) {
        Professor prof = null;
        Scanner sc = new Scanner(System.in);

        String profID = "";
        while (true) {
            // enter ID
            while (true) {
                if (mode.equals("0")) {
                    System.out.println("Please enter the professor's ID, or type 'q' to quit. eg. '001' ");
                    profID = sc.next().trim();
                } else {
                    profID = "060";
                }
                if (profID.equals("q") || profID.equals("Q")) {
                    return null;
                }
                boolean flag = true;
                for (Professor professor : database.getProfessorsInfo()) {
                    if (professor.getID().equals(profID)) {
                        flag = false;
                        System.out.println("This id is already used by: " + professor.getName());
                        break;
                    }
                }
                if (!flag) {
                    continue;
                } else {
                    break;
                }
            }


            // enter name
            String profName = "";
            if (mode.equals("0")) {
                System.out.println("Please enter the professor's name, or type 'q' to quit. eg. 'olivia' ");
                profName = sc.next().trim();
            } else {
                profName = "Cathy";
            }
            if (profName.equals("q") || profName.equals("Q")) {
                return null;
            }

            // enter username
            String profUserName = "";
            while (true) {
                if (mode.equals("0")) {
                    System.out.println("Please enter the professor's username, or type 'q' to quit. eg. 'olivia1972' ");
                    profUserName = sc.next().trim();
                } else {
                    profUserName = "C";
                }
                if (profUserName.equals("q") || profUserName.equals("Q")) {
                    return null;
                }
                boolean flag = true;
                for (Professor professor : database.getProfessorsInfo()) {
                    if (professor.getUserName().equals(profUserName)) {
                        flag = false;
                        System.out.println("This username is already used by: " + professor.getName());
                        break;
                    }
                }
                if (!flag) {
                    continue;
                } else {
                    break;
                }
            }

            // enter password
            String profPassword = "";
            if (mode.equals("0")) {
                System.out.println("Please enter the professor's password, or type 'q' to quit. eg. '123' ");
                profPassword = sc.next().trim();
            } else {
                profPassword = "22";
            }

            if (profPassword.equals("q") || profPassword.equals("Q")) {
                System.out.println("Goodbye!");
                return null;
            }

            // create a new prof
            if (database.addNewProf(profID, profName, profUserName, profPassword)) {
                // after adding the new prof, give him the new course
                for (Professor professor : database.getProfessorsInfo()) {
                    if (professor.getID().equals(profID)) {
                        prof = professor;
                    }
                }
            }
            break;
        }
        return prof;
    }


    /**
     * Deleted given courses
     * return true if succeed
     * otherwise false
     */
    public boolean deleteCourse(FileReader database, List<Student> students, String profName, Course course) {
        // find the instructor
        Professor professor = null;
        for (Professor prof : database.getProfessorsInfo()) {
            if (prof.getName().equals(profName)) {
                professor = prof;
                break;
            }
        }
        // redraw the courses from students' list
        for (Student std : students) {
            if (std.getCourseList().contains(course)) {
                std.getCourseList().remove(course);
            }
        }

        // remove the course from prof's list
        if (professor.getCourseList().contains(course)) {
            professor.getCourseList().remove(course);
        }


        // remove the course from the database
        if (database.getCourseInfo().contains(course)) {
            database.getCourseInfo().remove(course);
        }

        return true;
    }

}
