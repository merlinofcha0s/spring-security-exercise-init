package fr.plb.springsecuritydemo.repository;


import fr.plb.springsecuritydemo.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
