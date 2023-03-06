package people;

import file.Course;
import file.FileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfessorTest {
    Professor prof;
    FileReader db;

    @BeforeEach
    void setUp() {
        this.db = new FileReader();
        this.prof = new Professor("001", "Clayton Greenberg", "Greenberg", "password590");
        this.db.setUp("studentInfo.txt", "profInfo.txt", "adminInfo.txt"
                ,"courseInfo.txt" );
    }

    //test user type
    @Test
    void getUserType() {
        assertEquals("professor",prof.getUserType());

    }

    //test add course
    @Test
    void addCourse() throws ParseException {
        Course c1 = this.db.getCourseInfo().get(0);
        assertEquals(1,this.prof.addCourse(c1));
        //test add course again
        assertEquals(0,this.prof.addCourse(c1));

    }


}

