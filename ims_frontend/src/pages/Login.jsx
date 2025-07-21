import React from "react";
import { useNavigate } from "react-router-dom";
import APIService from "../service/APIService";

const LoginPage = () => {
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [message, setMessage] = React.useState("");

  const navigate = useNavigate();
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const loginData = { email, password };
      const response = await APIService.loginUser(loginData);
      console.log(response);

      if (response.status === 200) {
        APIService.saveToken(response.token);
        APIService.saveRole(response.role);
        setMessage(response.message);
        navigate("/dashboard");
      }
    } catch (error) {
      showMessage(
        error.response?.data?.message ||
          "Login failed. Please try again." + error
      );
      console.log(error);
    }
  };

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => {
      setMessage("");
    }, 3000);
  };
  return (
    <div className="auth-container">
      <video autoPlay loop muted className="background-video">
        <source src="/video.mp4" type="video/mp4" />
        Your browser does not support the video tag.
      </video>
      <div className="auth-card">
        <div className="auth-header">
          <h2 className="auth-title">Login</h2>
          <p className="auth-subtitle">Access your account</p>
        </div>
        {message && (
          <div className="alert alert-info text-center mb-2">{message}</div>
        )}
        <form className="auth-form" onSubmit={handleLogin} autoComplete="off">
          <div className="form-group">
            <label className="form-label" htmlFor="login-email">
              Email
            </label>
            <input
              id="login-email"
              className="form-input"
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="login-password">
              Password
            </label>
            <input
              id="login-password"
              className="form-input"
              type="password"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button className="btn btn-primary w-full" type="submit">
            Login
          </button>
        </form>
        <div className="auth-link">
          Don't have an account? <a href="/register">Register</a>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
