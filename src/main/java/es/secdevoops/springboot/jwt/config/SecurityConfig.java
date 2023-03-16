package es.secdevoops.springboot.jwt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    //JwtAuthenticationFilter will filter and verify the JWT token
    private final JwtAuthenticationFilter jwtAuthFilter;

    //AuthenticationProvider will authenticate the user
    private final AuthenticationProvider authenticationProvider;

    //LogoutHandler will handle the user logout
    private final LogoutHandler logoutHandler;

    //Configures the Spring Security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()  //Disables CSRF protection (jwt is not vulnerable to CSRF)
                .disable()
                .authorizeRequests() //Configures authorization
                .requestMatchers("/secdevoops/admin/**", "/secdevoops/register/admin/**")
                .hasRole("ADMIN") //Only users with ROLE_ADMIN are allow to request this urls
                .requestMatchers("/secdevoops/user/**")
                .hasAnyRole("USER", "ADMIN")//Only users with ROLE_ADMIN and ROLE_USER are allow to request this urls
                .requestMatchers("/secdevoops/auth/**", "/secdevoops/register/user/**", "/error")
                .permitAll() //Permits all requests to these endpoints
                .requestMatchers("/swagger-ui/**", "/swagger-ui/", "/v3/**")
                .permitAll() //Permits all requests to these endpoints
                .anyRequest()
                .authenticated() //Authenticates any other request
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Disables session creation
                .and()
                .authenticationProvider(authenticationProvider) //Sets the AuthenticationProvider to authenticate users
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) //Adds the JWT filter before the UsernamePasswordAuthenticationFilter
                .logout()
                .logoutUrl("/secdevoops/logout") //Configures the logout endpoint
                .addLogoutHandler(logoutHandler) //Adds the logout handler
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()); //Clears the SecurityContextHolder after logout
        return http.build();
    }
}