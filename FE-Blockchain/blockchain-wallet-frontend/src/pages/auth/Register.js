import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/Register.css";
import { registerUser } from "../../service/api";  // Import hàm registerUser từ API

const Register = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [username, setUsername] = useState("");  // Thêm state cho username
    const [error, setError] = useState("");  // State để lưu thông báo lỗi
    const navigate = useNavigate();

    const handleRegister = async () => {
      if (email && password && username) {
        try {
          const data = await registerUser(email, password, username);

          if (data.message === "Đăng ký thành công!") {
            navigate("/login");  // Sau khi đăng ký thành công, chuyển hướng đến trang đăng nhập
          }
        } catch (error) {
          setError(error.message || "Đăng ký thất bại!");
        }
      } else {
        setError("Vui lòng điền đầy đủ thông tin");
      }
    };

    return (
        <div className="register-container">
            <div className="register-form">
                <h2>Sign up</h2>
                {/* Hiển thị thông báo lỗi nếu có */}
                {error && <div className="error-message">{error}</div>}

                <form onSubmit={(e) => e.preventDefault()}>
                    <div className="input-group">
                        <input
                            type="text"
                            placeholder="Username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}  // Xử lý nhập username
                        />
                    </div>
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
                    <button type="button" onClick={handleRegister}>Đăng Ký</button>
                </form>
            </div>
        </div>
    );
};

export default Register;
