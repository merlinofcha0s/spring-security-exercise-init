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

import java.time.Instant;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        serverFactory.addAdditionalTomcatConnectors(redirectConnector());
        return serverFactory;
    }

    private Connector redirectConnector() {
        Connector connector = new Connector(TomcatReactiveWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
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
    CommandLineRunner dataUser(UserRepository userRepository, AuthorityRepository authorityRepository) {
        return args -> {
            User user = new User();
            user.setLogin("toto");
            user.setEmail("toto@toto.com");
            user.setPassword("{noop}password");
            user.setAuthorities(Set.of(new Authority("ROLE_USER")));

            User admin = new User();
            admin.setLogin("totoadmin");
            admin.setEmail("totoadmin@toto.com");
            admin.setPassword("{noop}god");
            admin.setAuthorities(Set.of(new Authority("ROLE_ADMIN")));

            Authority adminAuthority = new Authority();
            adminAuthority.setName("ROLE_ADMIN");
            Authority userAuthority = new Authority();
            userAuthority.setName("ROLE_USER");

            authorityRepository.save(adminAuthority);
            authorityRepository.save(userAuthority);
            authorityRepository.findAll().forEach(System.out::println);

            userRepository.save(user);
            userRepository.save(admin);
            userRepository.findAll().forEach(System.out::println);
        };
    }


}
