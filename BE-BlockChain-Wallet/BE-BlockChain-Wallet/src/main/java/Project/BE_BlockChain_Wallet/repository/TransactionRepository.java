package Project.BE_BlockChain_Wallet.repository;

import Project.BE_BlockChain_Wallet.model.Transaction;
import Project.BE_BlockChain_Wallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);  // Lấy giao dịch theo người dùng
}
