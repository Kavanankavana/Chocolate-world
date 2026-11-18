package com.example.chocolate.controller;

import com.example.chocolate.model.User;
import com.example.chocolate.repository.PlaceRepository;
import com.example.chocolate.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    public AuthController(UserRepository userRepository, PlaceRepository placeRepository) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/";
        }
        model.addAttribute("places", placeRepository.findAll());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userRepository.findByUsername(username.trim());
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            session.setAttribute("currentUser", userOpt.get());
            redirectAttributes.addFlashAttribute("success", "Welcome back, " + userOpt.get().getFullName() + "!");
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("error", "Invalid username or password. Please try again.");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session, Model model) {
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/";
        }
        model.addAttribute("user", new User());
        model.addAttribute("places", placeRepository.findAll());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
                                  @RequestParam String fullName,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam String confirmPassword,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        if (username.isBlank() || password.isBlank() || fullName.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "All fields are required.");
            return "redirect:/register";
        }
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/register";
        }
        if (userRepository.existsByUsername(username.trim())) {
            redirectAttributes.addFlashAttribute("error", "Username '" + username + "' is already taken.");
            return "redirect:/register";
        }

        User newUser = new User(username.trim(), password, fullName.trim(), email.trim(), "ROLE_USER");
        userRepository.save(newUser);

        // Automatically log in the user
        session.setAttribute("currentUser", newUser);
        redirectAttributes.addFlashAttribute("success", "Account created successfully! Welcome to Chocolate World, " + fullName + "!");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("info", "You have been logged out successfully.");
        return "redirect:/login";
    }
}

