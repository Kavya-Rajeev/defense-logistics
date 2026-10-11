package com.team21.defense_logistics;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    // demo accounts: username -> {password, display name}
    private static final Map<String, String[]> USERS = Map.of(
            "admin", new String[]{"admin123", "Command Administrator"},
            "officer", new String[]{"officer123", "Logistics Officer"});

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("user") != null) return "redirect:/";
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, Model model) {
        String[] u = USERS.get(username.trim().toLowerCase());
        if (u == null || !u[0].equals(password)) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
        session.setAttribute("user", u[1]);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}