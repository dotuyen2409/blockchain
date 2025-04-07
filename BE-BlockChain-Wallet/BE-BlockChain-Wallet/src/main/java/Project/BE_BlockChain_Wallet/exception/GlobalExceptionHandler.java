package Project.BE_BlockChain_Wallet.exception;

import Project.BE_BlockChain_Wallet.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý ngoại lệ tùy chỉnh
    @ExceptionHandler(AppException.class)
    public ResponseEntity<Object> handleAppException(AppException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), "BAD_REQUEST"), HttpStatus.BAD_REQUEST);
    }

    // Xử lý các lỗi toàn cục khác
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("Internal Server Error", "INTERNAL_SERVER_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
