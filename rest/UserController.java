package ru.greenatom.atomskils.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.greenatom.atomskils.model.User;
import ru.greenatom.atomskils.resources.UserRepository;
import ru.greenatom.atomskils.services.UserService;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private UserService service;

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder devPasswordEncoder;


    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> getAll() {
        return this.repository.getAll(new User());
    }

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User create(@RequestBody User user) {
        String encode = devPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        Integer integer = this.repository.create(user, null);
        user.setId(integer);
        return user;
    }

    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User update(@RequestBody User user) {
        String encode = devPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        return this.repository.edit(user);
    }


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User getById(@PathVariable Integer id) {
        return this.service.getById(id);
    }

    @DeleteMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(@RequestBody User user) {
        this.service.delete(user);
    }

}
