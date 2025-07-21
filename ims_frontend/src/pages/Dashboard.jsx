"use client";

import { useState, useEffect } from "react";
import Layout from "../component/Layout";
import APIService from "../service/APIService";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
  AreaChart,
  Area,
  BarChart,
  Bar,
  ComposedChart,
} from "recharts";

const Dashboard = () => {
  const [message, setMessage] = useState("");
  const [selectedMonth, setSelectedMonth] = useState(new Date().getMonth() + 1);
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear());
  const [selectedData, setSelectedData] = useState("amount");
  const [transactionData, setTransactionData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const transactionResponse = await APIService.getAllTransactions();
        if (transactionResponse.status === 200) {
          setTransactionData(
            transformTransactionData(
              transactionResponse.transactions,
              selectedMonth,
              selectedYear
            )
          );
        }
      } catch (error) {
        showMessage(
          error.response?.data?.message ||
            "Login failed. Please try again." + error
        );
      }
    };
    fetchData();
  }, [selectedMonth, selectedYear, selectedData]);

  const transformTransactionData = (transactions, month, year) => {
    const dailyData = {};
    const daysInMonths = new Date(year, month, 0).getDate();

    for (let day = 1; day <= daysInMonths; day++) {
      dailyData[day] = {
        day,
        count: 0,
        quantity: 0,
        amount: 0,
      };
    }

    transactions.forEach((transaction) => {
      const transactionDate = new Date(transaction.createdAt);
      const transactionMonth = transactionDate.getMonth() + 1;
      const transactionYear = transactionDate.getFullYear();

      if (transactionMonth === month && transactionYear === year) {
        const day = transactionDate.getDate();
        dailyData[day].count += 1;
        dailyData[day].quantity += transaction.totalProducts;
        dailyData[day].amount += transaction.totalPrice;
      }
    });

    return Object.values(dailyData);
  };

  const handleMonthChange = (e) => {
    setSelectedMonth(Number.parseInt(e.target.value, 10));
  };

  const handleYearChange = (e) => {
    setSelectedYear(Number.parseInt(e.target.value, 10));
  };

  const showMessage = (msg) => {
    setMessage(msg);
    setTimeout(() => {
      setMessage("");
    }, 3000);
  };

  // Calculate summary statistics
  const getSummaryStats = () => {
    const total = transactionData.reduce(
      (sum, day) => sum + day[selectedData],
      0
    );
    const average = total / transactionData.length;
    const max = Math.max(...transactionData.map((day) => day[selectedData]));
    const min = Math.min(...transactionData.map((day) => day[selectedData]));

    return { total, average: average.toFixed(2), max, min };
  };

  const stats = getSummaryStats();

  return (
    <Layout>
      {message && <div className="message">{message}</div>}
      <div className="dashboard-page">
        <div className="button-groups">
          <button
            onClick={() => setSelectedData("count")}
            className={selectedData === "count" ? "active" : ""}
          >
            Number Of Transaction
          </button>
          <button
            onClick={() => setSelectedData("quantity")}
            className={selectedData === "quantity" ? "active" : ""}
          >
            Product Quantity
          </button>
          <button
            onClick={() => setSelectedData("amount")}
            className={selectedData === "amount" ? "active" : ""}
          >
            Amount
          </button>
        </div>

        <div className="dashboard-contents">
          <div className="filter-section">
            <label htmlFor="month-select">Select Month : </label>
            <select
              id="month-select"
              value={selectedMonth}
              onChange={handleMonthChange}
            >
              {Array.from({ length: 12 }, (_, i) => (
                <option key={i + 1} value={i + 1}>
                  {new Date(0, i).toLocaleString("default", { month: "long" })}
                </option>
              ))}
            </select>
            <label htmlFor="year-select">Select Year :</label>
            <select
              id="year-select"
              value={selectedYear}
              onChange={handleYearChange}
            >
              {Array.from({ length: 5 }, (_, i) => {
                const year = new Date().getFullYear() - i;
                return (
                  <option key={year} value={year}>
                    {year}
                  </option>
                );
              })}
            </select>
          </div>

          {/* Summary Statistics Cards */}
          <div className="stats-grid">
            <div className="stat-card">
              <div className="stat-value">{stats.total}</div>
              <div className="stat-label">
                Total{" "}
                {selectedData.charAt(0).toUpperCase() + selectedData.slice(1)}
              </div>
            </div>
            <div className="stat-card">
              <div className="stat-value">{stats.average}</div>
              <div className="stat-label">Daily Average</div>
            </div>
            <div className="stat-card">
              <div className="stat-value">{stats.max}</div>
              <div className="stat-label">Highest Day</div>
            </div>
            <div className="stat-card">
              <div className="stat-value">{stats.min}</div>
              <div className="stat-label">Lowest Day</div>
            </div>
          </div>

          {/* Charts Grid Layout */}
          <div className="charts-grid">
            {/* Bar Chart */}
            <div className="chart-container">
              <h3>
                Daily{" "}
                {selectedData.charAt(0).toUpperCase() + selectedData.slice(1)} -
                Bar Chart
              </h3>
              <ResponsiveContainer width="100%" height={350}>
                <BarChart data={transactionData}>
                  <CartesianGrid strokeDasharray="3 3" stroke="#333" />
                  <XAxis dataKey="day" stroke="#b3b3b3" fontSize={12} />
                  <YAxis stroke="#b3b3b3" fontSize={12} />
                  <Tooltip
                    contentStyle={{
                      backgroundColor: "#1e1e1e",
                      border: "1px solid #333",
                      borderRadius: "8px",
                      color: "#fff",
                    }}
                  />
                  <Legend />
                  <Bar
                    dataKey={selectedData}
                    fill="#00d4aa"
                    radius={[4, 4, 0, 0]}
                  />
                </BarChart>
              </ResponsiveContainer>
            </div>

            {/* Area Chart */}
            <div className="chart-container">
              <h3>
                Daily{" "}
                {selectedData.charAt(0).toUpperCase() + selectedData.slice(1)} -
                Area Chart
              </h3>
              <ResponsiveContainer width="100%" height={350}>
                <AreaChart data={transactionData}>
                  <CartesianGrid strokeDasharray="3 3" stroke="#333" />
                  <XAxis dataKey="day" stroke="#b3b3b3" fontSize={12} />
                  <YAxis stroke="#b3b3b3" fontSize={12} />
                  <Tooltip
                    contentStyle={{
                      backgroundColor: "#1e1e1e",
                      border: "1px solid #333",
                      borderRadius: "8px",
                      color: "#fff",
                    }}
                  />
                  <Legend />
                  <Area
                    type="monotone"
                    dataKey={selectedData}
                    stroke="#0099cc"
                    fill="#0099cc"
                    fillOpacity={0.3}
                  />
                </AreaChart>
              </ResponsiveContainer>
            </div>

            {/* Composed Chart (Line + Bar) */}
            <div className="chart-container">
              <h3>Combined View - Line & Bar</h3>
              <ResponsiveContainer width="100%" height={350}>
                <ComposedChart data={transactionData}>
                  <CartesianGrid strokeDasharray="3 3" stroke="#333" />
                  <XAxis dataKey="day" stroke="#b3b3b3" fontSize={12} />
                  <YAxis stroke="#b3b3b3" fontSize={12} />
                  <Tooltip
                    contentStyle={{
                      backgroundColor: "#1e1e1e",
                      border: "1px solid #333",
                      borderRadius: "8px",
                      color: "#fff",
                    }}
                  />
                  <Legend />
                  <Bar
                    dataKey={selectedData}
                    fill="#00d4aa"
                    fillOpacity={0.6}
                    radius={[2, 2, 0, 0]}
                  />
                  <Line
                    type="monotone"
                    dataKey={selectedData}
                    stroke="#ff4757"
                    strokeWidth={3}
                    dot={{ fill: "#ff4757", strokeWidth: 2, r: 4 }}
                  />
                </ComposedChart>
              </ResponsiveContainer>
            </div>
          </div>

          {/* Full Width Line Chart */}
          <div className="line-chart-container">
            <h3>Daily Transactions - Trend Analysis</h3>
            <ResponsiveContainer width="100%" height={450}>
              <LineChart data={transactionData}>
                <CartesianGrid strokeDasharray="3 3" stroke="#333" />
                <XAxis
                  dataKey="day"
                  stroke="#b3b3b3"
                  fontSize={12}
                  label={{
                    value: "Day of Month",
                    position: "insideBottom",
                    offset: -10,
                    style: { textAnchor: "middle", fill: "#b3b3b3" },
                  }}
                />
                <YAxis
                  stroke="#b3b3b3"
                  fontSize={12}
                  label={{
                    value:
                      selectedData.charAt(0).toUpperCase() +
                      selectedData.slice(1),
                    angle: -90,
                    position: "insideLeft",
                    style: { textAnchor: "middle", fill: "#b3b3b3" },
                  }}
                />
                <Tooltip
                  contentStyle={{
                    backgroundColor: "#1e1e1e",
                    border: "1px solid #333",
                    borderRadius: "8px",
                    color: "#fff",
                  }}
                />
                <Legend />
                <Line
                  type="monotone"
                  dataKey={selectedData}
                  stroke="#00d4aa"
                  strokeWidth={3}
                  dot={{ fill: "#00d4aa", strokeWidth: 2, r: 5 }}
                  activeDot={{ r: 8, stroke: "#00d4aa", strokeWidth: 2 }}
                />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default Dashboard;
