package com.Aryaman.BankManagementSystem.Config;


import com.Aryaman.BankManagementSystem.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());

        HttpSecurity httpSecurity = http.authorizeHttpRequests(auth -> auth
                // ✅ 1. Admin Endpoints — must come first
                .requestMatchers(
                        "/api/user/delete/**",
                        "/api/user/get/**",
                        "/api/account/get/**",
                        "/api/account/delete/**",
                        "/api/user/role/**",
                        "/api/user/update/**",
                        "/api/account/changeStatus/**"
                ).hasRole("ADMIN")
                .requestMatchers(
                        "/api/user/update/**",
                        "/api/account/create/user/**",
                        "/api/account/update/**",
                        "/api/transactions/withdraw/**",
                        "/api/transactions/deposit/**",
                        "/api/transactions/transfer/**",
                        "/api/transactions/accBalance/**"

                )
                .hasAnyRole("ADMIN", "USER")

                // ✅ 2. Public endpoints
                /*.requestMatchers(
                        "/api/user/create",
                        "/login",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html"
                ).permitAll()*/

                // ✅ 3. Everything else requires authentication
                .anyRequest().authenticated()

        );
        http.httpBasic(Customizer.withDefaults());


       http.formLogin(withDefaults());
        //http.httpBasic(withDefaults());

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}


