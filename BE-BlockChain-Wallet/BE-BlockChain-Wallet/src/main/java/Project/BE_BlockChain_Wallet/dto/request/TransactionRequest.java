package Project.BE_BlockChain_Wallet.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String walletAddress; // Địa chỉ ví
    private BigDecimal amount;    // Số tiền
    private String transactionId; // ID giao dịch (dùng cho nhận giao dịch)
}
