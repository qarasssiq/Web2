package org.example.Web2.controllers;

import org.example.Web2.domain.Test;
import org.example.Web2.domain.User;
import org.example.Web2.repos.QuestionRepo;
import org.example.Web2.repos.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private TestRepo testRepo;

    @GetMapping()
    String profile(@AuthenticationPrincipal User user, Model model) {
        Iterable<Test> tests = testRepo.findByAuthorId(user);
        tests.forEach(test -> {
            test.setNumberOfQuestions(questionRepo.countByTestId(test));
            testRepo.save(test);
        });
        model.addAttribute("user", user);
        model.addAttribute("tests", tests);
        return "profile";
    }
}
