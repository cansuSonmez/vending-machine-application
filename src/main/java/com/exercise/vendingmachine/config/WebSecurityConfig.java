package com.exercise.vendingmachine.config;
import com.exercise.vendingmachine.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                /*
                 * H2 Console permitted
                 */
                .antMatchers("/h2/**").permitAll()

                .antMatchers(HttpMethod.GET,AUTH_WHITELIST).permitAll()

                /*
                 * UserController authorization configuration
                 */
                .antMatchers(HttpMethod.POST, "/users").permitAll()

                /*
                 * ProductController authorization & authority configuration
                 */
                .antMatchers(HttpMethod.GET, "/products/**").hasAnyAuthority("SELLER", "BUYER")
                .antMatchers("/products/**").hasAuthority("SELLER")

                /*
                 * VendingMachineController authorization & authority configuration
                 */
                .antMatchers(HttpMethod.POST, "/deposit").hasAuthority("BUYER")
                .antMatchers(HttpMethod.POST, "/buy").hasAuthority("BUYER")
                .antMatchers(HttpMethod.POST, "/reset").hasAuthority("BUYER")

                /*
                 * Common security configurations
                 * Since it is an example application, basic auth is used for simplicity. (JWT or OAuth2 not implemented)
                 */
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .realmName("Vending Machine API")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin();
    }

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

}