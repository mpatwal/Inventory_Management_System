import React, { useState, useEffect } from "react";
import Layout from "../component/Layout";
import APIService from "../service/APIService";

const Sell = () => {
  const [products, setProducts] = useState([]);
  const [productId, setProductId] = useState("");
  const [description, setDescription] = useState("");
  const [note, setNote] = useState("");
  const [quantity, setQuantity] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const productData = await APIService.getAllProducts();

        setProducts(productData.products);
      } catch (error) {
        showMessage(
          error.response?.data?.message || "Failed to get Products" + error
        );
      }
    };
    fetchProducts();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!productId || !quantity) {
      showMessage("Please fill all required feilds");
      return;
    }
    const body = {
      productId,
      quantity: parseInt(quantity),
    };
    try {
      const response = await APIService.sellProduct(body);
      showMessage(response.message);
      resetForm();
    } catch (error) {
      showMessage(
        error.response?.data?.message || "Failed to sell Products" + error
      );
    }
  };
  const resetForm = () => {
    setProductId("");
    setDescription();
    setNote("");
    setQuantity("");
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
      <div className="purchase-form">
        <h1>Sell Inventory</h1>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Select Product</label>
            <select
              value={productId}
              onChange={(e) => setProductId(e.target.value)}
              required
            >
              <option value="">Select the Product</option>

              {products.map((product) => (
                <option key={product.id} value={product.id}>
                  {product.name}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Quantity</label>
            <input
              type="number"
              value={quantity}
              onChange={(e) => setQuantity(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label>Description</label>
            <input
              type="text"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label>Note</label>
            <input
              type="text"
              value={note}
              onChange={(e) => setNote(e.target.value)}
            />
          </div>

          <button type="submit">Sell</button>
        </form>
      </div>
    </Layout>
  );
};
export default Sell;
