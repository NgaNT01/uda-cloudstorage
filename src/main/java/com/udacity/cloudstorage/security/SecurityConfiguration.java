package com.udacity.cloudstorage.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/signup", "/css/**", "/js/**")
            .permitAll()
            .anyRequest()
            .authenticated();

        http.formLogin()
            .loginPage("/login")
            .failureUrl("/login-error")
            .permitAll();

        http.formLogin()
            .defaultSuccessUrl("/home", true)
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/logout-success")
            .permitAll();

    }

}
