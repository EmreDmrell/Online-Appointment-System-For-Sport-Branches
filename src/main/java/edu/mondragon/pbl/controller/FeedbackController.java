package edu.mondragon.pbl.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import edu.mondragon.pbl.entity.Feedback;
import edu.mondragon.pbl.entity.User;
import edu.mondragon.pbl.repository.FeedbackRepository;
import edu.mondragon.pbl.service.UserServiceImpl;

@Controller
@RequestMapping(path="/feedback")
public class FeedbackController {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping(path="/form")
    public String showFeedbackForm(Model model) {
        model.addAttribute("feedback", new Feedback());
        return "feedback_form";
    }
    
    @PostMapping(path="/save")
    public String saveFeedback(Feedback feedback, Principal principal, @RequestParam("ano") Boolean ano) {
        String username = principal.getName();
        User user = userServiceImpl.findByUsername(username).get();
        if (ano) {
            feedback.setUser(user);
        }
        feedbackRepository.save(feedback);
        return "feedback_sent";
    }

}
