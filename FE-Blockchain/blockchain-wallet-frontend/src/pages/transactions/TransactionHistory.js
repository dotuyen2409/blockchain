import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { getTransactionHistory } from "../../service/api";
import "../../styles/TransactionHistory.css";

const TransactionHistory = () => {
  const [transaction, setTransaction] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchTransactionData = async () => {
      const token = localStorage.getItem("authToken");
      const walletAddress = localStorage.getItem("walletAddress");

      if (!token || !walletAddress) {
        navigate("/login");
        return;
      }

      try {
        const data = await getTransactionHistory(walletAddress);
        console.log("Dữ liệu transaction:", data);

        if (data && data.transactionId) {
          setTransaction(data);
        } else {
          throw new Error("Không có giao dịch nào.");
        }
      } catch (err) {
        setError("Lỗi: " + err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchTransactionData();
  }, [navigate]);

  if (loading) {
    return <div className="loading-message">Đang tải...</div>;
  }

  if (error) {
    return <div className="error-message">{error}</div>;
  }

  const walletAddress = localStorage.getItem("walletAddress");

  return (
    <div className="transaction-history-container">
      <h2>Lịch sử giao dịch</h2>
      {transaction ? (
        <div className="transaction-item">
          <p><span>ID giao dịch:</span> {transaction.transactionId}</p>
          <p><span>Trạng thái:</span> {transaction.status}</p>
          <p><span>Thời gian:</span> {transaction.timestamp}</p>
          <p><span>Địa chỉ ví:</span> {walletAddress}</p>
          <p><span>Số tiền:</span> {transaction.amount} ETH</p>
        </div>
      ) : (
        <p>Không có giao dịch nào.</p>
      )}
    </div>
  );
};

export default TransactionHistory;
