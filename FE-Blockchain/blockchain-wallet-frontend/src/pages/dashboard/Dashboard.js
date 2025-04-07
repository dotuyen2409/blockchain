import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/Dashboard.css";

const Dashboard = () => {
    const [userData, setUserData] = useState(null);
    const navigate = useNavigate();  // Hook navigate

    useEffect(() => {
        const fetchUserData = async () => {
            const token = localStorage.getItem("authToken"); // Kiểm tra token
            if (token) {
                try {
                    const response = await fetch("http://localhost:8080/api/auth/user-info", {
                        method: "GET",
                        headers: {
                            "Authorization": `Bearer ${token}`,
                            "Content-Type": "application/json",
                        },
                    });

                    if (response.ok) {
                        const data = await response.json();
                        setUserData(data); // Lưu thông tin người dùng vào state
                    } else {
                        console.log("Không thể lấy thông tin người dùng");
                        navigate("/login");  // Nếu không thể lấy dữ liệu, chuyển về trang đăng nhập
                    }
                } catch (error) {
                    console.log("Lỗi: ", error);
                    navigate("/login");  // Nếu gặp lỗi trong quá trình fetch, chuyển về trang login
                }
            } else {
                console.log("Chưa đăng nhập");
                navigate("/login");  // Nếu không có token, chuyển đến login
            }
        };

        fetchUserData();
    }, [navigate]);

    const goToBalance = () => navigate("/balance");
    const goToSendTransaction = () => navigate("/send-transaction");
    const goToMineCoin = () => navigate("/mine-coin");
    const goToMineHistory = () => navigate("/mine-history");
    const goToTransactionHistory = () => navigate("/transaction-history");

    // Hàm đăng xuất
    const handleLogout = () => {
        localStorage.removeItem("authToken"); // Xóa token khỏi localStorage
        localStorage.removeItem("walletAddress"); // Xóa walletAddress khỏi localStorage nếu cần
        navigate("/login"); // Chuyển hướng về trang đăng nhập
    };

    if (!userData) {
        return <div>Loading...</div>;  // Khi chưa có thông tin người dùng, hiển thị "Loading..."
    }

    const walletAddress = localStorage.getItem("walletAddress"); // Lấy walletAddress từ localStorage

    return (
        <div className="dashboard-container">
            <h2>Trang Chủ</h2>
            <div className="user-info">
                <p>Name: {userData.username}</p>
                <p>Email: {userData.email}</p>
                <p>WalletAddress: {walletAddress}</p>  {/* Hiển thị địa chỉ ví */}
                <p>Balance: ***** ETH</p>
            </div>

            <div className="dashboard-buttons">
                <button onClick={goToBalance}>Kiểm Tra Số Dư</button>
                <button onClick={goToSendTransaction}>Gửi Tiền</button>
                <button onClick={goToMineCoin}>Đào Coin</button>
                <button onClick={goToMineHistory}>Lịch Sử Đào Coin</button>
                <button onClick={goToTransactionHistory}>Lịch Sử Giao Dịch</button>
                <button onClick={handleLogout}>Đăng Xuất</button> {/* Nút đăng xuất */}
            </div>
        </div>
    );
};

export default Dashboard;
