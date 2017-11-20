package it.christianb.pfinanceblocking.controller;

import it.christianb.pfinanceblocking.model.User;
import it.christianb.pfinanceblocking.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@BasePathAwareController
public class NewUserController {

    @Autowired private UserRepo userRepo;

    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/newUser", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<?> newUser(@Valid @RequestBody User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser = userRepo.save(newUser);
        return ResponseEntity.ok(new Resource<>(newUser));
    }

}
