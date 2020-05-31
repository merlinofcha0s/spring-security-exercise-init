package fr.plb.springsecuritydemo.repository;


import fr.plb.springsecuritydemo.domain.Vinyl;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VinylRepository extends MongoRepository<Vinyl, String> {
    Optional<Vinyl> findBySongName(String songName);
}
