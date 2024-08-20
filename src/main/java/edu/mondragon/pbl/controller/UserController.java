package edu.mondragon.pbl.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mondragon.pbl.entity.Role;
import edu.mondragon.pbl.entity.User;
import edu.mondragon.pbl.service.UserServiceImpl;

@Controller
@RequestMapping(path="/user")
public class UserController {
    
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(path="/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping(path="/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping(path="/process_register")
    public String registerUser(Model model, User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);
        userServiceImpl.saveUser(user);
        return "register_success";
    }

    @GetMapping(path="/profile")
    public String showProfilePage(Principal principal, HttpSession session, Model model) {
        String username = principal.getName();
        User user = userServiceImpl.findByUsername(username).get();
        session.setAttribute("user", user);
        model.addAttribute(user);
        return "user_profile";
    }

    @GetMapping(path="/edit")
    public String showEditForm(Principal principal, Model model) {
        String username = principal.getName();
        User user = userServiceImpl.findByUsername(username).get();
        model.addAttribute("user", user);
        return "edit_profile";
    }

    @PostMapping(path="/save")
    public String saveProfile(Principal principal, User user) {
        String username = principal.getName();
        User old_user = userServiceImpl.findByUsername(username).get();
        user.setPassword(old_user.getPassword());
        user.setId(old_user.getId());
        user.setRole(old_user.getRole());
        userServiceImpl.saveUser(user);
        return "edit_profile_complete";
    }

}
