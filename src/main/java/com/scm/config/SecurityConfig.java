package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
    // user create and login using java with in memory service
    // @Bean
    // public UserDetailsService userDetailsService() {
    // UserDetails user =
    // User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN").build();

    // var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
    // return inMemoryUserDetailsManager;
    // }

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // configuration
        // url configuration (public and private)
        // httpSecurity.authorizeHttpRequests(authorize -> {
        // authorize.requestMatchers("/user/**").authenticated()
        // .requestMatchers("/do-register", "/register", "/css/**", "/js/**",
        // "/images/**").permitAll();
        // authorize.anyRequest().permitAll();
        // });

        // form default login
        // to change anything related to form, we will come here
        // httpSecurity.formLogin(Customizer.withDefaults());

        httpSecurity
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/user/**").authenticated()
                            .requestMatchers("/do-register", "/register", "/css/**", "/js/**", "/images/**")
                            .permitAll();
                    authorize.anyRequest().permitAll();
                })
                .formLogin(Customizer.withDefaults())
                .csrf().disable(); // ⛔ Use only in dev

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
