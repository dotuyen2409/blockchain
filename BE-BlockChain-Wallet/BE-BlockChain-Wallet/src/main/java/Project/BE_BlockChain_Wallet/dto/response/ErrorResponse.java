package Project.BE_BlockChain_Wallet.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
public class ErrorResponse {
    String message; // Thông điệp lỗi
    String code;    // Mã lỗi (Ví dụ: BAD_REQUEST, INTERNAL_SERVER_ERROR)
}
