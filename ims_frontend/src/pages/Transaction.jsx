import React, { useState, useEffect } from "react";
import Layout from "../component/Layout";
import APIService from "../service/APIService";
import { useNavigate } from "react-router-dom";

import PaginationComponent from "../component/PaginationComponent";

const Transaction = () => {
  const [transactions, setTransactions] = useState([]);
  const [message, setMessage] = useState("");
  const [filter, setFilter] = useState("");
  const [valueToSearch, setValueToSearch] = useState("");

  const navigate = useNavigate();

  //Pagination set-up
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const itemsPerPage = 10;

  useEffect(() => {
    const getTransactions = async () => {
      try {
        const transactionData = await APIService.getAllTransactions(
          valueToSearch
        );

        if (transactionData.status === 200) {
          setTotalPages(
            Math.ceil(transactionData.transactions.length / itemsPerPage)
          );

          setTransactions(
            transactionData.transactions.slice(
              (currentPage - 1) * itemsPerPage,
              currentPage * itemsPerPage
            )
          );
        }
      } catch (error) {
        showMessage(
          error.response?.data?.message ||
            "Failed to fetch transaction details." + error
        );
      }
    };
    getTransactions();
  }, [currentPage, valueToSearch]);

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => {
      setMessage("");
    }, 3000);
  };

  //handle Search
  const handleSearch = () => {
    setCurrentPage(1);
    setValueToSearch(filter);
  };

  //Navigate to Transfer Transaction Details Page
  const navigateTOTransaction = (transactionId) => {
    navigate(`/transactions/${transactionId}`);
  };

  return (
    <Layout>
      {message && (
        <div className="alert alert-info text-center mb-2">{message}</div>
      )}
      <div className="transaction-page">
        <div className="transaction-header">
          <h1>Transactions</h1>
          <div className="transaction-search">
            <input
              placeholder="Search transaction..."
              value={filter}
              onChange={(e) => setFilter(e.target.value)}
              type="text"
            />
            <button onClick={() => handleSearch()}>Search</button>
          </div>
        </div>
        {transactions && (
          <table className="transaction-table">
            <thead>
              <tr>
                <th>TYPE</th>
                <th>STATUS</th>
                <th>TOTAL PRICE</th>
                <th>TOTAL PRODUCTS</th>
                <th>DATE</th>
                <th>ACTIONS</th>
              </tr>
            </thead>
            <tbody>
              {transactions.map((transaction) => (
                <tr key={transaction.id}>
                  <td>{transaction.transactionType}</td>
                  <td>{transaction.status}</td>
                  <td>{transaction.totalPrice}</td>
                  <td>{transaction.totalProducts}</td>
                  <td>{new Date(transaction.createdAt).toLocaleString()}</td>

                  <td>
                    <button
                      onClick={() => navigateTOTransaction(transaction.id)}
                    >
                      View Details
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
      <PaginationComponent
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={setCurrentPage}
      />
    </Layout>
  );
};

export default Transaction;
