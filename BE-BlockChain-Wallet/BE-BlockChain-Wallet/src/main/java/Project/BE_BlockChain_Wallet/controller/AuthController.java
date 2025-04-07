package Project.BE_BlockChain_Wallet.controller;

import Project.BE_BlockChain_Wallet.dto.request.AuthRequest;
import Project.BE_BlockChain_Wallet.dto.response.AuthResponse;
import Project.BE_BlockChain_Wallet.model.User;
import Project.BE_BlockChain_Wallet.repository.UserRepository;
import Project.BE_BlockChain_Wallet.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;  // Tiêm phụ thuộc UserRepository vào đây


    // Đăng ký người dùng
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest authRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warn("Invalid input data for registration: {}", authRequest);
            return ResponseEntity.badRequest().body(new AuthResponse("Error: " + bindingResult.getAllErrors()));
        }

        try {
            // Đăng ký người dùng và tạo ví Ethereum
            AuthResponse authResponse = authService.registerUser(authRequest); // Trả về AuthResponse với token và walletAddress

            // Log thông tin đăng ký thành công
            logger.info("User registered successfully with email: {}", authRequest.getEmail());

            return ResponseEntity.ok(authResponse); // Trả về AuthResponse với token và walletAddress
        } catch (Exception e) {
            logger.error("Error during user registration for email: {}", authRequest.getEmail(), e);
            return ResponseEntity.badRequest().body(new AuthResponse("Error: " + e.getMessage()));
        }
    }

    // Đăng nhập và trả về token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warn("Invalid input data for login: {}", authRequest);
            return ResponseEntity.badRequest().body(new AuthResponse("Error: " + bindingResult.getAllErrors()));
        }

        try {
            String token = authService.authenticateUser(authRequest);

            // Lấy walletAddress từ userRepository sau khi đăng nhập
            User user = userRepository.findByEmail(authRequest.getEmail()).get();
            String walletAddress = user.getWalletAddress();

            logger.info("Generated Token for user: {}", authRequest.getEmail());

            // Trả về AuthResponse với token và walletAddress
            return ResponseEntity.ok(new AuthResponse(token, walletAddress));  // Trả về cả token và walletAddress
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", authRequest.getEmail(), e);
            return ResponseEntity.badRequest().body(new AuthResponse("Error: " + e.getMessage()));
        }
    }




    // Lấy thông tin người dùng từ token
    @GetMapping("/user-info")
    public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        try {
            // Lấy token từ header Authorization
            String token = authHeader.replace("Bearer ", "");
            User user = authService.getUserInfoFromToken(token); // Sử dụng service để lấy thông tin người dùng
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error fetching user info", e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
