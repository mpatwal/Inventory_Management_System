import React from "react";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const navigate = useNavigate();

  return (
    <div className="home-container">
      {/* 🎬 Background Video */}
      <video autoPlay loop muted className="background-video">
        <source src="/video.mp4" type="video/mp4" />
        Your browser does not support the video tag.
      </video>

      {/* 🔷 Overlay Content */}
      <div className="overlay">
        <h1>Smart Inventory Management System</h1>
        <p className="subtitle">
          Streamline your inventory. Boost efficiency. Empower decision-making.
        </p>

        <div className="features">
          <div>✔ Real-time Stock Updates</div>
          <div>✔ Smart Purchase & Sales Tracking</div>
          <div>✔ Supplier & Product Insights</div>
        </div>

        <div className="buttons">
          <button onClick={() => navigate("/register")}>Register</button>
          <button onClick={() => navigate("/login")}>Login</button>
        </div>
      </div>

      {/* 💅 Embedded CSS */}
      <style>{`
        .home-container {
          position: relative;
          height: 100vh;
          overflow: hidden;
          font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .background-video {
          position: absolute;
          width: 100%;
          height: 100%;
          object-fit: cover;
          top: 0;
          left: 0;
          z-index: -1;
        }
        .overlay {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%, -50%);
          text-align: center;
          color: #fff;
          background: rgba(0, 0, 0, 0.6);
          padding: 40px;
          border-radius: 16px;
          width: 90%;
          max-width: 700px;
        }
        h1 {
          font-size: 3rem;
          margin-bottom: 0.5rem;
        }
        .subtitle {
          font-size: 1.3rem;
          margin-bottom: 2rem;
          color: #f1f1f1;
        }
        .features {
          display: flex;
          flex-direction: column;
          gap: 10px;
          font-size: 1.1rem;
          margin-bottom: 2rem;
        }
        .features div::before {
          content: "🛠️ ";
        }
        .buttons button {
          margin: 0 12px;
          padding: 12px 25px;
          font-size: 1rem;
          border: none;
          border-radius: 6px;
          cursor: pointer;
          background-color: #00b894;
          color: white;
          transition: background 0.3s ease;
        }
        .buttons button:hover {
          background-color: #019875;
        }
        @media (max-width: 600px) {
          .overlay {
            padding: 20px;
          }
          h1 {
            font-size: 2rem;
          }
          .subtitle {
            font-size: 1rem;
          }
          .buttons button {
            margin: 6px;
            width: 100px;
          }
        }
      `}</style>
    </div>
  );
};

export default Home;
