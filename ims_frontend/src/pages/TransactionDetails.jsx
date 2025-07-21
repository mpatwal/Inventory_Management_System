import React, { useState, useEffect } from "react";
import Layout from "../component/Layout";
import APIService from "../service/APIService";
import { useNavigate, useParams } from "react-router-dom";

const TransactionDetails = () => {
  const { transactionId } = useParams();
  const [transaction, setTransactions] = useState(null);
  const [message, setMessage] = useState("");
  const [status, setStatus] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    const getTransaction = async () => {
      try {
        const transactionData = await APIService.getTransactionById(
          transactionId
        );

        if (transactionData.status === 200) {
          setTransactions(transactionData.transaction);
          setStatus(transactionData.transaction.status.toString());
        }
      } catch (error) {
        showMessage(
          error.response?.data?.message ||
            "Failed to fetch transaction details." + error
        );
      }
    };
    getTransaction();
  }, [transactionId]);

  //update transaction status
  const handleUpdateStatus = async () => {
    try {
      await APIService.updateTransactionStatus(transactionId, status);
      navigate("/transactions");
    } catch (error) {
      showMessage(
        error.response?.data?.message ||
          "Failed to Update transaction details." + error
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
      {message && (
        <div className="alert alert-info text-center mb-2">{message}</div>
      )}
      <div className="transaction-detail">
        {transaction && (
          <>
            <div className="section-card">
              <h2>Transaction Information</h2>
              <p>Type : {transaction.transactionType}</p>
              <p>Status : {transaction.status}</p>
              <p>Description : {transaction.description}</p>
              <p>Note : {transaction.note}</p>
              <p>Total Products : {transaction.totalProducts}</p>
              <p>Total Price : {transaction.totalPrice.toFixed(2)}</p>
              <p>
                Created At : {new Date(transaction.createdAt).toLocaleString()}
              </p>
              {transaction.updatedAt && (
                <p>
                  Updated At :{" "}
                  {new Date(transaction.updatedAt).toLocaleString()}
                </p>
              )}
            </div>

            {transaction?.product && (
              <div className="section-card">
                <h2>Product Information</h2>
                <p>Name : {transaction.product.name}</p>
                <p>SKU : {transaction.product.sku}</p>
                <p>Stock Quantity : {transaction.product.stockQuantity}</p>
                <p>Description : {transaction.product.description}</p>

                {transaction.product.imageUrl && (
                  <img
                    className="product-image"
                    src={`/${transaction.product.imageUrl}`}
                    alt={transaction.product.name}
                  />
                )}

                <p>Price : {transaction.product.price.toFixed(2)}</p>
              </div>
            )}

            {/*User information of the transaction*/}
            <div className="section-card">
              <h2>User Information</h2>
              <p>Name : {transaction.user.name}</p>
              <p>Email : {transaction.user.email}</p>
              <p>Phone Number : {transaction.user.phoneNumber}</p>
              <p>Role : {transaction.user.role}</p>
              <p>
                Created At : {new Date(transaction.createdAt).toLocaleString()}
              </p>
            </div>

            {/*Supplier  information of the transaction*/}
            {transaction.suppliers && (
              <div className="section-card">
                <h2>Supplier Information</h2>
                <p>Name : {transaction.supplier.name}</p>
                <p>Contact : {transaction.supplier.contactInfo}</p>
                <p>Address : {transaction.supplier.address}</p>
              </div>
            )}

            {/* UPDATE TRANSACTION STATUS*/}
            <div className="section-card transaction-status update">
              <label>Status : </label>
              <select
                value={status}
                onChange={(e) => setStatus(e.target.value)}
              >
                <option value="PENDING">PENDING</option>
                <option value="PROCESSING">PROCESSING</option>
                <option value="COMPLETED">COMPLETED</option>
                <option value="CANCELLED">CANCELLED</option>
              </select>
              <button onClick={() => handleUpdateStatus()}>
                Update Status
              </button>
            </div>
          </>
        )}
      </div>
    </Layout>
  );
};
export default TransactionDetails;
