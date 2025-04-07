
// Hàm đăng ký người dùng
const API_URL = "http://localhost:8080/api";  // Địa chỉ backend của bạn

export const registerUser = async (email, password, username) => {
  const response = await fetch(`${API_URL}/auth/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      email,
      password,
      username,
    }),
  });

  const data = await response.json();  // Thay đổi từ text() thành json()

  console.log("Phản hồi từ API:", data);  // Kiểm tra phản hồi từ API

  // Nếu phản hồi là "User registered successfully", đăng ký thành công
  if (data.token && data.walletAddress) {
    localStorage.setItem("authToken", data.token);  // Lưu token vào localStorage
    localStorage.setItem("walletAddress", data.walletAddress);  // Lưu walletAddress vào localStorage
    return { message: "Đăng ký thành công!" };
  } else {
    throw new Error(data.message || "Đăng ký thất bại!");
  }
};

// Hàm đăng nhập người dùng
export const loginUser = async (email, password) => {
  const response = await fetch(`${API_URL}/auth/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      email,
      password,
    }),
  });

  if (!response.ok) {
    throw new Error('Đăng nhập thất bại!');
  }

  const data = await response.json();
  const token = data.token;  // Lưu token từ phản hồi API
  const walletAddress = data.walletAddress;  // Lấy walletAddress từ phản hồi API

  // Lưu token và walletAddress vào localStorage
  localStorage.setItem("authToken", token);
  localStorage.setItem("walletAddress", walletAddress);  // Lưu walletAddress vào localStorage

  return data;  // Trả về dữ liệu người dùng sau khi đăng nhập thành công
};


// Hàm lấy số dư ví
export const getBalance = async () => {
  const token = localStorage.getItem("authToken"); // Lấy token từ localStorage
  const walletAddress = localStorage.getItem("walletAddress");  // Lấy walletAddress từ localStorage
  if (!token || !walletAddress) throw new Error('Bạn chưa đăng nhập!');

  const response = await fetch(`${API_URL}/transactions/balance/${walletAddress}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error('Không thể lấy số dư ví!');
  }

  return response.json();
};

// Hàm gửi giao dịch
export const sendTransaction = async (walletAddress, amount) => {
  const token = localStorage.getItem("authToken"); // Lấy token từ localStorage
  if (!token || !walletAddress) throw new Error('Bạn chưa đăng nhập!');

  const response = await fetch(`${API_URL}/transactions/send-transaction`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({
      walletAddress,
      amount,
    }),
  });

  if (!response.ok) {
    throw new Error('Không thể gửi giao dịch!');
  }

  return response.json();
};

// Hàm "đào coin" và nhận tiền
export const mineCoin = async () => {
  const token = localStorage.getItem("authToken"); // Lấy token từ localStorage
  const walletAddress = localStorage.getItem("walletAddress");  // Lấy walletAddress từ localStorage
  if (!token || !walletAddress) throw new Error('Bạn chưa đăng nhập!');

  const response = await fetch(`${API_URL}/transactions/mine-coin`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({
      walletAddress,
    }),
  });

  if (!response.ok) {
    throw new Error('Không thể đào coin!');
  }

  return response.json();
};

// Hàm lấy lịch sử đào coin
export const getMineHistory = async (walletAddress) => {
  const token = localStorage.getItem("authToken"); // Lấy token từ localStorage
  if (!token) throw new Error('Bạn chưa đăng nhập!');

  const response = await fetch(`${API_URL}/transactions/mine-history/${walletAddress}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error('Không thể lấy lịch sử đào coin!');
  }

  // Ở đây backend trả về 1 object: { transactionId, status, timestamp }
  const data = await response.json();

  // Trả về object đó, để frontend xử lý hiển thị
  return data;
};

// Hàm lấy lịch sử giao dịch
export const getTransactionHistory = async (walletAddress) => {
  const token = localStorage.getItem("authToken");
  if (!token) throw new Error("Bạn chưa đăng nhập!");

  const response = await fetch(
    `${API_URL}/transactions/transaction-history/${walletAddress}`,
    {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  );

  if (!response.ok) {
    throw new Error("Không thể lấy lịch sử giao dịch!");
  }

  const data = await response.json();
  return data; // Trả về đối tượng JSON đầy đủ
};


