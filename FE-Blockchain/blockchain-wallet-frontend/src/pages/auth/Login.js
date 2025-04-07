import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/Login.css";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleLogin = async () => {
        if (email && password) {
            try {
                const response = await fetch("http://localhost:8080/api/auth/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ email, password }),
                });

                if (response.ok) {
                    const data = await response.json();
                    const token = data.token;
                    const walletAddress = data.walletAddress; // Giả sử API trả về walletAddress

                    if (!walletAddress) {
                        console.log("Lỗi: walletAddress không có trong phản hồi từ API.");
                        return;
                    }

                    // Lưu token và walletAddress vào localStorage
                    localStorage.setItem("authToken", token);
                    localStorage.setItem("walletAddress", walletAddress); // Lưu walletAddress vào localStorage

                    navigate("/dashboard");  // Chuyển hướng đến trang Dashboard sau khi đăng nhập thành công
                } else {
                    console.log("Đăng nhập thất bại");
                }
            } catch (error) {
                console.log("Lỗi đăng nhập: ", error);
            }
        } else {
            console.log("Email hoặc mật khẩu không hợp lệ");
        }
    };

    const goToRegister = () => {
        navigate("/register");  // Chuyển hướng đến trang đăng ký
    };

    return (
        <div className="login-container">
            <div className="login-form">
                <h2>Login Page</h2>
                <div className="input-group">
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <div className="input-group">
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button onClick={handleLogin}>Login</button>
                <button className="register-button" onClick={goToRegister}>Register</button>
            </div>
        </div>
    );
};

export default Login;
