package fr.plb.springsecuritydemo.service;

import fr.plb.springsecuritydemo.domain.Authority;
import fr.plb.springsecuritydemo.domain.User;
import fr.plb.springsecuritydemo.repository.AuthorityRepository;
import fr.plb.springsecuritydemo.repository.UserRepository;
import fr.plb.springsecuritydemo.service.dto.UserDTO;
import fr.plb.springsecuritydemo.service.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthorityRepository authorityRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.userMapper = userMapper;
    }

    public UserDTO registerUser(UserDTO userDTO) {
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById("ROLE_USER").ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        User userSaved = userRepository.save(newUser);
        return userMapper.toDto(userSaved);
    }

    public Optional<UserDTO> getUserByUsername(String username) {
        return userRepository.findOneByLogin(username).map(userMapper::toDto);
    }

}
