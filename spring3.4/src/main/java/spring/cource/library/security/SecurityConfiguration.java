package spring.cource.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .anyRequest().authenticated()
                )

                .csrf(AbstractHttpConfigurer::disable)
                .formLogin((formLogin) ->
                        formLogin
                                .permitAll()
                                .defaultSuccessUrl("/success")
                                .usernameParameter("lib_username")
                                .passwordParameter("lib_password")
                )
                .rememberMe((remember) -> remember.key("library")
                        .tokenValiditySeconds(60 * 60 * 24));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
