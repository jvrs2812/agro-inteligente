package com.agro.inteligente.Configuration;

import br.com.caelum.stella.validation.CPFValidator;
import com.agro.inteligente.Configuration.Security.CustomDetailsService;
import com.agro.inteligente.User.Repository.Adapters.IAdapterUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final IAdapterUserRepository userRepository;

    private final CustomDetailsService userDetailsService;
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> this.userDetailsService.loadUserByUsername(username);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CPFValidator cpfValidator(){
        return new CPFValidator();
    }

    @Bean
    public ClassLoaderTemplateResolver classLoaderTemplateResolver(){
        return new ClassLoaderTemplateResolver();
    }
}

