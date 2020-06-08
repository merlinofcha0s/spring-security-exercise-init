package fr.plb.springsecuritydemo.config;

import fr.plb.springsecuritydemo.service.security.UserDetailsServiceMongo;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private UserDetailsServiceMongo userDetailsServiceMongo;

    public SecurityConfiguration(UserDetailsServiceMongo userDetailsServiceMongo) {
        this.userDetailsServiceMongo = userDetailsServiceMongo;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceMongo);
    }
}
