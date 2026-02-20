package com.keleybolos.api.config;

import com.keleybolos.api.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Necessário para o formulário de pedido conseguir enviar dados (POST)
                .authorizeHttpRequests(auth -> auth
                        // Libera apenas o que é "estético"
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/images/**").permitAll()
                        // Bloqueia TODO o resto (inclusive a página inicial e salvar pedidos)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true) // Após logar, vai para a página principal
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/login")) // Quando sair, volta pro login
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository repository) {
        return login -> repository.findByLogin(login)
                .map(u -> User.withUsername(u.getLogin())
                        .password(u.getSenha())
                        .roles("ADMIN")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + login));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Como as senhas no banco estão em texto simples (ex: 12345)
        return NoOpPasswordEncoder.getInstance();
    }
}