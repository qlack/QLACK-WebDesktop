package com.eurodyn.qlack.webdesktop.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security Configuration that disables authentication for all paths
 *
 * @author European Dynamics SA.
 */
@Configuration
@ConditionalOnProperty(prefix = "security", value = "enabled", havingValue = "none")
public class NoSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Permits access to all paths
     *
     * @param http the {@link org.springframework.security.config.annotation.web.builders.HttpSecurity} object
     * @throws Exception if the security configuration cannot be applied
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();
    }
}
