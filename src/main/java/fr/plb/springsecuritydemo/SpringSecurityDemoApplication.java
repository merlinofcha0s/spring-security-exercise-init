package fr.plb.springsecuritydemo;

import fr.plb.springsecuritydemo.domain.Author;
import fr.plb.springsecuritydemo.domain.Authority;
import fr.plb.springsecuritydemo.domain.User;
import fr.plb.springsecuritydemo.domain.Vinyl;
import fr.plb.springsecuritydemo.repository.AuthorRepository;
import fr.plb.springsecuritydemo.repository.AuthorityRepository;
import fr.plb.springsecuritydemo.repository.UserRepository;
import fr.plb.springsecuritydemo.repository.VinylRepository;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner data(VinylRepository vinylRepository, AuthorRepository authorRepository) {
        return args -> {
            Author authorSaved = authorRepository.save(new Author("Linkin Park", "", Instant.now()));

            Vinyl in_the_end = new Vinyl("In the end", Instant.now(), authorSaved);
            Vinyl papercut = new Vinyl("Papercut", Instant.now(), authorSaved);
            Vinyl one_step_closer = new Vinyl("One step closer", Instant.now(), authorSaved);
            Vinyl points_of_authority = new Vinyl("Points of Authority", Instant.now(), authorSaved);

            vinylRepository.save(in_the_end);
            vinylRepository.save(papercut);
            vinylRepository.save(one_step_closer);
            vinylRepository.save(points_of_authority);

            vinylRepository.findAll().forEach(System.out::println);
        };
    }

    @Bean
    CommandLineRunner dataUser(UserRepository userRepository, AuthorityRepository authorityRepository,
                               PasswordEncoder passwordEncoder, VinylRepository vinylRepository) {
        return args -> {
            Authority adminAuthority = new Authority();
            adminAuthority.setName("ROLE_ADMIN");
            Authority userAuthority = new Authority();
            userAuthority.setName("ROLE_USER");

            authorityRepository.save(adminAuthority);
            authorityRepository.save(userAuthority);
            authorityRepository.findAll().forEach(System.out::println);

            User user = new User();
            user.setLogin("toto");
            user.setEmail("toto@toto.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setAuthorities(Set.of(authorityRepository.findById("ROLE_USER").get()));
            userRepository.save(user);

            Optional<Vinyl> in_the_end = vinylRepository.findBySongName("In the end");
            in_the_end.get().setUser(user);
            vinylRepository.save(in_the_end.get());
            user.getVinyls().add(vinylRepository.findBySongName("In the end").get());

            User admin = new User();
            admin.setLogin("totoadmin");
            admin.setEmail("totoadmin@toto.com");
            admin.setPassword(passwordEncoder.encode("god"));
            admin.setAuthorities(Set.of(authorityRepository.findById("ROLE_ADMIN").get()));
            userRepository.save(admin);

            Optional<Vinyl> points_of_authority = vinylRepository.findBySongName("Points of Authority");
            points_of_authority.get().setUser(admin);
            vinylRepository.save(points_of_authority.get());
            admin.getVinyls().add(vinylRepository.findBySongName("Points of Authority").get());

            userRepository.findAll().forEach(System.out::println);
        };
    }


}
