package com.example.demo.Controller;

import com.example.demo.Entity.Hero;
import com.example.demo.Entity.User;
import com.example.demo.Repo.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;


@Controller
public class GreetingController {

    @Autowired
    HeroRepository hp;

    @Value("${upload.path}")
    private String uplPath;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/heroes")
    public String getHero( Model model){

       model.addAttribute("hero",new Hero());


        return "main";
    }

    @PostMapping ("/heroes")
    public String addHero(@AuthenticationPrincipal User user,
                          @Valid @ModelAttribute("hero") Hero hero,
                          BindingResult br,
                          Model model,
                          @RequestParam("photo") MultipartFile file){
       if (br.hasErrors()){
           return "main";
        }
        else {
            if (file != null && !file.isEmpty()) {
                File uplDir = new File(uplPath);
                if (!uplDir.exists()) {
                    uplDir.mkdir();
                }
                String UUIDFile = UUID.randomUUID().toString();
                String res = UUIDFile + "." + file.getOriginalFilename();

                try {
                    file.transferTo(new File(uplPath + "/" + res));
                    hero.setFilename(res);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            hero.setEditor(user);
            hp.save(hero);
        }
        return "redirect:/heroes";
    }

    @PostMapping ("/heroSearch")
    public String searchHeroes(@ModelAttribute Hero hero,
                               Model model){
        Iterable<Hero> heroes;
        if (hero.getHeroName()==null){
            heroes=hp.findAll();
        }
        else
        heroes=hp.findByHeroName(hero.getHeroName());
        model.addAttribute("heroes",heroes);
        return "main";
    }

}
