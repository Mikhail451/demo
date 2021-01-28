package com.example.demo.Repo;

import com.example.demo.Entity.Hero;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HeroRepository extends CrudRepository<Hero,Long> {
    public List<Hero> findByHeroName(String name);
}
