package com.example.demo.Entity;

import javax.persistence.*;


import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Hero(){}

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }



    @NotEmpty(message = "Field HeroName should not be empty")
    private String heroName;
    @NotBlank(message = "Field Abilities should not be empty")
    @Size(min=2 ,message = "Length must be min 2 symbols")
    private String abilities;
    @NotBlank(message = "Field Rank should not be empty")
    private String ranking;

    private String filename;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User editor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }

    public String getUser() {
        return editor!=null? getEditor().getUsername():"no user";
    }
}
