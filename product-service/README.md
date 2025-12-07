# 🛒 Product Service

Handles product stocking, updating, retrieval. 

---

## **Endpoints**

### 🔹 Add product
**POST** `/Inventory/add` 

**Sample Request**
```json
{
    "productName": "puma",
    "availableQuantity": 1
}
```

### 🔹 Get product
**GET** - ` /Inventory/get/{productName}`

**Sample Response**
```json
{
    "productName": "puma",
    "availableQuantity": 1,
    "inStock": true
}
```
### 🔹 Get all products
**GET** - `/Inventory/getall`

### 🔹Update product
**PUT** - `/Inventory/update/product` - Deletion of order based on the given id

### 🔹Run
```bash
cd product-service
mvn spring-boot:run
```
