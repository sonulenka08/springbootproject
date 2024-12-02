// Course.java (course.entity package)
package course.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseid")
    private final Long courseid; // final added

    @Size(min = 3, message = "Course name is invalid") // slightly clearer message
    @Column(name = "coursename")
    private String name;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;

    // Needed for JPA/Hibernate.  Make it protected instead of public.
    public Course() {
        this.courseid = null; // Initialize in no-arg constructor for consistency
    }


    public Course(String name) {
        this.courseid = null;  // Set to null; JPA will handle it
        this.name = name;
    }


    public Long getCourseid() {
        return courseid;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}