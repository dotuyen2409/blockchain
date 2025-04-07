// src/pages/mine-history/MineHistory.js

import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { getMineHistory } from "../../service/api";  // API call
import "../../styles/MineHistory.css";

const MineHistory = () => {
  const [mineData, setMineData] = useState(null); // Lưu cả object trả về
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("authToken");
        const walletAddress = localStorage.getItem("walletAddress"); // Lưu walletAddress ở đâu đó

        if (!token || !walletAddress) {
          console.log("Chưa đăng nhập hoặc chưa có walletAddress");
          navigate("/login");
          return;
        }

        const data = await getMineHistory(walletAddress);
        console.log("Dữ liệu trả về từ API:", data);

        // data: { transactionId, status, timestamp }
        // timestamp: "Số lượng: 5.00 ETH ... \n Số lượng: 5.00 ETH ..."

        setMineData(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [navigate]);

  if (loading) {
    return <div>Đang tải...</div>;
  }

  if (error) {
    return <div>Lỗi: {error}</div>;
  }

  // Nếu mineData == null hoặc không có timestamp => không có dữ liệu
  if (!mineData || !mineData.timestamp) {
    return (
      <div className="mine-history-container">
        <h2>Lịch Sử Đào Coin</h2>
        <p>Không có lịch sử đào coin</p>
      </div>
    );
  }

  // Ngược lại, ta parse chuỗi timestamp thành các dòng
  // Giả sử backend gộp tất cả lịch sử thành 1 chuỗi, mỗi lần đào coin 1 dòng
  const lines = mineData.timestamp.split("\n").filter(line => line.trim() !== "");

  return (
    <div className="mine-history-container">
      <h2>Lịch Sử Đào Coin</h2>
      {/* Hiển thị status hoặc transactionId nếu cần */}
      <p><b>{mineData.status}</b> (transactionId: {mineData.transactionId})</p>

      {/* Hiển thị từng dòng */}
      <ul>
        {lines.map((line, idx) => (
          <li key={idx}>{line}</li>
        ))}
      </ul>
    </div>
  );
};

export default MineHistory;
