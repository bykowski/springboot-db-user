package pl.bykowski.springbootdbuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AppUserRepo appUserRepo;

    @Autowired
    public WebSecurityConfig(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Jan")
                .password(getPasswordEncoder().encode("Jan123"))
                .roles("ADMIN", "USER");

        auth.inMemoryAuthentication()
                .withUser("Kaśka")
                .password(getPasswordEncoder().encode("Kaśka123"))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/test1").authenticated()
                .antMatchers("/test2").permitAll()
                .antMatchers("/test3").hasRole("USER")
                .antMatchers("/test4").hasRole("ADMIN")
                .antMatchers("/test5").hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin().permitAll();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        AppUser appUser = new AppUser();
        appUser.setUsername("Tapsik");
        appUser.setPassword(getPasswordEncoder().encode("Tapsik123"));
        appUserRepo.save(appUser);
    }
}
