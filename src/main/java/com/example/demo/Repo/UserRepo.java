package com.example.demo.Repo;

import com.example.demo.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User,Long> {
    User findByUsername(String userName);
}
