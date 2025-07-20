import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import Layout from "../component/Layout";
import APIService from "../service/APIService";
import { useNavigate } from "react-router-dom";

const AddEditSupplier = () => {
  const { supplierId } = useParams();
  const [name, setName] = useState("");
  const [contactInfo, setContactInfo] = useState("");
  const [address, setAddress] = useState("");
  const [message, setMessage] = useState("");
  const [isEditing, setIsEditing] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    if (supplierId) {
      setIsEditing(true);
      const fetchSupplier = async () => {
        try {
          const supplierData = await APIService.getSupplierById(supplierId);
          if (supplierData.status === 200) {
            setName(supplierData.supplier.name);
            setContactInfo(supplierData.supplier.contactInfo);
            setAddress(supplierData.supplier.address);
          }
        } catch (error) {
          showMessage(
            error.response?.data?.message ||
              "Failed to fetch supplier details. Please try again." + error
          );
        }
      };
      fetchSupplier();
    }
  }, [supplierId]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const supplierData = {
      name,
      contactInfo,
      address,
    };
    try {
      if (isEditing) {
        await APIService.updateSupplier(supplierId, supplierData);
        showMessage("Supplier updated successfully.");
      } else {
        await APIService.addSupplier(supplierData);
        showMessage("Supplier added successfully.");
      }
      navigate("/supplier");
    } catch (error) {
      showMessage(
        error.response?.data?.message ||
          "Failed to save supplier. Please try again." + error
      );
    }
  };

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => {
      setMessage("");
    }, 3000);
  };

  return (
    <Layout>
      {message && <div className="message">{message}</div>}
      <div className="supplier-form">
        <h1>{isEditing ? "Edit Supplier" : "Add Supplier"}</h1>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Supplier Name</label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="contactInfo">Contact Information</label>
            <input
              type="text"
              id="contactInfo"
              value={contactInfo}
              onChange={(e) => setContactInfo(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="address">Address</label>
            <input
              type="text"
              id="address"
              value={address}
              onChange={(e) => setAddress(e.target.value)}
              required
            />
          </div>
          <button type="submit">
            {isEditing ? "Update Supplier" : "Add Supplier"}
          </button>
        </form>
      </div>
    </Layout>
  );
};

export default AddEditSupplier;
