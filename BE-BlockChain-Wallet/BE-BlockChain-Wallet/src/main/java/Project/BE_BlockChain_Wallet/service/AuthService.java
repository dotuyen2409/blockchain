package Project.BE_BlockChain_Wallet.service;

import Project.BE_BlockChain_Wallet.dto.request.AuthRequest;
import Project.BE_BlockChain_Wallet.dto.response.AuthResponse;
import Project.BE_BlockChain_Wallet.model.User;
import Project.BE_BlockChain_Wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EthereumWalletService ethereumWalletService;

    // Đăng ký người dùng
    public AuthResponse registerUser(AuthRequest authRequest) throws Exception {
        if (userRepository.existsByEmail(authRequest.getEmail())) {
            throw new Exception("User already exists");
        }

        User user = new User();
        user.setEmail(authRequest.getEmail());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setUsername(authRequest.getUsername());
        user.setBalance(BigDecimal.ZERO); // Gán giá trị mặc định cho balance

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");  // Mặc định user có ROLE_USER
        user.setRoles(roles);

        // Tạo ví Ethereum cho người dùng
        String walletAddress = ethereumWalletService.createNewWallet();
        user.setWalletAddress(walletAddress);

        userRepository.save(user);

        // Tạo token JWT sau khi người dùng đăng ký thành công
        String token = jwtUtil.generateJwtToken(user);

        // Trả về AuthResponse với token và walletAddress
        return new AuthResponse(token, walletAddress);
    }

    // Đăng nhập và lấy JWT token
    public String authenticateUser(AuthRequest authRequest) throws Exception {
        Optional<User> user = userRepository.findByEmail(authRequest.getEmail());
        if (user.isPresent() && passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())) {
            return jwtUtil.generateJwtToken(user.get());
        } else {
            throw new Exception("Invalid email or password");
        }
    }

    public User getUserInfoFromToken(String token) throws Exception {
        String email = jwtUtil.extractUsername(token);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }
}
