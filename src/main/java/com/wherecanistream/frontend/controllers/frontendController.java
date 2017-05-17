package com.wherecanistream.frontend.controllers;

/**
 * Created by djuve on 10.05.2017.
 */


import com.wherecanistream.frontend.auth.RestHandler;
import com.wherecanistream.frontend.auth.UserDetailsServiceImpl;
import com.wherecanistream.frontend.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class frontendController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    RestHandler restHandler;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {

        if (logout == null){
            model.addAttribute("login","true");
        }

        if(logout != null){
            this.userDetailsService.clearLoggedInUser();
        }

        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
            logger.warn("login failed");
        }

        return "home";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String register(@ModelAttribute User user, Model model){
        String error = restHandler.addNewUser(user);
        if(error != null){
            model.addAttribute("error",error);
            logger.info(error);
            return "register";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public String showUserData(Model model) {
        User loggedInUser = userDetailsService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);
        return "profile";
    }

    @RequestMapping(value = {"/profileUpdate"}, method = RequestMethod.GET)
    public String updateUser(Model model){
        User loggedInUser = userDetailsService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);
        return "profileUpdate";
    }

    @RequestMapping(value = {"/profileUpdate"}, method = RequestMethod.POST)
    public String updateUser(@ModelAttribute User user, Model model){
        String error = restHandler.updateUser(user);
        if(error != null){
            model.addAttribute("error",error);
            logger.info(error);
            return "register";
        }
        this.userDetailsService.updateLoggedInUser(user.getLoginID());
        return "redirect:/profile";
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        User loggedInUser = userDetailsService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);
        return "home";
    }
}
