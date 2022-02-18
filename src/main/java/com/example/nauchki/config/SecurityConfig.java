package com.example.nauchki.config;

import com.example.nauchki.jwt.JwtLoginAndPasswordAuthenticationFilter;
import com.example.nauchki.jwt.JwtProvider;
import com.example.nauchki.jwt.JwtTokenVerifierFilter;
import com.example.nauchki.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * Файл общих настроек security
 */
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${upload.path}")
    private String uploadPath;

    private String localDir = System.getProperty("user.dir");

    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/registration", "/reg","/static/**", "/activate/*", "/test", "/auth", "/login",
                        "index.html","/swagger-ui", "/editpassword/**","editpass").permitAll()
                .antMatchers("/del/{spring:[0-9]+}", "/user/{spring:[0-9]+}","/stage/**","/admin","/admin/**").hasAnyAuthority("ADMIN","SUPERADMIN")
                //.anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtLoginAndPasswordAuthenticationFilter(authenticationManager(), jwtProvider))
                .addFilterAfter(new JwtTokenVerifierFilter(jwtProvider), JwtLoginAndPasswordAuthenticationFilter.class)
                .cors()
                .and()
                .formLogin()
                .and()
                .logout()
                .and()
                .csrf()
                .disable();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000","http://127.0.0.1:3000","http://localhost:8080")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///" + localDir + uploadPath + "/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
