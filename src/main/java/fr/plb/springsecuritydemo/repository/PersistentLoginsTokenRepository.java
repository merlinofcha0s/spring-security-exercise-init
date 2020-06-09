package fr.plb.springsecuritydemo.repository;

import fr.plb.springsecuritydemo.domain.PersistentLoginToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersistentLoginsTokenRepository extends MongoRepository<PersistentLoginToken, String> {

	PersistentLoginToken findBySeries(String series);
	PersistentLoginToken findByUsername(String username);
	
}
