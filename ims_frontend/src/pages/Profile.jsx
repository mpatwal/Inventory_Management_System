import React, { useState, useEffect } from "react";
import Layout from "../component/Layout";
import APIService from "../service/APIService";

const Profile = () => {
  const [user, setUser] = useState(null);
  const [message, setMessage] = useState(null);

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const userInfo = await APIService.getLoggedInUser();
        setUser(userInfo);
      } catch (error) {
        setMessage(
          error.response?.data?.message ||
            "Failed to fetch user information. Please try again." + error
        );
      }
    };
    fetchUserInfo();
  }, []);
  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => setMessage(""), 3000);
  };

  return (
    <Layout>
      <div className="profile-container">
        {user ? (
          <>
            <h2 className="profile-title">Hello...., {user.name} </h2>
            {message && (
              <div className="alert alert-info text-center mb-2">{message}</div>
            )}
            <div className="profile-details">
              <p>
                <strong>Name:</strong> {user.name}
              </p>
              <p>
                <strong>Email:</strong> {user.email}
              </p>
              <p>
                <strong>Phone Number:</strong> {user.phoneNumber}
              </p>
              <p>
                <strong>Role:</strong> {user.role}
              </p>
            </div>
          </>
        ) : (
          <p>Loading user information...</p>
        )}
      </div>
    </Layout>
  );
};
export default Profile;
