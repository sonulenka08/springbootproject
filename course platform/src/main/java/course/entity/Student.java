// Student.java (course.entity package)
package course.entity;

import org.hibernate.validator.constraints.NotEmpty; // Make sure this is available if using older Spring Boot

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, message = "Firstname is invalid")
    @Column(name = "firstname") // Added column name to be explicit
    private String firstName;

    @Size(min = 3, message = "Lastname is invalid")
    @Column(name = "lastname")  // Added column name to be explicit
    private String lastName;

    @Size(min = 2, message = "Department is invalid")
    private String department;

    @NotEmpty // Might need to remove if using Spring Boot 3.x and having dependency issues
    @Email
    private String email;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "student_course",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "courseid")})
    private Set<Course> courses = new HashSet<>(0); // Use diamond operator


    // Needed by JPA. Make protected.
    public Student() {}


    public Student(String firstName, String lastName, String department, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }



    public boolean hasCourse(Course course) {
        for (Course studentCourse: getCourses()) {
            if (studentCourse.getCourseid().equals(course.getCourseid())) { // Use .equals() to compare Long objects
                return true;
            }
        }
        return false;

    }
}