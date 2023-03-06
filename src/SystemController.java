import file.Course;
import file.FileReader;
import people.Admin;
import people.Professor;
import people.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SystemController {
    /**
     * This is the launcher of the system
     * Made by Ze Sheng
     */

    public static void main(String[] args) throws ParseException {
        // Initialize the database:
        FileReader database = new FileReader();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        database.setUp("studentInfo.txt", "profInfo.txt", "adminInfo.txt"
                , "courseInfo.txt");

        boolean again = true;

        String userType = "";

        String number;


        while (true) {
            // initialize potential uses
            Student studentUser = null;

            Professor professorUser = null;

            Admin adminUser = null;

            // initialize input
            Scanner sc = new Scanner(System.in);

            System.out.println("---------------------------------------");
            String welcomeWord = "Welcome to Student Management System (SMS)";
            System.out.println(welcomeWord);
            System.out.println("---------------------------------------");
            System.out.println("1 -- Login as a student");
            System.out.println("2 -- Login as a professor");
            System.out.println("3 -- Login as an admin");
            System.out.println("4 -- Quit the system");


            // input the number:
            while (true) {
                System.out.println("Please enter your option, eg. 1");
                number = sc.next().trim();
                if (number.equals("4")) {
                    System.out.println("Goodbye!");
                    return;
                }
                if (!number.equals("1") && !number.equals("2") && !number.equals("3")) {
                    System.out.println("Invalid input!");
                } else {

                    break;
                }
            }

            while (true) {
                // input the userName
                if (number.equals("1")) {
                    System.out.println("Login as a student:");
                }
                if (number.equals("2")) {
                    System.out.println("Login as a professor:");
                }
                if (number.equals("3")) {
                    System.out.println("Login as an admin:");
                }

                System.out.println("Please enter your username, or type 'q' to quit\n");
                String userName = sc.next();

                // DEBUG ONLY!!
                // userName = "Greenberg";

                if (userName.equals("q")) {
                    System.out.println("Goodbye!");
                    return;
                }
                // input the password
                System.out.println("Please enter your passWord, or type 'q' to quit\n");
                String passWord = sc.next();

                // DEBUG ONLY!!
                // passWord = "password590";

                if (userName.equals("q")) {
                    System.out.println("Goodbye!");
                    return;
                }

                if (number.equals("1")) {
                    studentUser = database.studentLogin(userName, passWord);
                } else if (number.equals("2")) {
                    professorUser = database.professorLogin(userName, passWord);
                } else if (number.equals("3")) {
                    adminUser = database.adminLogin(userName, passWord);
                }


                // try the userName and passWord
                if (studentUser == null && professorUser == null && adminUser == null) {
                    System.out.println("Your username or password is incorrect, please try again");
                    System.out.println("---------------------------------------\n");
                } else {
                    break;
                }
            }


            /**
             * Student Service
             */
            if (studentUser != null) {
                // transaction part:
                while (true) {
                    System.out.println("---------------------------------------");
                    System.out.println("Welcome " + studentUser.getName());
                    System.out.println("---------------------------------------");
                    String input = "";


                    System.out.println("1 -- View all courses");
                    System.out.println("2 -- Add courses to the list");
                    System.out.println("3 -- View enrolled course");
                    System.out.println("4 -- Drop courses in your list");
                    System.out.println("5 -- View grades");
                    System.out.println("6 -- Return previous menus");
                    System.out.println("Or input q to quit");
                    number = sc.next();


                    if (number.equals("6")) {
                        break;
                    }

                    if (number.equals("q") || number.equals("Q")) {
                        System.out.println("Goodbye!");
                        return;
                    }

                    // Service 1: view all courses
                    if (number.equals("1")) {
                        System.out.println("---------------------------------------");
                        System.out.println("All courses:");
                        database.showAllCourses();
                        System.out.println("---------------------------------------");

                        while (true) {
                            System.out.println("Enter 'q' to return to the previous menu or enter 'r' to quit");
                            input = sc.next();
                            if ((!input.equals("q") && !input.equals("Q")) && (!input.equals("r") && !input.equals("R"))) {
                                System.out.println("Invalid input!");
                            } else {
                                break;
                            }
                        }
                    }

                    // Service 2: Add courses to the list
                    if (number.equals("2")) {
                        while (true) {
                            System.out.println("---------------------------------------");
                            System.out.println("Please select the course ID you want to add to your list, eg. 'CIT590'. Or" +
                                    " enter 'q' to return to the previous menu.");
                            input = sc.next().trim().toUpperCase();
                            Course course = database.findCourse(input);

                            // corner case: if the user wants to quit
                            if (input.equals("q") || input.equals("Q")) {
                                break;
                            }

                            // corner case: if the course does not exist:
                            if (course == null) {
                                System.out.println("Sorry, this course dose not exist!");
                                continue;
                            }
                            int response = studentUser.addCourse(course);


                            // case 1: the course is already taken
                            if (response == 0) {
                                System.out.println("You have already taken this course. Please add another course again.");
                                continue;
                            }

                            // case 2: the course is already in the list
                            if (response == 1) {
                                System.out.println("This course is already in your list! Please add another course again.");
                                continue;
                            }

                            // case 3: if this course is full
                            if (response == 2) {
                                System.out.println("This course is already full! Please add another course again.");
                                continue;
                            }

                            // case 4: if there is a conflict on time
                            if (response == 3) {
                                System.out.println("Please add another course again!");
                                continue;
                            }

                            // everything is ok, add this course
                            if (response == 4) {
                                System.out.println("Course added successfully");
                                continue;
                            }
                        }
                    }

                    // Service 3: View enrolled course
                    if (number.equals("3")) {
                        System.out.println("---------------------------------------");
                        System.out.println("Your courses are:");
                        studentUser.viewSelectedCourses();
                        System.out.println("---------------------------------------");
                        while (true) {
                            System.out.println("Enter 'q' to return to the previous menu or enter 'r' to quit");
                            input = sc.next();
                            if ((!input.equals("q") && !input.equals("Q")) && (!input.equals("r") && !input.equals("R"))) {
                                System.out.println("Invalid input!");
                            } else {
                                break;
                            }
                        }
                    }

                    // Service 4: Drop courses in your list
                    if (number.equals("4")) {
                        while (true) {
                            System.out.println("---------------------------------------");
                            System.out.println("Your courses are:");
                            studentUser.viewSelectedCourses();
                            System.out.println("---------------------------------------");
                            System.out.println("Please select the course ID you want to drop from your list, eg. 'CIT590'. Or" +
                                    " enter 'q' to return to the previous menu.");
                            input = sc.next().trim().toUpperCase();

                            // corner case: if the user wants to quit
                            if (input.equals("q") || input.equals("Q")) {
                                break;
                            }

                            // if the course is dropped
                            if (studentUser.dropCourse(input)) {
                                System.out.println("Course dropped successfully!");
                            } else { // if the course is not in the list
                                System.out.println("The course is not in your schedule. Please select another course.");
                            }

                        }
                    }

                    // Service 5: View grades
                    if (number.equals("5")) {
                        while (true) {
                            // corner case: if there is no course taken
                            if (studentUser.getGrades().isEmpty()) {
                                System.out.println("---------------------------------------");
                                System.out.println("There is no course taken yet.");
                                System.out.println("Enter 'q' to return to the previous menu or enter 'r' to quit");
                                input = sc.next();
                                if ((!input.equals("q") && !input.equals("Q")) && (!input.equals("r") && !input.equals("R"))) {
                                    System.out.println("Invalid input!");
                                    continue;
                                } else {
                                    break;
                                }
                            }

                            System.out.println("---------------------------------------");
                            System.out.println("Here are the courses you already taken, with your grade in a letter format:");

                            // print all grades
                            for (Map.Entry<String, String> entry : studentUser.getGrades().entrySet()) {
                                System.out.println("Grade of " + entry.getKey() + " " + database.findCourse(entry.getKey()).getCourseName()
                                        + " : " + entry.getValue());
                            }

                            while (true) {
                                System.out.println("Enter 'q' to return to the previous menu or enter 'r' to quit");
                                input = sc.next();
                                if ((!input.equals("q") && !input.equals("Q")) && (!input.equals("r") && !input.equals("R"))) {
                                    System.out.println("Invalid input!");
                                } else {
                                    break;
                                }
                            }
                            break;
                        }
                    } else {
                        continue;
                    }

                    // condition: return to the previous menu or quit
                    if (input.equals("q") || input.equals("Q")) {
                        continue;
                    } else {
                        System.out.println("Goodbye!");
                        return;
                    }
                }
            }


            /**
             * Professor Service
             */
            if (professorUser != null) {
                // transaction part:
                while (true) {
                    System.out.println("---------------------------------------");
                    System.out.println("Welcome " + professorUser.getName());
                    System.out.println("---------------------------------------");
                    String input = "";


                    System.out.println("1 -- View given courses");
                    System.out.println("2 -- View student list of the given course");
                    System.out.println("3 -- Return previous menus");
                    System.out.println("Or input q to quit");
                    number = sc.next();

                    if (number.equals("3")) {
                        break;
                    }

                    if (number.equals("q")) {
                        System.out.println("Goodbye!");
                        return;
                    }

                    // Service 1: View given courses
                    if (number.equals("1")) {
                        while (true) {
                            // corner case: if there is no course taken
                            if (professorUser.getCourseList().isEmpty()) {
                                System.out.println("---------------------------------------");
                                System.out.println("There is no course taken yet.");
                                System.out.println("Enter 'q' to return to the previous menu or enter 'r' to quit");
                                input = sc.next();
                                if ((!input.equals("q") && !input.equals("Q")) && (!input.equals("r") && !input.equals("R"))) {
                                    System.out.println("Invalid input!");
                                    continue;
                                } else if (input.equals("r") || input.equals("R")) {
                                    System.out.println("Goodbye");
                                    return;
                                } else {
                                    break;
                                }
                            }

                            System.out.println("-----------------The Course List------------------");
                            professorUser.showAllCourses();
                            System.out.println("--------------------------------------------------");
                            while (true) {
                                System.out.println("Enter 'q' to return to the previous menu or enter 'r' to quit");
                                input = sc.next();
                                if ((!input.equals("q") && !input.equals("Q")) && (!input.equals("r") && !input.equals("R"))) {
                                    System.out.println("Invalid input!");
                                } else if (input.equals("r") || input.equals("R")) {
                                    System.out.println("Goodbye");
                                    return;
                                } else {
                                    break;
                                }
                            }
                            break;
                        }
                    }

                    // Service 2: View student list of the given course
                    if (number.equals("2")) {
                        while (true) {
                            System.out.println("---------------------------------------");
                            System.out.println("Your courses are:");
                            System.out.println("-----------------The Course List------------------");
                            professorUser.showAllCourses();
                            System.out.println("--------------------------------------------------");
                            System.out.println("Please enter the course ID to see the student list, eg. 'CIT590'. Or" +
                                    " enter 'q' to return to the previous menu.");
                            input = sc.next().trim().toUpperCase();

                            // corner case: if the user wants to quit
                            if (input.equals("q") || input.equals("Q")) {
                                break;
                            }

                            // show the list:
                            Course course = database.findCourse(input);
                            System.out.println("Student in your course " + course.getCourseNumber() + " " + course.getCourseName());
                            course.showStudentList();

                            while (true) {
                                System.out.println("Enter 'q' to return to the previous menu or enter 'r' to quit");
                                input = sc.next();
                                if ((!input.equals("q") && !input.equals("Q")) && (!input.equals("r") && !input.equals("R"))) {
                                    System.out.println("Invalid input!");
                                } else if (input.equals("r") || input.equals("R")) {
                                    System.out.println("Goodbye");
                                    return;
                                } else {
                                    break;
                                }
                            }
                            break;
                        }
                    }

                }
            }


            /**
             * Admin Service
             */
            if (adminUser != null) {
                // transaction part:
                while (true) {
                    System.out.println("---------------------------------------");
                    System.out.println("Welcome " + adminUser.getName());
                    System.out.println("---------------------------------------");
                    String input = "";

                    Professor prof = null;

                    /**
                     * Admin Service
                     */
                    System.out.println("1 -- View all courses");
                    System.out.println("2 -- Add new courses");
                    System.out.println("3 -- Delete courses");
                    System.out.println("4 -- Add new professor");
                    System.out.println("5 -- Delete professor");
                    System.out.println("6 -- Add new student");
                    System.out.println("7 -- Delete student");
                    System.out.println("8 -- Return to previews menu");
                    System.out.println("Or input q to quit");
                    number = sc.next();

                    if (number.equals("8")) {
                        break;
                    }

                    if (number.equals("q")) {
                        System.out.println("Goodbye!");
                        return;
                    }

                    // Service 1: View all courses
                    if (number.equals("1")) {
                        System.out.println("---------------------------------------");
                        System.out.println("All courses:");
                        database.showAllCourses();
                        System.out.println("---------------------------------------");

                        while (true) {
                            System.out.println("Enter 'q' to return to the previous menu or enter 'r' to quit");
                            input = sc.next();
                            if (input.equals("q") || input.equals("Q")) {
                                break;
                            } else if (input.equals("r") || input.equals("R") ) {
                                System.out.println("Goodbye!");
                                return;
                            } else {
                                System.out.println("Invalid input!");
                            }
                        }
                    }

                    // Service 2: Add new courses
                    if (number.equals("2")) {
                        // initialize new course:
                        Course newCourse = null;

                        // Section 1: create course
                        // input ID
                        String courseNumber = "";
                        while (true) {
                            System.out.println("Please enter the course ID, or type 'q' to end. eg. 'CIT789' ");
                            courseNumber = sc.next().trim().toUpperCase();
                            if (courseNumber.equals("q") || courseNumber.equals("Q")) {
                                System.out.println("Goodbye!");
                                return;
                            }
                            // if the id is already in the list
                            boolean flag = false;
                            for (Course course : database.getCourseInfo()) {
                                if (course.getCourseNumber().equals(courseNumber)) {
                                    System.out.println("This id is already used, please try another");
                                    flag = true;
                                }
                            }
                            if (!flag) {
                                break;
                            }
                        }

                        // input name
                        System.out.println("Please enter the course name, or type 'q' to end. eg. 'Python'");
                        String courseName = sc.next();
                        if (courseName.equals("q") || courseName.equals("Q")) {
                            System.out.println("Goodbye!");
                            return;
                        }

                        // input start time
                        String courseStartTime = "";
                        while (true) {
                            System.out.println("Please enter the start time, or type 'q' to end. eg. '13:00'");
                            courseStartTime = sc.next().trim();
                            if (courseStartTime.equals("q") || courseStartTime.equals("Q")) {
                                System.out.println("Goodbye!");
                                return;
                            }
                            try {
                                df.parse(courseStartTime);
                                break;
                            } catch (ParseException p) {
                                System.out.println("Invalid input!!");
                                continue;
                            }
                        }


                        // input end time
                        String courseEndTime = "";
                        while (true) {
                            System.out.println("Please enter the end time, or type 'q' to end. eg. '13:00'");
                            courseEndTime = sc.next().trim();
                            if (courseEndTime.equals("q") || courseEndTime.equals("Q")) {
                                System.out.println("Goodbye!");
                                return;
                            }
                            try {
                                df.parse(courseEndTime);
                            } catch (ParseException p) {
                                System.out.println("Invalid input!!");
                                continue;
                            } finally {
                                if (!df.parse(courseEndTime).after(df.parse(courseStartTime))) {
                                    System.out.println("The end time can only be after the start time!");
                                    continue;
                                }
                                break;
                            }
                        }


                        // input dates
                        String courseDays = "";
                        while (true) {
                            System.out.println("Please enter the course date, or type 'q' to end. eg. 'MW' or 'TR' ");
                            courseDays = sc.next().trim().toUpperCase();
                            Set<Character> workDays = new HashSet<>();
                            workDays.add('M'); workDays.add('T'); workDays.add('W'); workDays.add('R'); workDays.add('F');
                            if (courseDays.equals("q") || courseDays.equals("Q")) {
                                System.out.println("Goodbye!");
                                return;
                            }
                            boolean flag = true;
                            for (char chr : courseDays.toCharArray()) {
                                if (!workDays.contains(chr)) {
                                    System.out.println("Invalid input!");
                                    flag = false;
                                    break;
                                }
                            }
                            if (!flag) {
                                continue;
                            } else {
                                break;
                            }
                        }


                        // input capacity
                        System.out.println("Please enter the course capacity, or type 'q' to end eg. '120'");
                        String courseCapacity = sc.next().trim();
                        if (courseCapacity.equals("q") || courseCapacity.equals("Q")) {
                            System.out.println("Goodbye!");
                            return;
                        }

                        // change string into an int
                        int capacity = 0;
                        char[] array = courseCapacity.toCharArray();
                        int length = array.length;
                        for (char chr : array) {
                            capacity += (chr - '0') * Math.pow(10, length - 1);
                            length--;
                        }

                        // input prof ID
                        System.out.println("Please enter the lecturer's ID, or type 'q' to end. eg. '001'");
                        String lecturerID = sc.next().trim();
                        if (lecturerID.equals("q") || lecturerID.equals("Q")) {
                            System.out.println("Goodbye!");
                            return;
                        }

                        // Section 2: find professor

                        // case 1: if this professor exists:
                        for (Professor professor : database.getProfessorsInfo()) {
                            if (professor.getID().equals(lecturerID)) {
                                prof = professor;
                            }
                        }
                        if (prof == null) {
                            // case 2: if this professor does not exist, create a new professor
                            System.out.println("The professor is not in the system, please add this professor first");
                            prof = adminUser.addNewProf(database, "0");
                            if (prof == null) {
                                continue;
                            }
                        }

                        String lecturerName = prof.getName();
                        String description = courseNumber + "; " + courseName + "; " + lecturerName + "; "
                                + courseDays + ";" + courseStartTime + "; " + courseEndTime + "; " + courseCapacity;
                        newCourse = new Course(description, courseNumber, courseName, lecturerName, courseDays,
                                courseStartTime, courseEndTime, capacity);

                        // Now we have a prof and a course, we need to add the course to the prof's list
                        if (!prof.getCourseList().isEmpty()) {
                            // case 1: if there is a time conflict between two courses
                            Date newStartTime = df.parse(newCourse.getStartTime());
                            Date newEndTime = df.parse(newCourse.getEndTime());
                            Set<Character> days = new HashSet<>();

                            for (char chr : newCourse.getDays().toCharArray()) {
                                days.add(chr);
                            }

                            boolean conflict = false;
                            // check the time of every course
                            for (Course course1 : prof.getCourseList()) {
                                for (char chr : newCourse.getDays().toCharArray()) {
                                    if (days.contains(chr)) {
                                        Date startTime = df.parse(course1.getStartTime());
                                        Date endTime = df.parse(course1.getEndTime());
                                        boolean noStartConflict = newStartTime.before(startTime) && !newEndTime.after(startTime);
                                        boolean noEndConflict = !newStartTime.before(endTime) && newEndTime.after(endTime);
                                        if (!(noEndConflict || noStartConflict)) {
                                            System.out.println("The course you selected has time conflict with " + course1.getCourseNumber()
                                                    + " " + course1.getCourseName() + ", " + course1.getStartTime() + "-" +
                                                    course1.getEndTime() + " on " + course1.getDays());
                                            conflict = true;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (conflict) {
                                // when a time conflict happens
                                System.out.println("Unable to add new course: " + newCourse.getDescription());
                                adminUser.deleteCourse(database, database.getStudentsInfo(), prof.getName(), newCourse);
                                continue;
                            }
                        }

                        // case 2, 3 if it passes the check or the list is empty, just add it
                        prof.addCourse(newCourse);
                        System.out.println("Successfully added the course: " + newCourse.getDescription());
                        database.getCourseInfo().add(newCourse);
                        continue;
                    }


                    // Service 3: Delete courses
                    if (number.equals("3")) {
                        Course course = null;
                        boolean back = false;
                        while (true) {
                            System.out.println("Please input the course ID you want to delete or enter 'q' to return");
                            String courseID = sc.next().trim().toUpperCase();
                            if (courseID.equals("q") || courseID.equals("Q")) {
                                back = true;
                                break;
                            }
                            course = database.findCourse(courseID);
                            if (course == null) {
                                // if the course does not exist, re-input it
                                System.out.println("This course does not exist. Please input a valid course ID");
                            } else {
                                break;
                            }
                        }
                        if (back) {
                            continue;
                        }
                        if (adminUser.deleteCourse(database, database.getStudentsInfo(), course.getInstructor(), course)) {
                            System.out.println("Successfully deleted: " + course.getDescription());
                        }
                        while (true) {
                            System.out.println("Enter 'q' to return to the previous menu or enter 'r' to quit");
                            input = sc.next();
                            if ((!input.equals("q") && !input.equals("Q")) && (!input.equals("r") && !input.equals("R"))) {
                                System.out.println("Invalid input!");
                            } else if (input.equals("r") || input.equals("R") ) {
                                System.out.println("Goodbye!");
                                return;
                            } else {
                                break;
                            }
                        }
                    }

                    // Service 4: Add new professor
                    if (number.equals("4")) {
                        Professor newProf = adminUser.addNewProf(database, "0");
                        if (newProf == null) {
                            continue;
                        }

                    }

                    // Service 5: Delete professor
                    if (number.equals("5")) {
                        int response = adminUser.deleteProf(database, "0");
                        if (response == 0) {
                            continue;
                        }
                    }

                    // Service 6: Add new student
                    if (number.equals("6")) {
                        int response = adminUser.addNewStudent(database, "0");
                        if (response == 0) {
                            continue;
                        }
                    }

                    // Service 7: Delete student
                    if (number.equals("7")) {
                        int response = adminUser.deleteStudent(database, "0");
                    }

                    // Service 8: Return to previews menu
                    if (number.equals("8")) {
                        continue;
                    }


                }
            }



            // condition: back to the previous menus without any service to choose:
            boolean studentBack = (number.equals("6") && studentUser != null);
            boolean profBack = (number.equals("3") && professorUser != null);
            boolean adminBack = (number.equals("8") && adminUser != null);
            if (studentBack || profBack || adminBack) {
                continue;
            }

        }
    }
}
