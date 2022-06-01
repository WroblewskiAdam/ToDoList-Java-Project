package com.example.PAP2022.security;

import com.example.PAP2022.enums.ApplicationUserRole;
import com.example.PAP2022.security.jwt.AuthorizationFilter;
import com.example.PAP2022.services.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final ApplicationUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthorizationFilter authenticationJwtTokenFilter() {
        return new AuthorizationFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder.bCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/auth/**").permitAll()
                .antMatchers("/teams/save").hasAnyAuthority("ADMIN", "TEAM_LEADER")
                .antMatchers("/teams/edit").hasAnyAuthority("ADMIN", "TEAM_LEADER")
                .antMatchers("/teams/addMember").hasAnyAuthority("ADMIN", "TEAM_LEADER")
                .antMatchers("/teams/deleteMember").hasAnyAuthority("ADMIN", "TEAM_LEADER")
                .antMatchers("/teams/delete").hasAnyAuthority("ADMIN", "TEAM_LEADER")
                .antMatchers("/teams/**").hasAnyAuthority("ADMIN", "TEAM_LEADER", "USER")
                .antMatchers("/user/change_role").hasAnyAuthority("ADMIN")
                .antMatchers("/user/delete").hasAnyAuthority("ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ADMIN", "TEAM_LEADER", "USER")
                .antMatchers("/tasks/**").hasAnyAuthority("ADMIN", "TEAM_LEADER", "USER")
//                .antMatchers("/team/**").permitAll()
//                .antMatchers("/task/**").permitAll()
//                .antMatchers("/user/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
