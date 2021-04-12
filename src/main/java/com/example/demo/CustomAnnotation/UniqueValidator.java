package com.example.demo.CustomAnnotation;

import com.example.demo.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueValidator implements ConstraintValidator<UniqueUserName,String> {
    @Autowired UserRepo up;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return up.findByUsername(username)==null;
    }
}
