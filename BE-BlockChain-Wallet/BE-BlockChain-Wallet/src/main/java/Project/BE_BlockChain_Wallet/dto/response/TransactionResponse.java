package Project.BE_BlockChain_Wallet.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransactionResponse {
    String transactionId;  // ID giao dịch
    String status;         // Trạng thái giao dịch
    String timestamp;      // Thời gian giao dịch
    String walletAddress;  // Địa chỉ ví
    Double amount;         // Số tiền giao dịch

    // Constructor cho các trường hợp chỉ cần thông báo (không có walletAddress và amount)
    public TransactionResponse(String transactionId, String status, String timestamp, Object o) {
        this(transactionId, status, timestamp, null, null);
    }

    // Phương thức tĩnh để tạo TransactionResponse cho lịch sử giao dịch
    public static TransactionResponse withTransactionHistory(String transactionId, String status, String timestamp, Double amount) {
        return new TransactionResponse(transactionId, status, timestamp, null, amount);
    }

    // Phương thức tĩnh để tạo TransactionResponse cho lịch sử đào coin
    public static TransactionResponse withMineHistory(String transactionId, String status, String timestamp, String walletAddress, Double amount) {
        return new TransactionResponse(transactionId, status, timestamp, walletAddress, amount);
    }
}
