// StudentServiceImpl.java
package course.service;

import course.entity.Student;
import course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl {

    private final StudentRepository repository;

    @Autowired
    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    public Student findStudentById(Long id) {
       Optional<Student> studentOptional = repository.findById(id);
       return studentOptional.orElse(null);
    }

    public List<Student> findAllByOrderByFirstNameAsc() {
        return repository.findAllByOrderByFirstNameAsc();
    }

    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    public void updateStudent(Student student) { // Explicit update method
        repository.save(student);
    }

    public void deleteStudentById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllStudents() {  // Use with caution!
        repository.deleteAll();
    }

    public List<Student> findAllStudents() {
        return (List<Student>) repository.findAll(); // No cast needed
    }
}