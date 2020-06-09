package fr.plb.springsecuritydemo.config;

import fr.plb.springsecuritydemo.service.security.PersistentLoginsTokenService;
import fr.plb.springsecuritydemo.service.security.UserDetailsServiceMongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(2)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceMongo userDetailsServiceMongo;
    private PersistentLoginsTokenService persistentLoginsTokenService;

    public SecurityConfiguration(UserDetailsServiceMongo userDetailsServiceMongo,
                                 PersistentLoginsTokenService persistentLoginsTokenService) {
        this.userDetailsServiceMongo = userDetailsServiceMongo;
        this.persistentLoginsTokenService = persistentLoginsTokenService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/api/register").permitAll()
                .mvcMatchers("/api/vinyls").hasRole("ADMIN")
                .mvcMatchers("/api/vinyls-for-admin/*")
                .access("isFullyAuthenticated() && hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .rememberMe().key("lkdfslkflsdk§è§è§rerieui")
                .rememberMeCookieName("mon-cookie-remember")
                .tokenRepository(persistentLoginsTokenService)
                .and()
                .httpBasic()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .and().oauth2Login();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceMongo);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
