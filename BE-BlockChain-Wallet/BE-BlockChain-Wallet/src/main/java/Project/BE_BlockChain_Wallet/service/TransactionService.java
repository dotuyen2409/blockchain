package Project.BE_BlockChain_Wallet.service;

import Project.BE_BlockChain_Wallet.dto.request.TransactionRequest;
import Project.BE_BlockChain_Wallet.dto.response.TransactionResponse;
import Project.BE_BlockChain_Wallet.model.MineHistory;
import Project.BE_BlockChain_Wallet.model.Transaction;
import Project.BE_BlockChain_Wallet.model.User;
import Project.BE_BlockChain_Wallet.repository.MineHistoryRepository;
import Project.BE_BlockChain_Wallet.repository.TransactionRepository;
import Project.BE_BlockChain_Wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private InfuraService infuraService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MineHistoryRepository mineHistoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Phương thức gửi giao dịch blockchain
    public TransactionResponse sendTransaction(TransactionRequest transactionRequest) {
        try {
            // Kiểm tra tham số
            if (transactionRequest == null || transactionRequest.getAmount() == null || transactionRequest.getWalletAddress() == null) {
                throw new Exception("Dữ liệu giao dịch không hợp lệ");
            }

            // Tìm user
            User user = userRepository.findByWalletAddress(transactionRequest.getWalletAddress())
                    .orElseThrow(() -> new Exception("Người dùng không tìm thấy"));

            // Kiểm tra số dư
            if (user.getBalance() == null) {
                user.setBalance(BigDecimal.ZERO);
            }

            BigDecimal balance = user.getBalance();
            if (balance.compareTo(transactionRequest.getAmount()) < 0) {
                throw new Exception("Số dư không đủ");
            }

            // Trừ số dư
            user.setBalance(balance.subtract(transactionRequest.getAmount()));
            userRepository.save(user);

            // Gọi InfuraService để thực hiện giao dịch
            String txId = infuraService.sendTransaction(transactionRequest.getWalletAddress(), transactionRequest.getAmount());
            // Tạo Transaction entity để lưu vào DB
            Transaction tx = new Transaction(
                    user,
                    transactionRequest.getAmount(),
                    "SEND",     // type = SEND
                    txId
            );
            transactionRepository.save(tx);

            // Tạo phản hồi
            return new TransactionResponse(
                    txId,
                    "Giao dịch thành công.",
                    tx.getTimestamp().toString(),
                    null);
        } catch (Exception e) {
            return new TransactionResponse("0", "Lỗi: " + e.getMessage(), null, null);
        }
    }
    // Phương thức nhận tiền từ quá trình "đào coin"
    public TransactionResponse mineCoinAndReceive(TransactionRequest transactionRequest) {
        try {
            if (transactionRequest == null || transactionRequest.getWalletAddress() == null) {
                throw new Exception("Dữ liệu giao dịch không hợp lệ");
            }

            // Kiểm tra nếu người dùng tồn tại
            User user = userRepository.findByWalletAddress(transactionRequest.getWalletAddress())
                    .orElseThrow(() -> new Exception("Người dùng không tìm thấy"));

            // Xử lý đào coin với số lượng ngẫu nhiên từ 1 đến 10 ETH
            BigDecimal minedAmount = new BigDecimal(1 + Math.random() * 9); // Tạo số ngẫu nhiên từ 1 đến 10
            BigDecimal newBalance = user.getBalance().add(minedAmount);
            user.setBalance(newBalance);

            userRepository.save(user);

            // Lưu lịch sử đào coin vào cơ sở dữ liệu
            MineHistory mineHistory = new MineHistory(minedAmount, java.time.LocalDateTime.now());
            mineHistory.setUser(user);
            mineHistoryRepository.save(mineHistory);

            String timestamp = mineHistory.getTimestamp().toString();  // Lấy timestamp của quá trình đào

            return new TransactionResponse("TX123456", "Số tiền đào được và đã được cộng vào số dư.", timestamp, null);
        } catch (Exception e) {
            return new TransactionResponse("0", "Lỗi: " + e.getMessage(), null, null);
        }
    }



