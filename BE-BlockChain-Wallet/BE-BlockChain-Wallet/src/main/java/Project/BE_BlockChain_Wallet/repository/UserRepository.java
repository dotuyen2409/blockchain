package Project.BE_BlockChain_Wallet.repository;

import Project.BE_BlockChain_Wallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm kiếm người dùng theo email
    Optional<User> findByEmail(String email);

    // Tìm kiếm người dùng theo địa chỉ ví walletAddress
    Optional<User> findByWalletAddress(String walletAddress);

    // Tìm kiếm người dùng theo tên người dùng
    Optional<User> findByUsername(String username);

    // Kiểm tra người dùng có tồn tại theo email không
    boolean existsByEmail(String email);
}
