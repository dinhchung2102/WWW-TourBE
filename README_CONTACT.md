# API Documentation - Contact

**Base URL:** `http://localhost:8080`

## Contact Endpoints

### 1. Gửi thông tin liên hệ
- **Method:** `POST`
- **Endpoint:** `/contact`
- **Auth:** Không cần
- **Request Body:**
```json
{
  "fullName": "Nguyễn Văn A",
  "phone": "0123456789",
  "email": "user@example.com",
  "description": "Tôi muốn tìm hiểu về tour Đà Lạt",
  "active": true
}
```
- **Response (200):** ContactDTO (đã có `id`, `createdAt`, `updatedAt`, `active` = true)
- **Error (500):** `"Failed to add contact: [error message]"`

### 2. Lấy tất cả contact
- **Method:** `GET`
- **Endpoint:** `/contact`
- **Auth:** Không cần
- **Response (200):**
```json
[
  {
    "id": 1,
    "fullName": "Nguyễn Văn A",
    "phone": "0123456789",
    "email": "user@example.com",
    "description": "Tôi muốn tìm hiểu về tour Đà Lạt",
    "active": true,
    "createdAt": "2024-01-01T00:00:00.000+00:00",
    "updatedAt": "2024-01-01T00:00:00.000+00:00"
  }
]
```
- **Error (500):** Internal Server Error

### 3. Lấy contact theo trạng thái active
- **Method:** `GET`
- **Endpoint:** `/contact/active?active=true`
- **Auth:** Không cần
- **Query Params:** `active` (boolean, default: true)
- **Response (200):** Array of ContactDTO
- **Error (500):** Internal Server Error

### 4. Lấy contact theo ID
- **Method:** `GET`
- **Endpoint:** `/contact/{id}`
- **Auth:** Không cần
- **Response (200):** ContactDTO
- **Error (404):** Not Found
- **Error (500):** Internal Server Error

### 5. Cập nhật contact
- **Method:** `PUT`
- **Endpoint:** `/contact`
- **Auth:** Không cần
- **Request Body:** ContactDTO (phải có `id`)
```json
{
  "id": 1,
  "fullName": "Nguyễn Văn A",
  "phone": "0123456789",
  "email": "user@example.com",
  "description": "Đã liên hệ",
  "active": false
}
```
- **Response (200):** ContactDTO
- **Error (500):** `"Failed to update contact: [error message]"`

### 6. Xóa contact
- **Method:** `DELETE`
- **Endpoint:** `/contact/{id}`
- **Auth:** Không cần
- **Response (200):** `"Xóa contact thành công"`
- **Error (500):** `"Failed to delete contact: [error message]"`

---

## Data Models

### ContactDTO
```json
{
  "id": 1,
  "fullName": "string",
  "phone": "string",
  "email": "string",
  "description": "string",
  "active": true,
  "createdAt": "2024-01-01T00:00:00.000+00:00",
  "updatedAt": "2024-01-01T00:00:00.000+00:00"
}
```

