package Project.BE_BlockChain_Wallet.dto.response;

import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private String walletAddress;

    // Constructor với cả token và walletAddress
    public AuthResponse(String token, String walletAddress) {
        this.token = token;
        this.walletAddress = walletAddress;
    }

    // Constructor chỉ với token (dùng khi login)
    public AuthResponse(String token) {
        this.token = token;
    }
}



