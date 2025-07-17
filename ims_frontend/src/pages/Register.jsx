import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import ApiService from "../service/APIService";

const RegisterPage = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const registrationData = { name, email, password, phoneNumber };
      await ApiService.registerUser(registrationData);
      setMessage("Registration successful! Redirecting to login page...");
      navigate("/login");
    } catch (error) {
      showMessage(
        error.response?.data?.message ||
          "Registration failed. Please try again." + error
      );
      console.log(error);
    }
  };
  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(""), 3000);
  };
  return (
    <div className="auth-container">
      <div className="auth-card">
        <div className="auth-header">
          <h2 className="auth-title">Register</h2>
          <p className="auth-subtitle">Create your account to get started</p>
        </div>
        {message && (
          <div className="alert alert-info text-center mb-2">{message}</div>
        )}
        <form
          className="auth-form"
          onSubmit={handleRegister}
          autoComplete="off"
        >
          <div className="form-group">
            <label className="form-label" htmlFor="register-name">
              Name
            </label>
            <input
              id="register-name"
              className="form-input"
              type="text"
              placeholder="Enter your name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="register-email">
              Email
            </label>
            <input
              id="register-email"
              className="form-input"
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="register-password">
              Password
            </label>
            <input
              id="register-password"
              className="form-input"
              type="password"
              placeholder="Create a password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="register-phone">
              Phone Number
            </label>
            <input
              id="register-phone"
              className="form-input"
              type="text"
              placeholder="Enter your phone number"
              value={phoneNumber}
              onChange={(e) => setPhoneNumber(e.target.value)}
              required
            />
          </div>
          <button className="btn btn-primary w-full" type="submit">
            Register
          </button>
        </form>
        <div className="auth-link">
          Already have an account? <a href="/login">Login here</a>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;
