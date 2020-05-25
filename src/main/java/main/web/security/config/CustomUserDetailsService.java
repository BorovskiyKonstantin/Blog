package main.web.security.config;

import main.domain.user.usecase.UserUseCase;
import main.web.security.user.model.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserUseCase userUseCase;

    @Override
    public UserDetails loadUserByUsername(final String email)
            throws UsernameNotFoundException {
        return userUseCase
                .getUserByEmail(email)
                .map(WebUser::new)
                .orElseThrow(() ->  new UsernameNotFoundException("User not found: " + email));
    }
}