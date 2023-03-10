# Student Management System

## Made by Ze Sheng



## All Source Files:
### Controller File
* SystemController.java

### User group Files
* User.java
* Professor.java
* Admin.java
* Student.java

### Course File
* Course.java

### Helper File
* FileReader.java

### Test Files
* AdminTest.java
* ProfessorTest.java
* StudentTest.java
* UserTest.java
* FileReaderTest.java


## Progress
* ✓ - Finished
* O - In progess
* ? - Have problem
* blank - Not started

Section | State
:----: |:----:
Data Cleaning |   ✓
Database for Course| ✓
Database for Profs | ✓
Database for Students | ✓
Database for Admins | ✓
Login session| ✓
Student functions | ✓
Prof functions | ✓
Admin functions | ✓
Corner cases check | ✓


## Overview of work accomplished
* This is an Upenn student management system for all CIS and CIT courses. It will be served for 3 different user groups: Students, Professors and Admins.
* After running the SMS, a login prompt appears like:
* 
    ````
    ---------------------------------------
    Welcome to Student Management System (SMS)
    ---------------------------------------
    1 -- Login as a student
    2 -- Login as a professor
    3 -- Login as an admin
    4 -- Quit the system
    Please enter your option, eg. 1
    ````
* After getting into the system, the functions are different for each group.
### Student functions
* ````
    ---------------------------------------
    Welcome StudentName1
    ---------------------------------------
    1 -- View all courses
    2 -- Add courses to the list
    3 -- View enrolled course
    4 -- Drop courses in your list
    5 -- View grades
    6 -- Return previous menus
    Or input q to quit
    ````
### Professor functions
* ````
    ---------------------------------------
    Welcome Clayton Greenberg
    ---------------------------------------
    1 -- View given courses
    2 -- View student list of the given course
    3 -- Return previous menus
    Or input q to quit
    ````

### Administer functions
* ````
    ---------------------------------------
    Welcome admin
    ---------------------------------------
    1 -- View all courses
    2 -- Add new courses
    3 -- Delete courses
    4 -- Add new professor
    5 -- Delete professor
    6 -- Add new student
    7 -- Delete student
    8 -- Return to previews menu
    Or input q to quit
    ````


## Challenges
* There are mountains of corner cases in the user input, such as non-datetime input when searching for course days and invalid course number when creating a course. In this project, I tried my best to check all corner cases and it works well.
* When a professor retires, he/she needs to be deleted from the system. In this case, the course he/she would teach will be also deleted in the system. I fixed this problem and it works very well.



