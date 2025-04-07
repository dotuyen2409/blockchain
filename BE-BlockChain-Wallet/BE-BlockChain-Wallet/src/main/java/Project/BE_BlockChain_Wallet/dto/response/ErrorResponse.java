package Project.BE_BlockChain_Wallet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message; // Thông điệp lỗi
    private String code;    // Mã lỗi (Ví dụ: BAD_REQUEST, INTERNAL_SERVER_ERROR)
}
