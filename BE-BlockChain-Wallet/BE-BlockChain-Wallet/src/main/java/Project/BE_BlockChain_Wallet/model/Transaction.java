package Project.BE_BlockChain_Wallet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Mối quan hệ với User để lấy walletAddress

    private BigDecimal amount;
    private String type;
    private LocalDateTime timestamp;

    @Column(name = "transaction_id")
    private String transactionId;

    // Constructor không tham số
    public Transaction() {}

    // Constructor để dễ dàng tạo giao dịch
    public Transaction(User user, BigDecimal amount, String type, String transactionId) {
        this.user = user;
        this.amount = amount;
        this.type = type;
        this.timestamp = LocalDateTime.now();
        this.transactionId = transactionId;
    }

    // Getter và Setter

    // Phương thức lấy walletAddress từ User
    public String getWalletAddress() {
        return this.user != null ? this.user.getWalletAddress() : null;
    }
}
