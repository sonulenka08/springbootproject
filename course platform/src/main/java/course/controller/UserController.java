// UserController.java (course.controller package)
package course.controller;

import course.entity.User;
import course.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam(value = "id", required = false) Long userId, Model model) {
        if (!bindingResult.hasErrors()) {
            if (user.getPassword() != null) {
                userService.encryptPassword(user);
            }
            if (userId == null) {
                if (userService.findByUsername(user.getUsername()) == null) {
                    userService.saveUser(user);
                } else {
                    bindingResult.rejectValue("username", "error.userexists", "Username already exists");
                    model.addAttribute("user", user); // Add user back to model
                    String title = (userId == null) ? "Add User" : "Edit User";
                    model.addAttribute("title", title);
                    return "userForm"; // Stay on same page
                }
            } else {
                userService.saveUser(user);
            }
            return "redirect:/users";
        } else {
            String title = (userId == null) ? "Add User" : "Edit User";
            model.addAttribute("title", title);
            return "userForm";
        }
    }

    @GetMapping("users")
    public String index(Model model) {
        List<User> users = (List<User>) userService.findAllByOrderByUsernameAsc();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping(value = "user/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Add User");
        return "userForm";
    }

    @GetMapping(value = "user/edit/{id}")
    public String editUser(@PathVariable("id") Long userId, Model model) {
        model.addAttribute("user", userService.findUserById(userId));
        model.addAttribute("title", "Edit User");
        return "userForm";
    }


    @GetMapping("user/{id}")
    public String showUser(@PathVariable("id") Long userId, Model model) {
        model.addAttribute("user", userService.findUserById(userId));
        model.addAttribute("title", "Show User");
        return "userShow";
    }

    @GetMapping(value = "user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long userId, Model model) {
        userService.deleteUserById(userId);
        return "redirect:/users";
    }
}