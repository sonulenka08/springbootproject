// CourseServiceImpl.java
package course.service;

import course.entity.Course;
import course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl {

    private final CourseRepository repository;

    @Autowired
    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }

    public Course findCourseById(Long courseId) {
        Optional<Course> courseOptional = repository.findById(courseId);
        return courseOptional.orElse(null);
    }

    public List<Course> findAllByOrderByNameAsc() {
        return repository.findAllByOrderByNameAsc();
    }

    public Course saveCourse(Course course) {
        return repository.save(course);
    }

    public void updateCourse(Course course) { // Explicit update if needed
        repository.save(course);
    }

    public void deleteCourseById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllCourses() {  // Use with caution
        repository.deleteAll();
    }


    public List<Course> findAllCourses() {
        return (List<Course>) repository.findAll(); // No cast needed
    }
}