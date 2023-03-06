package people;

import file.Course;
import file.FileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class StudentTest {

    FileReader db;


    @BeforeEach
    void setUp() throws Exception {
        // initialize the database
        this.db = new FileReader();

        // setup the database
        this.db.setUp("studentInfo.txt", "profInfo.txt", "adminInfo.txt"
                ,"courseInfo.txt" );
    }

    @Test
        //test add course
    void TestaddCourse() throws ParseException {
        Course c1 = this.db.getCourseInfo().get(0);
        Student s1 = this.db.getStudentsInfo().get(0);
        assertEquals(4, s1.addCourse(c1));
        assertEquals(1,s1.addCourse(c1));
        assertNotEquals(3,s1.addCourse(c1));
    }



    //test select course
    void TestviewSelectedCourses() {
        Course c1 = this.db.getCourseInfo().get(0);
        Student s1 = this.db.getStudentsInfo().get(0);

        s1.viewSelectedCourses();
    }
    @Test
        //test drop course
    void TestdropCourse() throws ParseException {

        Course c1 = this.db.getCourseInfo().get(0);
        Student s1 = this.db.getStudentsInfo().get(0);
        int i1 = s1.getCourseList().size();
        s1.addCourse(c1);

        assertTrue(s1.dropCourse(c1.getCourseNumber()));
        assertEquals(i1, s1.getCourseList().size());
        assertFalse(s1.dropCourse(c1.getCourseNumber()));
        assertNotEquals(i1 - 1, s1.getCourseList().size());
    }

}
