package course.controller;

import course.entity.Course;
import course.service.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;

    @GetMapping("/courses") // Updated to @GetMapping
    public String index(Model model) {
        List<Course> courses = courseService.findAllByOrderByNameAsc(); // No need to cast
        model.addAttribute("courses", courses);
        return "courses"; // Assumes you have a "courses.html" Thymeleaf template
    }

    @GetMapping("/course/new") // Updated to @GetMapping
    public String newCourse(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("title", "Add Course");
        return "courseForm"; // Assumes you have a "courseForm.html" Thymeleaf template
    }

    @GetMapping("/course/edit/{id}") // Updated to @GetMapping
    public String editCourse(@PathVariable("id") Long courseId, Model model) {
        Course course = courseService.findCourseById(courseId);
        if (course == null) {
            return "redirect:/courses"; // Or handle the case where the course is not found
        }
        model.addAttribute("course", course);
        model.addAttribute("title", "Edit Course");
        return "courseForm";
    }


    @GetMapping("/course/{id}") // Updated to @GetMapping
    public String showCourse(@PathVariable("id") Long courseId, Model model) {
        Course course = courseService.findCourseById(courseId);        
        if (course == null) {
            return "redirect:/courses"; // Handle course not found
        }
        model.addAttribute("course", course);
        model.addAttribute("title", "Show Course");
        return "courseShow";  // Assumes "courseShow.html" exists
    }

    @PostMapping("/saveCourse") // Updated to @PostMapping.  Important!
    public String saveCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult, @RequestParam(value = "courseid", required = false) Long courseId, Model model) { // Added @RequestParam

        if (bindingResult.hasErrors()) {
            String title = (courseId == null) ? "Add Course" : "Edit Course";
            model.addAttribute("title", title);
            return "courseForm"; // Return to the form with errors
        }


        courseService.saveCourse(course);
        return "redirect:/courses";  // Redirect after successful save
    }

    @GetMapping("/course/delete/{id}") // Updated to @GetMapping
    public String deleteCourse(@PathVariable("id") Long courseId) {
        courseService.deleteCourseById(courseId);
        return "redirect:/courses";
    }
}