package people;

import file.FileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserTest {

    Student stu;
    Professor prof;
    Admin ad;
    FileReader file;

    @BeforeEach
    void setUp() {



        stu = new Student("001", "Cathy", "Cathy001", "1234567");
        prof = new Professor("002", "Jack", "Jack002", "000");
        ad = new Admin("003","Lucy", "Lucy003", "666");
        file = new FileReader();
    }

    @Test
        //test user name
    void TestgetUserName() {
        assertEquals("Cathy001", stu.getUserName());
        assertNotEquals("Jim",prof.getUserName());

    }

    @Test
        //test password
    void TestgetPassWord() {
        assertNotEquals("001", stu.getPassWord());
        assertEquals("666", ad.getPassWord());
    }

    @Test
        //test id
    void TestgetID() {
        assertEquals("003",ad.getID());
        assertNotEquals("222",stu.getID());
    }

    @Test
        //test name
    void TestgetName() {
        assertNotEquals("W",stu.getName());
        assertEquals("Jack",prof.getName());
    }

}