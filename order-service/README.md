# 🛒 Order Service

Handles order creation, cancellation, and retrieval.  
Communicates with Product Service to validate and restore stock.

---

## **Endpoints**

### 🔹 Create Order  
**POST** `/order/create`  
Creates an order if product stock is available.

**Sample Request**
```json
{
  "customerName": "Deepika",
  "items": [
    {"productName": "keyboard", "quantity": 1}
  ]
}

### 🔹 Get order by ID
**GET** - `/order/get/{id}`
Sample Response:
{
    "customerName": "Joan",
    "items": [
        {
            "productName": "Ipad",
            "quantity": 1
        },
        {
            "productName": "charger",
            "quantity": 1
        },
        {
            "productName": "cooker",
            "quantity": 1
        }
    ]
}
Get all orders
GET - /order/getall
Delete Order
DELETE - /order/delete/{id} - Deletion of order based on the given id
