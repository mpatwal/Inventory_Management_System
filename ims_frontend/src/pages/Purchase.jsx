import React, { useState, useEffect } from "react";
import Layout from "../component/Layout";
import APIService from "../service/APIService";

const Purchase = () => {
  const [products, setProducts] = useState([]);
  const [suppliers, setSuppliers] = useState([]);
  const [productId, setProductId] = useState("");
  const [supplierId, setSupplierId] = useState("");
  const [description, setDescription] = useState("");
  const [note, setNote] = useState("");
  const [quantity, setQuantity] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    const fetchproductandSuppliers = async () => {
      try {
        const productData = await APIService.getAllProducts();
        const supplierData = await APIService.getAllSuppliers();
        setProducts(productData.products);
        setSuppliers(supplierData.suppliers);
      } catch (error) {
        showMessage(
          error.response?.data?.message || "Failed to get Products" + error
        );
      }
    };
    fetchproductandSuppliers();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!productId || !supplierId || !quantity) {
      showMessage("Please fill all required feilds");
      return;
    }
    const body = {
      productId,
      quantity: parseInt(quantity),
      supplierId,
      description,
      note,
    };
    console.log(body);
    try {
      const response = await APIService.purchaseProduct(body);
      showMessage(response.message);
      resetForm();
    } catch (error) {
      showMessage(
        error.response?.data?.message || "Failed to get Products" + error
      );
    }
  };
  const resetForm = () => {
    setProductId("");
    setSupplierId("");
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
        <h1>Receive Inventory</h1>
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
            <label>Select Supplier</label>
            <select
              value={supplierId}
              onChange={(e) => setSupplierId(e.target.value)}
              required
            >
              <option value="">Select the Supplier</option>

              {suppliers.map((supplier) => (
                <option key={supplier.id} value={supplier.id}>
                  {supplier.name}
                </option>
              ))}
            </select>
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

          <div className="form-group">
            <label>Quantity</label>
            <input
              type="number"
              value={quantity}
              onChange={(e) => setQuantity(e.target.value)}
            />
          </div>
          <button type="submit">Purchase</button>
        </form>
      </div>
    </Layout>
  );
};
export default Purchase;
