package com.GotYourBac.gotYourBac.controllers;

import com.GotYourBac.gotYourBac.models.ApplicationUser;
import com.GotYourBac.gotYourBac.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    //autowired passwordEncoder to use BCrypt for unique password.
    //TODO: Lets look at having a minimum password requirement.
    @Autowired
    private PasswordEncoder passwordEncoder;

    //go to registration page.
    @GetMapping("/registration")
    public String getRegistration(){
        return "registration";
    }

    //Create a new user in DB, ensure we don't user Postgresql pre-defined variables. i.e 'user'
    @PostMapping("/registration")
    public RedirectView createNewUser(String username, String password, String firstName, String lastName, String gender, double height, float weight) {
        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), firstName, lastName, gender, height, weight);
        applicationUserRepository.save(newUser);
        //TODO: change the redirect route to what we decide. For now its to the homepagegit
        return new RedirectView("/");
    }

    @GetMapping("/profile")
    public String showProfile(Principal p, Model m) {
        if(p != null) {
            ApplicationUser loggedInUser = applicationUserRepository.findByUsername((p.getName()));
            m.addAttribute("loggedInUser", loggedInUser);
        }
        return "profile";
    }

//    @PutMapping("/update/{id}")
//    public @ResponseBody String updateUser(@RequestBody ApplicationUser loggedInUser) {
//        applicationUserRepository.save(loggedInUser);
//        return "profileUpdate";
//    }

//TODO: If separate login page is needed then uncomment route.
//    @GetMapping("/login")
//    public String loginAsAUser(){
//        return "login";
//    }
        //TODO: change the redirect route to what we decide. For now its to the homepage




    @GetMapping("/login")
    public String loginAsAUser(){
        return "login";
    }
}
