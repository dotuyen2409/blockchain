package Project.BE_BlockChain_Wallet.service;

import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Service
public class EthereumWalletService {

    // Phương thức nhận ví Ethereum từ private key
    public String getAddressFromPrivateKey(String privateKey) throws IllegalArgumentException {
        try {
            // Tạo đối tượng Credentials từ private key
            Credentials credentials = Credentials.create(privateKey);

            // Lấy địa chỉ ví từ Credentials
            return credentials.getAddress();
        } catch (Exception e) {
            // Xử lý lỗi nếu private key không hợp lệ
            throw new IllegalArgumentException("Invalid private key", e);
        }
    }

    // Phương thức để tạo ví Ethereum mới (bao gồm tạo private key và địa chỉ ví)
    public String createNewWallet() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        // Tạo private key mới và lấy địa chỉ ví
        String privateKey = Keys.createEcKeyPair().getPrivateKey().toString(16);
        Credentials credentials = Credentials.create(privateKey);

        return credentials.getAddress();
    }

    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        EthereumWalletService walletService = new EthereumWalletService();

        // Ví dụ sử dụng private key có sẵn
        String privateKey = "7210aae4dda4e9f395c81752781b0abc3d346ada83711e42b53459a0ff874123";  // Thay thế bằng private key của bạn

        try {
            // Lấy địa chỉ ví từ private key
            String walletAddress = walletService.getAddressFromPrivateKey(privateKey);
            System.out.println("Wallet Address: " + walletAddress);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Tạo ví mới và in địa chỉ ví
        String newWalletAddress = walletService.createNewWallet();
        System.out.println("New Wallet Address: " + newWalletAddress);
    }
}
