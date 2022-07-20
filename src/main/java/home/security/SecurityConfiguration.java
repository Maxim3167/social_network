package home.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private CustomUserDetails customUserDetails;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/open/**").permitAll()
                .antMatchers("/create/**").access("hasAnyRole('ADMIN','USER','MODERATOR')")
                .antMatchers("/deleteComment/id/{id}").access("@customSecurity.isAuthorOfComment(authentication.name,#id) or hasAnyRole('ADMIN','MODERATOR')")
                .antMatchers("/deletePost/id/{id}").access("@customSecurity.isAuthorOfPost(authentication.name,#id) or hasAnyRole('ADMIN','MODERATOR')")
                .antMatchers("/updatePost/*/id/{id}").access("@customSecurity.isAuthorOfPost(authentication.name,#id) or hasAnyRole('ADMIN','MODERATOR')")
                .anyRequest().authenticated();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       return NoOpPasswordEncoder.getInstance();
    }
}
