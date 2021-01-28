package com.example.demo.Controller;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrController {
    @Autowired
    UserRepo up;

    @GetMapping("/regist")
    public String getReg(){
        return "regist";
    }

    @PostMapping("/regist")
    public String registr(@ModelAttribute User user, Model model){
        user.setActiv(true);
        user.setRoles(Collections.singleton(Role.USER));
        up.save(user);
        return "redirect:/login";
    }
}
