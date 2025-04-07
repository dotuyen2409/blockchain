// src/components/BalanceComponent.js

import React, { useState, useEffect } from "react";
import { getBalance } from "../service/api";

const BalanceComponent = ({ walletAddress }) => {
  const [balance, setBalance] = useState(null);
  const [error, setError] = useState(null);
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchBalance = async () => {
      try {
        const response = await getBalance(walletAddress, token);
        if (response.balance) {
          setBalance(response.balance);
        } else {
          setError(response.message);
        }
      } catch (error) {
        setError("Không thể lấy số dư, thử lại!");
      }
    };

    if (walletAddress) {
      fetchBalance();
    }
  }, [walletAddress, token]);

  return (
    <div>
      {error && <div>{error}</div>}
      {balance !== null && <div>{`Balance: ${balance} ETH`}</div>}
    </div>
  );
};

export default BalanceComponent;
