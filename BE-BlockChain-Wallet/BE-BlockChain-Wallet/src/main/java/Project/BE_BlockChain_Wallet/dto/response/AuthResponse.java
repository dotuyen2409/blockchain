package Project.BE_BlockChain_Wallet.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    String token;
    String walletAddress;

    // Constructor chỉ với token (dùng khi login)
    public AuthResponse(String token) {
        this.token = token;
    }
}



