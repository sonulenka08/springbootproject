// StudentController.java (course.controller package)
package course.controller;

import course.entity.Student;
import course.entity.Course;
import course.service.StudentServiceImpl;
import course.service.CourseServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@Controller
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private CourseServiceImpl courseService;

    @GetMapping("students")
    public String index(Model model) {
        List<Student> students = (List<Student>) studentService.findAllByOrderByFirstNameAsc();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("student/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("title", "Add Student");
        return "studentForm";
    }

    @GetMapping("student/edit/{id}")
    public String editStudent(@PathVariable("id") Long studentId, Model model) {
        model.addAttribute("student", studentService.findStudentById(studentId));
        model.addAttribute("title", "Edit Student");
        return "studentForm";
    }

    @GetMapping("student/{id}")
    public String showStudent(@PathVariable("id") Long studentId, Model model) {
        model.addAttribute("student", studentService.findStudentById(studentId));
        model.addAttribute("title", "Show Student");
        return "studentShow";
    }

    @PostMapping("saveStudent")
    public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, @RequestParam("id") Long studentId, Model model) {
        if (!bindingResult.hasErrors()) {
            studentService.saveStudent(student);
        } else {
            String title = (studentId == null) ? "Add Student" : "Edit Student";
            model.addAttribute("title", title);
            return "studentForm"; // Return to form to show errors
        }
        return "redirect:/students";
    }


    @GetMapping("student/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long studentId, Model model) {
        studentService.deleteStudentById(studentId);
        return "redirect:/students";
    }

    @GetMapping("addStudentCourse/{id}")
    public String addStudentCourse(@PathVariable("id") Long studentId, Model model) {
        model.addAttribute("student", studentService.findStudentById(studentId));
        model.addAttribute("courses", courseService.findAllCourses());
        return "addStudentCourse";
    }


    @GetMapping("student/{id}/courses")
    public String studentsAddCourse(@RequestParam(value = "action", required = true) String action, @PathVariable Long id, @RequestParam Long courseId, Model model) {
        Student student = studentService.findStudentById(id);
        Course course = courseService.findCourseById(courseId);

        if (!student.hasCourse(course)) {
            student.getCourses().add(course);
        }

        studentService.saveStudent(student);  // It's crucial to save the student after modifying the courses

        // Redirect back to the students page or wherever makes sense in your application flow
        return "redirect:/students";
    }



    @GetMapping("getstudents")
    public @ResponseBody List<Student> getStudents() {
        return (List<Student>) studentService.findAllStudents();
    }
}