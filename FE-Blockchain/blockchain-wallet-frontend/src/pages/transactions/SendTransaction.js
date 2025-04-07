import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { sendTransaction } from "../../service/api";  // Giả sử bạn có hàm API để gửi giao dịch
import "../../styles/SendTransaction.css";

const SendTransaction = () => {
    const [amount, setAmount] = useState("");
    const [walletAddress, setWalletAddress] = useState("");  // Địa chỉ ví người nhận
    const [transactionResult, setTransactionResult] = useState(null);  // Dùng để lưu kết quả giao dịch
    const [isSubmitting, setIsSubmitting] = useState(false);  // Kiểm tra khi giao dịch đang được gửi
    const navigate = useNavigate();

    useEffect(() => {
        // Kiểm tra xem người dùng có token trong localStorage hay không
        const token = localStorage.getItem("authToken");
        const storedWalletAddress = localStorage.getItem("walletAddress");  // Lấy địa chỉ ví từ localStorage

        if (!token) {
            // Nếu không có token, chuyển hướng về trang login
            navigate("/login");
        }

        if (storedWalletAddress) {
            setWalletAddress(storedWalletAddress);  // Tự động điền địa chỉ ví nếu có trong localStorage
        }
    }, [navigate]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem("authToken");

        if (!token) {
            navigate("/login");
            return;
        }

        if (!amount || !walletAddress) {
            setTransactionResult({ status: "Vui lòng điền đầy đủ thông tin!" });
            return;
        }

        setIsSubmitting(true);  // Đánh dấu bắt đầu quá trình gửi giao dịch

        try {
            const response = await sendTransaction(walletAddress, amount);  // Gửi giao dịch

            // Định dạng thời gian nếu có
            const formattedTime = new Date(response.timestamp).toLocaleString();  // Chuyển timestamp thành thời gian dễ đọc

            setTransactionResult({
                ...response,
                formattedTimestamp: formattedTime,
            });  // Lưu kết quả và thêm thời gian đã định dạng
        } catch (error) {
            console.error("Lỗi gửi giao dịch:", error);
            setTransactionResult({ status: "Lỗi: Không thể gửi giao dịch!" });  // Hiển thị lỗi
        } finally {
            setIsSubmitting(false);  // Đánh dấu quá trình gửi giao dịch kết thúc
        }
    };

    return (
        <div>
            <h2>Gửi Giao Dịch</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Địa chỉ ví:</label>
                    <input
                        type="text"
                        value={walletAddress}
                        onChange={(e) => setWalletAddress(e.target.value)}
                        placeholder="Nhập địa chỉ ví"
                    />
                </div>
                <div>
                    <label>Số tiền:</label>
                    <input
                        type="number"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                        placeholder="Nhập số tiền"
                    />
                </div>
                <button type="submit" disabled={isSubmitting}>Gửi</button>  {/* Vô hiệu hóa nút khi đang gửi */}
            </form>

            {transactionResult && (
                <div>
                    <h3>Kết quả giao dịch:</h3>
                    {transactionResult.status ? (
                        <p>{transactionResult.status}</p>  // Hiển thị thông báo lỗi nếu có
                    ) : (
                        <div>
                            <p>ID Giao Dịch: {transactionResult.transactionId}</p>
                            <p>Trạng Thái: {transactionResult.status}</p>
                            <p>Thời Gian: {transactionResult.formattedTimestamp}</p>  {/* Hiển thị thời gian đã định dạng */}
                        </div>
                    )}
                </div>
            )}
        </div>
    );
};

export default SendTransaction;
