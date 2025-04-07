import React from "react";
import { Link } from "react-router-dom";  // Sử dụng Link để điều hướng
import '../styles/navbar.css'; // Import CSS vào Navbar

const Navbar = () => {
    const token = localStorage.getItem("authToken");

    return (
        <nav>
            <h1>MyEtherWallet</h1>
            <ul>
                <li><Link to="/dashboard">Dashboard</Link></li>
                <li><Link to="/mine-history">Mine History</Link></li>
                <li><Link to="/send-transaction">Send Transaction</Link></li>
                <li><Link to="/mine-coin">Main Coin</Link></li>
                <li><Link to="/balance">Check Balance</Link></li>
                <li><Link to="/transaction-history">Transaction History</Link></li>
                {/* Chỉ hiển thị Login/Register khi không có token */}
                {!token && <li><Link to="/login">Login</Link></li>}
                {!token && <li><Link to="/register">Register</Link></li>}
            </ul>
        </nav>
    );
};

export default Navbar;
