import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/Balance.css";

const Balance = () => {
    const [balance, setBalance] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchBalance = async () => {
            const token = localStorage.getItem("authToken");
            const walletAddress = localStorage.getItem("walletAddress");  // Lấy walletAddress từ localStorage

            console.log("authToken: ", token);  // Kiểm tra giá trị token
            console.log("walletAddress: ", walletAddress);  // Kiểm tra giá trị walletAddress

            if (!token || !walletAddress) {
                console.log("Chưa đăng nhập hoặc không có walletAddress");
                navigate("/login");
                return;
            }

            try {
                const response = await fetch(`http://localhost:8080/api/transactions/balance/${walletAddress}`, {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                });

                if (response.ok) {
                    const data = await response.json();
                    console.log("Dữ liệu trả về từ API:", data); // Kiểm tra dữ liệu trả về từ API
                    setBalance(data.status);  // Gán giá trị số dư
                } else {
                    console.log("Không thể lấy số dư");
                    setBalance("Lỗi: Không thể lấy số dư");
                }
            } catch (error) {
                console.log("Lỗi: ", error);
                setBalance("Lỗi: Không thể kết nối tới máy chủ");
                navigate("/login");
            }
        };

        fetchBalance();
    }, [navigate]);

    return (
        <div>
            <h2>Số Dư Ví</h2>
            {balance ? (
                <p>{balance} ETH</p>
            ) : (
                <p>Đang tải số dư...</p>
            )}
        </div>
    );
};

export default Balance;
