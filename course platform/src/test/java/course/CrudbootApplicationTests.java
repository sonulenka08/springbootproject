package course;

import course.entity.Student;
import course.entity.User;
import course.repository.StudentRepository;
import course.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CrudbootApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private Environment environment;


    @Autowired
    private DataSource dataSource;   // Add this if you are using DataSource



    @Test
    void addUser() {
        User user = new User("testuser", "testuser", "USER");
        assertNull(user.getId());
        userRepository.save(user);
        assertNotNull(user.getId());
    }

    @Test
    void addStudent() {
        Student student = new Student("Test", "Student", "IT", "test@test.com");
        studentRepository.save(student);
        Optional<Student> findStudent = studentRepository.findById(student.getId());
        assertTrue(findStudent.isPresent());
    }

    @Test
    void showConfig() {
        System.out.println("Active Profiles: " + Arrays.toString(environment.getActiveProfiles()));
        System.out.println("Datasource URL: " + environment.getProperty("spring.datasource.url"));
        System.out.println("Database Platform: " + environment.getProperty("spring.jpa.database-platform"));
        System.out.println("spring.jpa.hibernate.ddl-auto: " + environment.getProperty("spring.jpa.hibernate.ddl-auto"));
       //Add other properties you want to check

        // This block is only needed if you are using DataSource in your tests
        try {
            if (dataSource != null) {
                System.out.println("DataSource URL (from DataSource object): " + dataSource.getConnection().getMetaData().getURL());
                // Print other DataSource properties as needed
            } else {
                System.out.println("DataSource is null"); // This helps if injection fails
            }
        } catch (SQLException e) {
            System.err.println("Error getting DataSource connection: " + e.getMessage()); // Clearer error message
        }
    }
}