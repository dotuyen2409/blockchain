package Project.BE_BlockChain_Wallet.dto.response;

import lombok.Data;

@Data
public class TransactionResponse {
    private String transactionId;  // ID giao dịch
    private String status;         // Trạng thái giao dịch
    private String timestamp;      // Thời gian giao dịch
    private String walletAddress;  // Địa chỉ ví
    private Double amount;         // Số tiền giao dịch

    // Constructor đầy đủ cho giao dịch
    public TransactionResponse(String transactionId, String status, String timestamp, String walletAddress, Double amount) {
        this.transactionId = transactionId;
        this.status = status;
        this.timestamp = timestamp;
        this.walletAddress = walletAddress;
        this.amount = amount;
    }

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
