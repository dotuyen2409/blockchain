import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";  // Import useNavigate
import { mineCoin } from "../../service/api";
import "../../styles/MineCoin.css";

const MineCoin = () => {
  const [walletAddress, setWalletAddress] = useState("");
  const [message, setMessage] = useState(null);
  const navigate = useNavigate();  // Khởi tạo hook navigate
  const token = localStorage.getItem("authToken");  // Lấy token từ localStorage

  // Kiểm tra nếu không có token, chuyển đến trang đăng nhập
  useEffect(() => {
    if (!token) {
      navigate("/login");
    }
  }, [token, navigate]);

  const handleMineCoin = async (e) => {
    e.preventDefault();

    // Kiểm tra token
    if (!token) {
      navigate("/login");  // Nếu không có token, chuyển đến trang login
      return;
    }

    try {
      const response = await mineCoin(walletAddress, token);
      if (response.message === "Amount mined and added to balance.") {
        setMessage("Đã đào coin thành công! Số tiền đã được cộng vào ví.");
      } else {
        setMessage(response.status);
      }
    } catch (error) {
      setMessage("Đào coin thất bại, thử lại!");
    }
  };

  return (
    <div>
      <h2>Đào Coin và Nhận Tiền</h2>
      {message && <div>{message}</div>}
      <form onSubmit={handleMineCoin}>
        <input
          type="text"
          placeholder="Địa chỉ ví"
          value={walletAddress}
          onChange={(e) => setWalletAddress(e.target.value)}
          required
        />
        <button type="submit">Đào Coin</button>
      </form>
    </div>
  );
};

export default MineCoin;
