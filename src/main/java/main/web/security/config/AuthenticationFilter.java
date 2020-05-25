package main.web.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.web.security.user.dto.UsernamePasswordPairDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Log logger = LogFactory.getLog(AuthenticationFilter.class);

    private static final String ERROR_MESSAGE = "Something went wrong while parsing /login request body";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationFilter() {
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernamePasswordPairDto authRequest = objectMapper.readValue(request.getInputStream(), UsernamePasswordPairDto.class);

            UsernamePasswordAuthenticationToken token
                    = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
            setDetails(request, token);

            return this.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            logger.error(ERROR_MESSAGE, e);
            throw new InternalAuthenticationServiceException(ERROR_MESSAGE, e);
        }
    }
}
