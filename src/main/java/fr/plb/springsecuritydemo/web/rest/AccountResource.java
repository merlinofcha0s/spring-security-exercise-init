package fr.plb.springsecuritydemo.web.rest;

import fr.plb.springsecuritydemo.service.UserService;
import fr.plb.springsecuritydemo.service.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountResource {

    private final UserService userService;

    public AccountResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerAccount(@RequestBody UserDTO userDTO) {
       return userService.registerUser(userDTO);
    }
}
