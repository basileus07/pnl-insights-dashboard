# pnl-insights-dashboard

# 📊 Trader Insights

A **Spring Boot + MongoDB** application to store, analyze, and gain insights from trading PnL (Profit & Loss) data.  
It supports bulk data upload, querying between dates, and performance metrics like average return, max, and min.

---

## 🚀 Features

- REST APIs for CRUD operations on trader PnL records
- Bulk upload of trading data (via DTO → Entity mapping)
- Insights calculation:
  - Average return %
  - Maximum return %
  - Minimum return %
- MongoDB persistence with date-range queries
- Layered architecture: Controller → Service → Repository → MongoDB

---

## 🏗️ Project Structure

```
src/main/java/com/example/traderinsights
│
├── controller # REST Controllers (expose APIs)
├── dto # Data Transfer Objects (for requests/responses)
├── entity # MongoDB entities
├── repository # Spring Data MongoDB repositories
├── service # Business logic interfaces & implementations
└── TraderInsightsApplication.java
```



---

## ⚙️ Tech Stack

- Java 17+
- Spring Boot 3.x
- MongoDB (NoSQL database)
- Maven (build tool)

---

## 📌 API Endpoints

| Method | Endpoint                   | Description                                         |
|--------|----------------------------|-----------------------------------------------------|
| POST   | /trader/add                | Add a single PnL record                             |
| POST   | /trader/bulk-upload        | Bulk upload PnL records (DTO)                       |
| GET    | /trader/insights           | Get insights between two dates (avg, max, min)     |
| GET    | /trader/top-traders        | **Bonus**: Fetch top 3 traders by average PnL %    |

---

## ▶️ Running the Application

1. Clone the repo
```bash
git clone https://github.com/your-username/trader-insights.git
cd trader-insights
```

2. Run the app
mvn spring-boot:run
# or
./mvnw spring-boot:run


3. Test API
example api :
```
curl -X POST http://localhost:8080/trader/add \
 -H "Content-Type: application/json" \
 -d '{"tradeDate":"2025-08-19","pnlAmount":5000,"allocatedMargin":20000}'
```


Example Response : 
```
{
 "averageReturn": 2.5,
 "maxReturn": 4.0,
 "minReturn": 1.2
}
```

🌟 Bonus API: Top Traders

Endpoint: GET /trader/top-traders

Query Params:

startdate (yyyy-MM-dd) – start of date range

enddate (yyyy-MM-dd) – end of date range

limit (optional, default 3) – number of top traders to fetch


Try with curl
```
curl "http://localhost:8080/trader/top-traders?startdate=2025-01-01&enddate=2025-12-31&limit=3"
```

Example Response : 
```
[
  {
    "traderId": "d8d32089-94a5-42d7-a0cc-fcf0b8537888",
    "averagePnlPercentage": 20.5,
    "totalTrades": 15,
    "totalPnlAmount": 15000
  },
  {
    "traderId": "a3e4ffdf-6cd2-4ca7-a7cf-42751c262ac1",
    "averagePnlPercentage": 18.3,
    "totalTrades": 12,
    "totalPnlAmount": 12000
  },
  {
    "traderId": "b2c1dddf-3cd2-4aa8-b7cf-42751c263bc2",
    "averagePnlPercentage": 16.7,
    "totalTrades": 10,
    "totalPnlAmount": 10000
  }
]
```
