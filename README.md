# 💳 paypal-payment-microservice 

A production-ready **Spring Boot REST API** for end-to-end PayPal payment lifecycle management — from order creation to capture — with full payment status tracking, persistent transaction records, and a clean 3-step API flow.

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![PayPal](https://img.shields.io/badge/PayPal-Sandbox-blue?style=flat-square&logo=paypal)
![MySQL](https://img.shields.io/badge/MySQL-8.x-blue?style=flat-square&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=flat-square&logo=apachemaven)

---

## 📌 What This Project Does

This service handles the complete PayPal payment workflow:

```
Create Payment Record  →  Initiate PayPal Order  →  User Approves on PayPal  →  Capture Payment
```

Each step updates the payment status in the database, giving you full traceability of every transaction from `PENDING` → `INITIATED` → `CAPTURED` (or `FAILED` / `CANCELLED`).

---

## 🏗️ Project Structure

```
Payment_Status_Tracker/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/rushikesh/payment/
│       │       ├── controller/        # REST controllers — PaymentController
│       │       ├── service/           # Business logic — PaymentService
│       │       ├── repository/        # JPA repositories
│       │       ├── model/             # JPA entities — Payment, PaymentMethod, etc.
│       │       ├── dto/               # Request/Response DTOs
│       │       ├── config/            # PayPal OAuth2 client config
│       │       └── PaymentStatusTrackerApplication.java
│       └── resources/
│           └── application.properties
├── ScreenShots/                       # API demo screenshots
├── pom.xml
└── README.md
```

---

## ⚙️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| REST Client | Spring WebClient / RestTemplate |
| Payment Gateway | PayPal Orders REST API v2 |
| Auth | PayPal OAuth2.0 (Client Credentials) |
| Database | MySQL 8.x + Spring Data JPA |
| Build Tool | Maven |
| Testing | Postman |

---

## 🔑 Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8.x running locally
- A [PayPal Developer](https://developer.paypal.com) account with a Sandbox App
- Postman (for testing)

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/CoderRushikesh/paypal-payment-microservice.git
cd Payment_Status_Tracker
```

### 2. Configure `application.properties`

```properties
# Server
server.port=8081

# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/payment_tracker_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# PayPal Sandbox Credentials
paypal.client.id=YOUR_PAYPAL_SANDBOX_CLIENT_ID
paypal.client.secret=YOUR_PAYPAL_SANDBOX_CLIENT_SECRET
paypal.mode=sandbox
paypal.base-url=https://api-m.sandbox.paypal.com
```

> Get your `client.id` and `client.secret` from [PayPal Developer Portal](https://developer.paypal.com/dashboard/applications/sandbox) → Applications → Your App.

### 3. Create the database

```sql
CREATE DATABASE payment_tracker_db;
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

Server starts at: `http://localhost:8081`

---

## 🔄 API Flow — Step by Step

### Overview

```
POST /v1/payments              ← Step 1: Create payment record
POST /v1/payments/{id}/initiate ← Step 2: Create PayPal order, get redirect URL
GET  redirect URL              ← Step 3: User logs in & approves on PayPal sandbox
POST /v1/payments/{id}/capture  ← Step 4: Capture the approved payment
```

---

### Step 1 — Create a Payment Record

**POST** `http://localhost:8081/v1/payments`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "userId": 101,
  "paymentMethodId": 1,
  "providerId": 1,
  "paymentTypeId": 1,
  "amount": 1.75,
  "currency": "USD",
  "merchantTransactionReference": "TXN-001"
}
```

**Response:** Returns a payment object with a generated `transactionId` (UUID) and status `PENDING`.

```json
{
  "transactionId": "3e9e4b33-70b3-4c59-911d-2634e95aeebd",
  "status": "PENDING",
  "amount": 1.75,
  "currency": "USD"
}
```

> 📝 **Copy the `transactionId`** — you'll need it for Step 2 and Step 4.

---

### Step 2 — Initiate PayPal Order

**POST** `http://localhost:8081/v1/payments/{transactionId}/initiate`

Replace `{transactionId}` with the UUID from Step 1.

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "successUrl": "http://localhost:8081/payments/success",
  "cancelUrl": "http://localhost:8081/payments/cancel"
}
```

**Response:** Returns a PayPal `approvalUrl` and updates payment status to `INITIATED`.

```json
{
  "transactionId": "3e9e4b33-70b3-4c59-911d-2634e95aeebd",
  "approvalUrl": "https://www.sandbox.paypal.com/checkoutnow?token=1YL78103PX030663D",
  "status": "INITIATED"
}
```

> 📝 **Copy the `approvalUrl`** — paste it in your browser for Step 3.

---

### Step 3 — Approve Payment on PayPal Sandbox

1. Open the `approvalUrl` in your browser.
2. You'll be redirected to the PayPal sandbox login screen.
3. Log in to [PayPal Developer Portal](https://developer.paypal.com/dashboard/accounts) → **Sandbox Accounts**.
4. Pick any sandbox **Personal** account — copy the email and password.
5. Log in with those credentials on the PayPal payment page.
6. Select your payment method (Credit Union / Visa) and click **Pay $X.XX**.

![PayPal Sandbox Checkout](ScreenShots/paypal_sandbox_checkout.png)

> ✅ Once you click Pay and the sandbox confirms, come back to Postman for Step 4.

---

### Step 4 — Capture the Payment

**POST** `http://localhost:8081/v1/payments/{transactionId}/capture`

**Headers:**
```
Content-Type: application/json
```

No request body required.

**Response:** Returns final payment status `CAPTURED` with PayPal's capture ID.

```json
{
  "transactionId": "3e9e4b33-70b3-4c59-911d-2634e95aeebd",
  "paypalCaptureId": "5O190127TN364715T",
  "status": "CAPTURED",
  "amount": 1.75,
  "currency": "USD"
}
```

---

## 📊 Payment Status Lifecycle

```
PENDING  →  INITIATED  →  APPROVED (by user)  →  CAPTURED
                                              ↘  FAILED
               ↘  CANCELLED (user cancels on PayPal)
```

---

## 🧪 Quick Postman Testing Sequence

| # | Method | URL | Body |
|---|--------|-----|------|
| 1 | POST | `localhost:8081/v1/payments` | Payment details JSON |
| 2 | POST | `localhost:8081/v1/payments/{txnId}/initiate` | Success + Cancel URLs |
| 3 | Browser | Paste `approvalUrl`, log in with sandbox account, approve | — |
| 4 | POST | `localhost:8081/v1/payments/{txnId}/capture` | None |

---

## 📋 Database Tables (Auto-created by JPA)

| Table | Purpose |
|---|---|
| `payments` | Core transaction records with UUID, status, amount, timestamps |
| `payment_methods` | Supported methods (PayPal, Card, etc.) |
| `payment_providers` | Provider config (PayPal sandbox/live) |
| `payment_types` | One-time, recurring, etc. |

---

## 📸 Screenshots

PayPal sandbox payment UI and Postman API flow screenshots are available in the [`ScreenShots/`](./ScreenShots) folder.

---

## 🤝 Author

**Rushikesh Sahadev Kamble**
Java Backend Developer | Spring Boot | Microservices | AWS

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?style=flat-square&logo=linkedin)](https://linkedin.com/in/rushikesh-kamble)
[![GitHub](https://img.shields.io/badge/GitHub-CoderRushikesh-black?style=flat-square&logo=github)](https://github.com/CoderRushikesh)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
