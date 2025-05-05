package Project.BE_BlockChain_Wallet.controller;

import Project.BE_BlockChain_Wallet.service.InfuraService;
import jnr.constants.platform.solaris.Access;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j // Sử dụng Lombok để tự động tạo logger
@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {

    @Autowired
    InfuraService infuraService;

    // API để lấy số dư ví Ethereum
    @GetMapping("/balance/{walletAddress}")
    public ResponseEntity<String> getBalance(@PathVariable("walletAddress") String walletAddress) {
        try {
            BigDecimal balance = infuraService.getBalance(walletAddress);
            return ResponseEntity.ok("Balance: " + balance.toString() + " ETH");
        } catch (Exception e) {
            // Log lỗi nếu có
            log.error("Error retrieving balance for walletAddress: " + walletAddress, e);
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
