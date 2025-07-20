import React, { useState, useEffect } from "react";
import Layout from "../component/Layout";
import APIService from "../service/APIService";
import { useNavigate } from "react-router-dom";

import PaginationComponent from "../component/PaginationComponent";

const Product = () => {
  const [products, setProducts] = useState([]);
  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  //Pagination set-up
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const itemsPerPage = 2;

  useEffect(() => {
    const getProducts = async () => {
      try {
        const productData = await APIService.getAllProducts();

        if (productData.status === 200) {
          setTotalPages(Math.ceil(productData.products.length / itemsPerPage));

          setProducts(
            productData.products.slice(
              (currentPage - 1) * itemsPerPage,
              currentPage * itemsPerPage
            )
          );
        }
      } catch (error) {
        showMessage(
          error.response?.data?.message ||
            "Failed to fetch products details." + error
        );
      }
    };
    getProducts();
  }, [currentPage]);

  //delete a product
  const handleDeleteProduct = async (productId) => {
    if (window.confirm("Are you sure you want to delete this product?")) {
      try {
        await APIService.deleteProduct(productId);
        showMessage("Product deleted successfully.");
      } catch (error) {
        showMessage(
          error.response?.data?.message || "Failed to delete product." + error
        );
      }
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
      <div className="product-page">
        <div className="product-header">
          <h1>Products</h1>
          <button
            className="add-product btn"
            onClick={() => navigate("/add-product")}
          >
            {" "}
            Add Product{" "}
          </button>
        </div>
        {products && (
          <div className="product-list">
            {products.map((product) => (
              <div key={product.id} className="product-item">
                <img
                  className="product-image"
                  src={product.imageUrl}
                  alt={product.name}
                />

                <div className="product-info">
                  <h3 className="name">{product.name}</h3>
                  <p className="sku">Sku:{product.su}</p>
                  <p className="price">Price:{product.price}</p>
                  <p className="quantity">Quantity:{product.quantity}</p>
                </div>
                <div className="product-actions">
                  <button
                    className="edit-btn"
                    onClick={() => navigate(`/edit-product/${product.id}`)}
                  >
                    Edit
                  </button>
                  <button
                    className="delete-btn"
                    onClick={() => handleDeleteProduct(product.id)}
                  >
                    Delete
                  </button>
                </div>
              </div>
            ))}
          </div>
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

export default Product;
