package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import com.scm.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetailService userDetailService; // to load data from database

    @Autowired
    private OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;

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

        // form default login
        // to change anything related to form, we will come here to

        httpSecurity
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/user/**").authenticated()
                            .requestMatchers("/do-register", "/register", "/css/**", "/js/**", "/images/**")
                            .permitAll();
                    authorize.anyRequest().permitAll();
                })
                .formLogin(formLogin -> {
                    formLogin.loginPage("/login");
                    formLogin.loginProcessingUrl("/authentication");
                    formLogin.defaultSuccessUrl("/user/profile", true);
                    formLogin.failureForwardUrl("/login?error=true");
                    formLogin.usernameParameter("email");
                    formLogin.passwordParameter("password");

                });

        httpSecurity.logout(logoutForm -> {
            logoutForm
                    .logoutRequestMatcher(new OrRequestMatcher(
                            new AntPathRequestMatcher("/logout", "POST"),
                            new AntPathRequestMatcher("/logout", "GET")))
                    .logoutSuccessUrl("/login?logout=true");
        });

        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(oAuthAuthenticationSuccessHandler);
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
