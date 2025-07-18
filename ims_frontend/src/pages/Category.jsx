import React, { useState, useEffect } from "react";
import Layout from "../component/Layout";
import APIService from "../service/APIService";

const Category = () => {
  const [categories, setCategories] = useState([]);
  const [categoryName, setCategoryName] = useState("");
  const [message, setMessage] = useState("");
  const [isEditing, setIsEditing] = useState(false);
  const [editingCategoryId, setEditingCategoryId] = useState(null);

  // to fetch categories from backend
  useEffect(() => {
    const getAllCategories = async () => {
      try {
        const response = await APIService.getAllCategories();
        console.log("Full response:", response);
        if (
          response &&
          response.status === 200 &&
          response.categories // <-- Fix here: use response.categories
        ) {
          setCategories(response.categories);
        } else if (
          response &&
          response.status === 200 &&
          response.data &&
          response.data.categories
        ) {
          setCategories(response.data.categories);
        } else {
          setMessage("No categories found or invalid response.");
        }
      } catch (error) {
        console.log("Error fetching categories:", error);
        setMessage(
          error.response?.data?.message ||
            "Login failed. Please try again." + error
        );
      }
    };
    getAllCategories(); // <-- Call the function here
  }, []);

  //add categoryconst

  const addCategory = async (e) => {
    if (!categoryName) {
      showMessage("Please enter a category name.");
      return;
    }

    try {
      await APIService.createCategory({ name: categoryName });
      showMessage("Category added successfully.");
      setCategoryName(""); //to clear the input field
      window.location.reload(); //to reload the page
    } catch (error) {
      showMessage(
        error.response?.data?.message || "Error logging in a User: " + error
      );
    }
  };

  // to edit category

  const editCategory = async () => {
    try {
      await APIService.updateCategory(editingCategoryId, {
        name: categoryName,
      });
      showMessage("Category updated successfully.");
      setIsEditing(false);
      setCategoryName("");
      window.location.reload(); //to reload the page
    } catch (error) {
      showMessage(
        error.response?.data?.message || "Error editing category: " + error
      );
    }
  };
  // populate the edit category data
  const handleEditCategory = (category) => {
    setIsEditing(true);
    setEditingCategoryId(category.id);
    setCategoryName(category.name);
  };

  //delete category
  const handledeleteCategory = async (categoryId) => {
    if (window.confirm("Are you sure you want to delete this category?")) {
      try {
        await APIService.deleteCategory(categoryId);
        showMessage("Category deleted successfully.");
        window.location.reload(); // to reload the page
      } catch (error) {
        showMessage(
          error.response?.data?.message || "Error deleting category: " + error
        );
      }
    }
  };

  //method to show error messages

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => {
      setMessage("");
    }, 3000);
  };
  return (
    <Layout>
      {message && <div className="message">{message}</div>}
      <div className="category-page">
        <div className="category-header">
          <h1>Categories </h1>
          <div className="add-cart">
            <input
              type="text"
              value={categoryName}
              placeholder="Category Name"
              onChange={(e) => setCategoryName(e.target.value)}
            />
            {!isEditing ? (
              <button onClick={addCategory}>Add Category</button>
            ) : (
              <button onClick={editCategory}>Edit Category</button>
            )}
          </div>
        </div>
        {categories && (
          <ul className="category-list">
            {categories.map((category) => (
              <li className="category-item" key={category.id}>
                <span>{category.name}</span>
                <div className="category-actions">
                  <button onClick={() => handleEditCategory(category)}>
                    Edit
                  </button>
                  <button onClick={() => handledeleteCategory(category.id)}>
                    Delete
                  </button>
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>
    </Layout>
  );
};

export default Category;
