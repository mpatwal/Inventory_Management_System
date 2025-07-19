import { useState, useEffect } from "react";
import Layout from "../component/Layout";
import APIService from "../service/APIService";
import { useNavigate } from "react-router-dom";

const Supplier = () => {
  const [suppliers, setSuppliers] = useState([]);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const getSuppliers = async () => {
      try {
        const response = await APIService.getAllSuppliers();
        console.log("Supplier API response:", response);
        // Try to handle both possible response formats
        if (response && (response.suppliers || response.data?.suppliers)) {
          setSuppliers(response.suppliers || response.data.suppliers);
        } else {
          showMessage(response.message || "No suppliers found.");
        }
      } catch (error) {
        setMessage(
          error.response?.data?.message ||
            "Failed to fetch suppliers. Please try again." + error
        );
        console.error(error);
      }
    };
    getSuppliers();
  }, []);

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => {
      setMessage("");
    }, 3000);
  };

  //Delete Supplier
  const handleDeleteSupplier = async (supplierId) => {
    try {
      if (window.confirm("Are you sure you want to delete this supplier?")) {
        await APIService.deleteSupplier(supplierId);
        window.location.reload();
      }
    } catch (error) {
      showMessage(
        error.response?.data?.message ||
          "Failed to delete supplier. Please try again." + error
      );
    }
  };

  return (
    <Layout>
      <h1>Supplier List</h1>
      {message && <div className="message">{message}</div>}
      <div className="supplier-page">
        <div className="supplier-header">
          <h1>Suppliers</h1>
          <div className="add-supplier">
            <button onClick={() => navigate("/add-supplier")}>
              Add Supplier
            </button>
          </div>
        </div>
      </div>
      {suppliers && (
        <ul className="supplier-list">
          {suppliers.map((supplier) => (
            <li key={supplier.id} className="supplier-item">
              <span>{supplier.name}</span>
              <div className="supplier-actions">
                <button
                  onClick={() => navigate(`/edit-supplier/${supplier.id}`)}
                >
                  Edit
                </button>
                <button onClick={() => handleDeleteSupplier(supplier.id)}>
                  Delete
                </button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </Layout>
  );
};
export default Supplier;
