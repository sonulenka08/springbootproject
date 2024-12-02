// SignupController.java (course.controller package)
package course.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import course.entity.User;
import course.service.UserServiceImpl;

@Controller
public class SignupController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("signup")
    public String signup(Model model) {
        model.addAttribute("signup", new User());
        return "signup";
    }


    @PostMapping("saveSignup")
    public String saveSignup(@Valid @ModelAttribute("signup") User user, BindingResult bindingResult, RedirectAttributes redirAttrs) {
        if (!bindingResult.hasErrors()) {
            if (user.getPassword().equals(user.getPasswordCheck())) {
                if (userService.findByUsername(user.getUsername()) == null) {
                    userService.encryptPassword(user);
                    user.setRole("USER");
                    userService.saveUser(user);
                    redirAttrs.addFlashAttribute("message", "User registered successfully!"); // Use RedirectAttributes for flash messages
                    return "redirect:/login"; 
                } else {
                    bindingResult.rejectValue("username", "error.userexists", "Username already exists");
                    return "signup"; // Return to form to display the error

                }
            } else {
                bindingResult.rejectValue("passwordCheck", "error.pwdmatch", "Passwords do not match");
                return "signup"; // Return to form to display error

            }
        }
        return "signup"; // Return to form to display errors

    }
}