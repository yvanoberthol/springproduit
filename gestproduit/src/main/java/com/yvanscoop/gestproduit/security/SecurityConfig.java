package com.yvanscoop.gestproduit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    public void globalConfig(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("yvan").password("1105").roles("ADMIN","GESTIONNAIRE");
        auth.inMemoryAuthentication().withUser("yvano").password("1997").roles("ADMIN","GESTIONNAIRE");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().formLogin().loginPage("/produits").permitAll();
    }
}
