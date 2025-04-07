package Project.BE_BlockChain_Wallet.config;

import Project.BE_BlockChain_Wallet.service.CustomUserDetailsService;
import Project.BE_BlockChain_Wallet.service.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Disable CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Cấu hình CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // Cho phép public access cho các endpoint xác thực
                        .requestMatchers("/api/blockchain/**").permitAll()  // Cho phép public access cho các endpoint blockchain
                        .anyRequest().authenticated()  // Các yêu cầu còn lại yêu cầu xác thực
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Thêm JWT filter trước bộ lọc xác thực

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",  // Cấu hình cho localhost nếu đang phát triển
                "http://192.168.100.10:3000"  // Địa chỉ khác có thể thêm vào
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Cho phép các phương thức cần thiết
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "*"));  // Cho phép các header cần thiết
        configuration.setAllowCredentials(true);  // Cho phép credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Áp dụng CORS cho tất cả các endpoint
        return source;
    }

    // Cấu hình Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Mã hóa mật khẩu
    }

    // Cấu hình Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();  // Cung cấp authentication manager
    }

    // Cung cấp UserDetailsService cho Spring Security
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();  // Dịch vụ UserDetailsService của bạn
    }
}
