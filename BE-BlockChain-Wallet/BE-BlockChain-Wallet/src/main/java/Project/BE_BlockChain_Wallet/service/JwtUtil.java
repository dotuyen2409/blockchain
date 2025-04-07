package Project.BE_BlockChain_Wallet.service;

import Project.BE_BlockChain_Wallet.model.User;
import Project.BE_BlockChain_Wallet.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Component
public class JwtUtil {

    private final String secretKeyString = "Kuf8P7K8+XBrQ3XMOl2x6xVoMYK8SZBWo9h/xym/jiubAgnpqMbXVuKS230JyFwu";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());

    private static final long EXPIRATION_TIME = 86400000L; // 24 giờ

    // 🔹 Tạo JWT Token
    public String generateJwtToken(UserDetails userDetails) {
        User user = (User) userDetails; // Ép kiểu sang User để lấy roles
        Set<String> roles = user.getRoles(); // Lấy danh sách vai trò của người dùng

        return Jwts.builder()
                .setSubject(user.getEmail()) // Sử dụng email làm subject
                .setIssuedAt(new Date()) // Thời điểm phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Hạn sử dụng của token
                .claim("roles", roles)  // Thêm claim chứa danh sách roles
                .signWith(secretKey, SignatureAlgorithm.HS512) // Ký token bằng secret key
                .compact();
    }

    // 🔹 Lấy email từ JWT Token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();  // Trả về email
    }

    // 🔹 Kiểm tra tính hợp lệ của token
    public boolean validateToken(String token, UserDetails userDetails) {
        String email = extractUsername(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // 🔹 Kiểm tra token có hết hạn chưa
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // 🔹 Lấy toàn bộ claims từ token
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 🔹 Lấy thông tin người dùng từ token (dành cho API /user-info)
    public User extractUserInfo(String token, UserRepository userRepository) throws Exception {
        String email = extractUsername(token); // Lấy email từ token
        Optional<User> user = userRepository.findByEmail(email); // Lấy user từ email
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }
}
