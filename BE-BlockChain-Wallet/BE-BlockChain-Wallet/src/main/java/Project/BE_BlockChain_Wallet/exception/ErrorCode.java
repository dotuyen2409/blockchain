package Project.BE_BlockChain_Wallet.exception;

public class ErrorCode {

    // Mã lỗi cho các trường hợp người dùng không tồn tại
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";

    // Mã lỗi cho trường hợp đăng nhập không thành công
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";

    // Mã lỗi cho giao dịch thất bại
    public static final String TRANSACTION_FAILED = "TRANSACTION_FAILED";

    // Mã lỗi cho trường hợp không đủ số dư trong ví
    public static final String INSUFFICIENT_FUNDS = "INSUFFICIENT_FUNDS";

    // Mã lỗi cho trường hợp truy cập bị từ chối
    public static final String ACCESS_DENIED = "ACCESS_DENIED";

    // Mã lỗi cho các lỗi không xác định
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    // Mã lỗi cho trường hợp yêu cầu không hợp lệ
    public static final String BAD_REQUEST = "BAD_REQUEST";
}