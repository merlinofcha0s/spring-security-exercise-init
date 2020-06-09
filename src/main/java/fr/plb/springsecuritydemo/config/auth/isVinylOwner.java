package fr.plb.springsecuritydemo.config.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class isVinylOwner {

    public boolean check(String username) {
        if (hasRole("ROLE_ADMIN")) {
            return true;
        }
        if (hasRole("ROLE_USER")) {
            String currentUsername = ((UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getUsername();
            return username.equals(currentUsername);
        }
        return false;
    }

    private boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (role.equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }




}
