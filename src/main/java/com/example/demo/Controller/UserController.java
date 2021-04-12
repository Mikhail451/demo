package com.example.demo.Controller;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserRepo up;

    @Autowired
    PasswordEncoder pe;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Model model){
        model.addAttribute("users",up.findAll());
        return "userList";
    }

    @GetMapping("/{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("users",user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userSave(@ModelAttribute User username
                           ,@RequestParam("id") User user,
                           @RequestParam Map<String,String> maps
                         ){
        user.setUsername(username.getUsername());
        Set<String> roleSet= Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key:maps.values()) {
            if(roleSet.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        up.save(user);
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String profiles(Model model, @AuthenticationPrincipal User user){

        model.addAttribute("password",user.getPassword());
        model.addAttribute("emailing",user.getEmail());
        return "profile";
    }

    @PostMapping("/profile")
    public String changeProfile(@AuthenticationPrincipal User user,@RequestParam("email") String email,@RequestParam("password") String password,Model model){
        if (change(user.getEmail(),email) || change(user.getPassword(),password) ) {
            user.setPassword(pe.encode(password));
            user.setEmail(email);
            up.save(user);
            model.addAttribute("mess","Your data has been changed");
            System.out.println("changed");
        }
        model.addAttribute("password",user.getPassword());
        model.addAttribute("emailing",user.getEmail());
        return "profile";
    }

    public boolean change(String first,String second){
        if (!first.equals(second)){
            return true;
        }
        else
            return false;
    }

}
