package com.example.demo.Controller;

import com.example.demo.Entity.Hero;
import com.example.demo.Entity.User;
import com.example.demo.Repo.HeroRepository;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@Controller
public class GreetingController {

    @Autowired
    HeroRepository hp;

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting.fthl";
    }

    @GetMapping("/heroes")
    public String getHero( Model model){

       model.addAttribute("hero",new Hero());


        return "main";
    }

    @PostMapping ("/heroes")
    public String addHero(@AuthenticationPrincipal User user,
                          @ModelAttribute Hero hero,
                          Model model){
        System.out.println(user.getUsername());
        hero.setEditor(user);
        hp.save(hero);
        Iterable<Hero> heroes=hp.findByHeroName(hero.getHeroName());
        model.addAttribute("heroes",heroes);
        return "main";
    }

}
