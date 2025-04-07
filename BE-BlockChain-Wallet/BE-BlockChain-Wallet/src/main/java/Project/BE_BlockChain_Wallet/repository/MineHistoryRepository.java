package Project.BE_BlockChain_Wallet.repository;

import Project.BE_BlockChain_Wallet.model.MineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MineHistoryRepository extends JpaRepository<MineHistory, Long> {
    // Tìm lịch sử đào coin theo walletAddress (phương thức không phải static)
    List<MineHistory> findByUser_WalletAddress(String walletAddress);
}
