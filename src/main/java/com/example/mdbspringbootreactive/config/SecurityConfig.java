package com.example.mdbspringbootreactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

//Claude
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.authentication.ReactiveAuthenticationManager;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // Tắt CSRF nếu dùng JWT
//                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/login").permitAll() // Cho phép truy cập /login mà không cần token
//                .anyRequest().authenticated(); // Các request khác cần xác thực
//    }
//}

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login").permitAll() // Cho phép truy cập /login mà không cần xác thực
//                        .anyRequest().authenticated() // Các request khác cần xác thực
//                )
//                .formLogin(form -> form
//                        .loginPage("/login") // Trang đăng nhập tùy chỉnh
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                );
//        return http.build();
//    }
//}

//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login", "/public/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .csrf(csrf -> csrf.disable()); // Tắt CSRF theo cách mới
//        return http.build();
//    }
//}

//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http
//                .authorizeExchange(exchanges ->
//                        exchanges
//                                .anyExchange().permitAll()  // cho phép tất cả
//                ).csrf(csrf -> csrf.disable());
//        return http.build();
//    }
//}


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationManager jwtAuthenticationManager;
    private final ServerSecurityContextRepository securityContextRepository;

    public SecurityConfig(JwtAuthenticationManager jwtAuthenticationManager) {
        this.jwtAuthenticationManager = jwtAuthenticationManager;
        this.securityContextRepository = new WebSessionServerSecurityContextRepository();
    }

//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//                .authenticationManager(jwtAuthenticationManager)
//                .securityContextRepository(securityContextRepository)
//                .authorizeExchange(exchanges -> exchanges
//                        .pathMatchers("/login", "/auth/**", "/public/**").permitAll()
//                        .anyExchange().authenticated()
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login")
//                        .authenticationFailureHandler((exchange, exception) -> {
//                            exchange.getExchange().getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                            return exchange.getExchange().getResponse().setComplete();
//                        })
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessHandler((exchange, authentication) -> {
//                            exchange.getExchange().getResponse().setStatusCode(HttpStatus.OK);
//                            return exchange.getExchange().getResponse().setComplete();
//                        })
//                )
//                .csrf(csrf -> csrf.disable())
//                .build();
//    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authenticationManager(jwtAuthenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/login", "/auth/**", "/public/**").permitAll()
                        .anyExchange().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }
}