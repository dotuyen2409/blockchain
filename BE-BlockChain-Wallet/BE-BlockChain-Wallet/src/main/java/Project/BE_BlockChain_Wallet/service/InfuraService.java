package Project.BE_BlockChain_Wallet.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

@Service
public class InfuraService {

    @Value("${infura.url}")
    private String infuraUrl;  // URL của Infura Node

    private Web3j web3j;

    private void initWeb3jClient() {
        if (infuraUrl != null && !infuraUrl.isEmpty()) {
            this.web3j = Web3j.build(new HttpService(infuraUrl));  // Khởi tạo Web3j client
        } else {
            throw new IllegalArgumentException("Infura URL is not configured.");
        }
    }

    // Lấy số dư ví Ethereum
    public BigDecimal getBalance(String walletAddress) throws Exception {
        if (web3j == null) {
            initWeb3jClient();
        }

        String balanceWei = web3j.ethGetBalance(walletAddress, org.web3j.protocol.core.DefaultBlockParameterName.LATEST)
                .send()
                .getBalance()
                .toString();

        return Convert.fromWei(new BigDecimal(balanceWei), Convert.Unit.ETHER);
    }

    // Gửi giao dịch Ethereum
    public String sendTransaction(String walletAddress, BigDecimal amount) throws Exception {
        // Logic gửi giao dịch qua Infura hoặc Web3j
        // Trả về ID giao dịch sau khi thực hiện thành công
        String transactionId = "TX123456";  // Đây chỉ là ví dụ, thực tế sẽ gọi Web3j để gửi giao dịch
        return transactionId;
    }

    // Kiểm tra trạng thái giao dịch
    public String checkTransactionStatus(String transactionId) throws Exception {
        // Logic kiểm tra trạng thái giao dịch trên blockchain
        // Trả về "Transaction confirmed" nếu giao dịch đã được xác nhận
        return "Transaction confirmed";  // Trả về trạng thái giao dịch (ví dụ)
    }
}
