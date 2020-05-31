package fr.plb.springsecuritydemo;

import fr.plb.springsecuritydemo.domain.Author;
import fr.plb.springsecuritydemo.domain.Vinyl;
import fr.plb.springsecuritydemo.repository.AuthorRepository;
import fr.plb.springsecuritydemo.repository.VinylRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

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
}
