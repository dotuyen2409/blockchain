import React, { useEffect, useState } from "react";
import { Routes, Route, Navigate } from "react-router-dom";  // Import Navigate
import Dashboard from "./pages/dashboard/Dashboard";
import MineHistory from "./pages/mine-history/MineHistory";
import SendTransaction from "./pages/transactions/SendTransaction";
import BalancePage from "./pages/balance/BalancePage";
import MineCoin from "./pages/transactions/MineCoin";
import TransactionHistory from "./pages/transactions/TransactionHistory";
import Navbar from "./components/Navbar";
import Login from "./pages/auth/Login";
import Register from "./pages/auth/Register";

function App() {
    const [token, setToken] = useState(null);

    useEffect(() => {
        const savedToken = localStorage.getItem("authToken");
        setToken(savedToken); // Lưu token từ localStorage
    }, []);

    return (
        <>
            <Navbar />
            <Routes>
                <Route path="/dashboard" element={token ? <Dashboard /> : <Navigate to="/login" />} />
                <Route path="/mine-history" element={token ? <MineHistory /> : <Navigate to="/login" />} />
                <Route path="/send-transaction" element={token ? <SendTransaction /> : <Navigate to="/login" />} />
                <Route path="/balance" element={token ? <BalancePage /> : <Navigate to="/login" />} />
                <Route path="/mine-coin" element={token ? <MineCoin /> : <Navigate to="/login" />} />
                <Route path="/transaction-history" element={token ? <TransactionHistory /> : <Navigate to="/login" />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
            </Routes>
        </>
    );
}

export default App;
