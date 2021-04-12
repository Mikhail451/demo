package com.example.demo.Controller;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.util.Collections;
import java.util.UUID;

@Controller
public class RegistrController {
    @Autowired
    UserRepo up;

    @Autowired
    MailSender ms;

    @Autowired
    PasswordEncoder pe;

    @GetMapping("/regist")
    public String getReg(Model model){
        model.addAttribute("user",new User());
        return "regist";
    }

    @PostMapping("/regist")
    public String registr(@Valid User user,
                          BindingResult br,
                          Model model){
        if (br.hasErrors()){
            return "regist";
        }
        else {
            user.setActiv(true);
            user.setRoles(Collections.singleton(Role.USER));
            user.setActivCode(UUID.randomUUID().toString());
            user.setPassword(pe.encode(user.getPassword()));
            up.save(user);

            if (!user.getEmail().isEmpty()) {
                String msg = String.
                        format("Hello, %s ,your account was created here.Please visit this http:localhost:8080/", user.getUsername());
                ms.sendEmail(user.getEmail(), "Activision", msg);
            }
            return "redirect:/login";
        }
    }

}
