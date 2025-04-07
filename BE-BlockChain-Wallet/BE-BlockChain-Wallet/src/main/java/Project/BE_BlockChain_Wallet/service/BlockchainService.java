package Project.BE_BlockChain_Wallet.service;

import Project.BE_BlockChain_Wallet.dto.request.TransactionRequest;
import Project.BE_BlockChain_Wallet.dto.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockchainService {

    @Autowired
    private InfuraService infuraService;

    // Phương thức gửi giao dịch blockchain
    public TransactionResponse sendTransaction(TransactionRequest transactionRequest) {
        try {
            // Gửi giao dịch qua Infura (cập nhật với thực tế)
            // Giả sử InfuraService có phương thức gửi giao dịch
            String transactionId = infuraService.sendTransaction(transactionRequest.getWalletAddress(), transactionRequest.getAmount());

            // Lấy timestamp của giao dịch gửi
            String timestamp = java.time.LocalDateTime.now().toString();  // Lấy timestamp hiện tại

            return new TransactionResponse(transactionId, "Giao dịch thành công.", timestamp, null);
        } catch (Exception e) {
            // Xử lý lỗi và ghi log
            return new TransactionResponse("0", "Lỗi: " + e.getMessage(), null, null);
        }
    }

    // Phương thức nhận giao dịch blockchain
    public TransactionResponse receiveTransaction(TransactionRequest transactionRequest) {
        try {
            // Kiểm tra trạng thái giao dịch
            String status = infuraService.checkTransactionStatus(transactionRequest.getTransactionId());

            // Lấy timestamp của giao dịch nhận
            String timestamp = java.time.LocalDateTime.now().toString();  // Lấy timestamp hiện tại

            return new TransactionResponse(transactionRequest.getTransactionId(), status, timestamp, null);
        } catch (Exception e) {
            // Xử lý lỗi và ghi log
            return new TransactionResponse("0", "Lỗi: " + e.getMessage(), null, null);
        }
    }
}
