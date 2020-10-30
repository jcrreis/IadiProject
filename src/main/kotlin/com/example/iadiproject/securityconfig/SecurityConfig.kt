package com.example.iadiproject.securityconfig

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
class SecurityConfig(
        val customUserDetails: CustomUserDetailsService
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }


    override fun configure(http: HttpSecurity) {

        http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/webjars/**").permitAll()
        .antMatchers("/swagger-resources/**").permitAll()
        .antMatchers("/swagger-ui.html").permitAll()
        .antMatchers("/v2/api-docs").permitAll()
        .antMatchers(HttpMethod.POST, "/students").permitAll()
        .antMatchers(HttpMethod.POST, "/reviewers").permitAll()
        .anyRequest().authenticated()
        .and()
        //.formLogin()
        //.and()
         .httpBasic();
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication().withUser("admin")
                .password(BCryptPasswordEncoder().encode("admin"))
                .authorities(mutableListOf(SimpleGrantedAuthority("ROLE_ADMIN")))
                .and()
                .passwordEncoder(BCryptPasswordEncoder())
                .and()
                .userDetailsService(customUserDetails)
                .passwordEncoder(BCryptPasswordEncoder())
    }



}