//    // Phương thức nhận giao dịch blockchain
//    public TransactionResponse receiveTransaction(TransactionRequest transactionRequest) {
//        try {
//            String status = infuraService.checkTransactionStatus(transactionRequest.getTransactionId());
//            String timestamp = java.time.LocalDateTime.now().toString(); // Lấy timestamp của giao dịch
//            return new TransactionResponse(transactionRequest.getTransactionId(), status, timestamp);
//        } catch (Exception e) {
//            return new TransactionResponse("0", "Lỗi: " + e.getMessage(), null);
//        }
//    }

    // Phương thức lấy số dư của ví người dùng
    public TransactionResponse getBalance(String walletAddress) {
        try {
            User user = userRepository.findByWalletAddress(walletAddress)
                    .orElseThrow(() -> new Exception("Người dùng không tìm thấy"));

            BigDecimal balance = user.getBalance() != null ? user.getBalance() : BigDecimal.ZERO;
            String timestamp = java.time.LocalDateTime.now().toString(); // Lấy timestamp

            return new TransactionResponse("0", "Số dư: " + balance.toString(), timestamp, null);
        } catch (Exception e) {
            return new TransactionResponse("0", "Lỗi: " + e.getMessage(), null, null);
        }
    }

    // Phương thức lấy lịch sử giao dịch của ví người dùng
    public TransactionResponse getTransactionHistory(String walletAddress) {
        try {
            User user = userRepository.findByWalletAddress(walletAddress)
                    .orElseThrow(() -> new Exception("Người dùng không tìm thấy"));

            // Lấy danh sách giao dịch từ cơ sở dữ liệu
            List<Transaction> transactions = transactionRepository.findByUser(user);
            if (transactions.isEmpty()) {
                throw new Exception("Không có giao dịch nào");
            }
            // Sắp xếp giao dịch theo timestamp giảm dần (giao dịch mới nhất ở đầu)
            transactions.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));
            Transaction latestTx = transactions.get(0);
            Double amount = latestTx.getAmount() != null ? latestTx.getAmount().doubleValue() : 0.0;

            return TransactionResponse.withTransactionHistory(
                    latestTx.getTransactionId(),
                    "Thành Công !",
                    latestTx.getTimestamp().toString(),
                    amount
            );
        } catch (Exception e) {
            return new TransactionResponse("0", "Lỗi: " + e.getMessage(), null, null);
        }
    }



    // Phương thức lấy lịch sử đào coin của ví người dùng
    public TransactionResponse getMineHistory(String walletAddress) {
        try {
            if (walletAddress == null || walletAddress.isEmpty()) {
                return new TransactionResponse("0", "Wallet address is missing", null, null);
            }

            User user = userRepository.findByWalletAddress(walletAddress)
                    .orElseThrow(() -> new Exception("Người dùng không tìm thấy"));

            List<MineHistory> mineHistoryList = mineHistoryRepository.findByUser_WalletAddress(walletAddress);

            if (mineHistoryList.isEmpty()) {
                return new TransactionResponse("0", "Không có lịch sử đào coin", null, null);
            }

            // Chuyển lịch sử đào coin thành một chuỗi thông báo
            StringBuilder historyDetails = new StringBuilder();
            for (MineHistory mineHistory : mineHistoryList) {
                historyDetails.append("--Đào coin thành công! - Số lượng: ")
                        .append(mineHistory.getAmount())
                        .append(" ETH - Thời gian: ")
                        .append(mineHistory.getTimestamp())
                        .append("\n");
            }

            return new TransactionResponse("1", "Lịch sử đào coin:", historyDetails.toString(), null);
        } catch (Exception e) {
            return new TransactionResponse("0", "Lỗi: " + e.getMessage(), null, null);
        }
    }


}
