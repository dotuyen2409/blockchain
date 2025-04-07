package Project.BE_BlockChain_Wallet.controller;

import Project.BE_BlockChain_Wallet.dto.request.TransactionRequest;
import Project.BE_BlockChain_Wallet.dto.response.TransactionResponse;
import Project.BE_BlockChain_Wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // API để gửi tiền
    @PostMapping("/send-transaction")
    public ResponseEntity<TransactionResponse> sendTransaction(@RequestBody TransactionRequest transactionRequest) {
        try {
            TransactionResponse response = transactionService.sendTransaction(transactionRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new TransactionResponse("0", "Lỗi: " + e.getMessage(), null, null));
        }
    }

    // API để nhận tiền từ "đào coin"
    @PostMapping("/mine-coin")
    public ResponseEntity<TransactionResponse> mineCoin(@RequestBody TransactionRequest transactionRequest) {
        try {
            TransactionResponse response = transactionService.mineCoinAndReceive(transactionRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new TransactionResponse("0", "Lỗi: " + e.getMessage(), null, null));
        }
    }

//    // API để nhận giao dịch blockchain
//    @PostMapping("/receive")
//    public ResponseEntity<TransactionResponse> receiveTransaction(@RequestBody TransactionRequest transactionRequest) {
//        try {
//            TransactionResponse response = transactionService.receiveTransaction(transactionRequest);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(new TransactionResponse("0", "Lỗi: " + e.getMessage(), null));
//        }
//    }

    // API để lấy số dư của ví người dùng
    @GetMapping("/balance/{walletAddress}")
    public ResponseEntity<TransactionResponse> getBalance(@PathVariable("walletAddress") String walletAddress) {
        try {
            TransactionResponse response = transactionService.getBalance(walletAddress);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new TransactionResponse("0", "Lỗi: " + e.getMessage(), null, null));
        }
    }

    // API để lấy lịch sử giao dịch
    @GetMapping("/transaction-history/{walletAddress}")
    public ResponseEntity<?> getTransactionHistory(@PathVariable("walletAddress") String walletAddress) {
        try {
            // Gọi service lấy ra TransactionResponse (hoặc list TransactionResponse)
            TransactionResponse response = transactionService.getTransactionHistory(walletAddress);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new TransactionResponse("0", "Lỗi: " + e.getMessage(), null, null)
            );
        }
    }

    // API để lấy lịch sử đào coin của người dùng
    @GetMapping("/mine-history/{walletAddress}")
    public ResponseEntity<TransactionResponse> getMineHistory(@PathVariable("walletAddress") String walletAddress) {
        try {
            TransactionResponse response = transactionService.getMineHistory(walletAddress);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new TransactionResponse("0", "Lỗi: " + e.getMessage(), null, null));
        }
    }


}
