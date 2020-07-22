package main.web.security.config;

import main.domain.user.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        var jsonResponseHandler = customAuthenticationFailureHandler();

        http
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,  "/api/auth/*").permitAll()   //запросы без аутентификации
                .antMatchers(HttpMethod.OPTIONS,  "/api/auth/*").permitAll()    //запросы без аутентификации
                .antMatchers(HttpMethod.POST).authenticated()   //все запросы POST проходят через аутентификацию
                .antMatchers(HttpMethod.PUT).authenticated()   //все запросы PUT проходят через аутентификацию
                .antMatchers(HttpMethod.DELETE).authenticated()   //все запросы DELETE проходят через аутентификацию
                .antMatchers("/api/auth/login").not().fullyAuthenticated()
                .antMatchers("/**").permitAll()     //запросы без аутентификации
                .and()
                .addFilterBefore(AuthenticationFilter(jsonResponseHandler), UsernamePasswordAuthenticationFilter.class)     //фильтр запросов
                .logout()
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler(jsonResponseHandler)
                .and()
                .logout()
                .permitAll()
        ;
    }

    @Autowired
    private UserUseCase userUseCase;

    @Bean
    public CustomAuthenticationHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationHandler(userUseCase);
    }

    @Bean
    public AuthenticationFilter AuthenticationFilter(CustomAuthenticationHandler authHandler) throws Exception {
        AuthenticationFilter authenticationFilter
                = new AuthenticationFilter();

        authenticationFilter.setAuthenticationSuccessHandler(authHandler);
        authenticationFilter.setAuthenticationFailureHandler(authHandler);
        authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/auth/login", "POST"));
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }


}
