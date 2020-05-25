package main.web.security.user.model;

import main.domain.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class WebUser implements UserDetails {

    private int id;
    private String email;
    private String password;
    private boolean isModerator;

    public WebUser(User userEntity) {
        this.id = userEntity.getId();
        this.password = userEntity.getPassword();
        this.email = userEntity.getEmail();
        this.isModerator = userEntity.isModerator();
    }

    public static WebUser fromAuth(Authentication authentication) {
        return (WebUser) authentication.getPrincipal();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (isModerator) {
            roles.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
