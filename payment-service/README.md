# 🛒 Payment Service

Process payments and retrieves payment data.

---
## **Endpoints**

### 🔹 Create Payment  
**POST** `/Payment/makepayment`  
Creates an order if product stock is available.

**Sample Request**
```json
{
  "orderId": 14,
  "amount": 20000,
  "paymentMethod": "CARD"
}
```

### 🔹 Get payment by ID
**GET** - `/Payment/get/{id}`

**Sample Response**
```json
{
    "id": 1,
    "orderId": 7,
    "amount": 899.0,
    "status": "FAILED",
    "paymentMethod": "UPI"
}

```
### 🔹 Get all payments
**GET** - /Payment/getall

