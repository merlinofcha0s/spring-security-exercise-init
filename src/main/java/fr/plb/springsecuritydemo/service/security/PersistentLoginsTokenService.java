package fr.plb.springsecuritydemo.service.security;

import fr.plb.springsecuritydemo.domain.PersistentLoginToken;
import fr.plb.springsecuritydemo.repository.PersistentLoginsTokenRepository;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersistentLoginsTokenService implements PersistentTokenRepository {

    private final PersistentLoginsTokenRepository tokenRepository;

    public PersistentLoginsTokenService(PersistentLoginsTokenRepository persistentLoginsTokenRepository) {
        this.tokenRepository = persistentLoginsTokenRepository;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        this.tokenRepository.save(new PersistentLoginToken(token.getUsername(),
                token.getSeries(), token.getTokenValue(), token.getDate()));
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentLoginToken currentToken = tokenRepository.findBySeries(series);
        tokenRepository.save(new PersistentLoginToken(currentToken.getId(),
                currentToken.getUsername(), series, tokenValue, lastUsed));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        PersistentLoginToken currentToken = tokenRepository.findBySeries(seriesId);
        return new PersistentRememberMeToken(currentToken.getUsername(),
                currentToken.getSeries(), currentToken.getTokenValue(), currentToken.getDate());

    }

    @Override
    public void removeUserTokens(String username) {
        PersistentLoginToken currentToken = tokenRepository.findByUsername(username);
        if (currentToken != null) {
            tokenRepository.delete(currentToken);
        }
    }
}
