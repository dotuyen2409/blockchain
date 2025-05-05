package Project.BE_BlockChain_Wallet.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class TransactionRequest {
    String walletAddress; // Địa chỉ ví
    BigDecimal amount;    // Số tiền
    String transactionId; // ID giao dịch (dùng cho nhận giao dịch)
}
