# API Documentation - About/Info

**Base URL:** `http://localhost:8080`

## Info Endpoints

### 1. Tạo info mới
- **Method:** `POST`
- **Endpoint:** `/about/info`
- **Auth:** Không cần
- **Request Body:**
```json
{
  "title": "Giới thiệu về chúng tôi",
  "description": "Nội dung giới thiệu...",
  "image": "https://example.com/image.jpg",
  "isContactInfo": false,
  "orderIndex": 1
}
```
- **Response (200):** InfoDTO (đã có `id`, `createdAt`, `updatedAt`)
- **Error (500):** `"Failed to add info: [error message]"`

### 2. Lấy tất cả info
- **Method:** `GET`
- **Endpoint:** `/about/info`
- **Auth:** Không cần
- **Response (200):** Array of InfoDTO
- **Error (500):** Internal Server Error

### 3. Lấy info theo thứ tự
- **Method:** `GET`
- **Endpoint:** `/about/info/ordered`
- **Auth:** Không cần
- **Response (200):** Array of InfoDTO (sắp xếp theo `orderIndex`)
- **Error (500):** Internal Server Error

### 4. Lấy info theo contact info
- **Method:** `GET`
- **Endpoint:** `/about/info/contact?isContactInfo=true`
- **Auth:** Không cần
- **Query Params:** `isContactInfo` (boolean, default: true)
- **Response (200):** Array of InfoDTO
- **Error (500):** Internal Server Error

### 5. Lấy info theo ID
- **Method:** `GET`
- **Endpoint:** `/about/info/{id}`
- **Auth:** Không cần
- **Response (200):** InfoDTO
- **Error (404):** Not Found
- **Error (500):** Internal Server Error

### 6. Cập nhật info
- **Method:** `PUT`
- **Endpoint:** `/about/info`
- **Auth:** Không cần
- **Request Body:** InfoDTO (phải có `id`)
- **Response (200):** InfoDTO
- **Error (500):** `"Failed to update info: [error message]"`

### 7. Xóa info
- **Method:** `DELETE`
- **Endpoint:** `/about/info/{id}`
- **Auth:** Không cần
- **Response (200):** `"Xóa info thành công"`
- **Error (500):** `"Failed to delete info: [error message]"`

---

## Data Models

### InfoDTO
```json
{
  "id": 1,
  "title": "string",
  "description": "string",
  "image": "string",
  "isContactInfo": false,
  "createdAt": "2024-01-01T00:00:00.000+00:00",
  "updatedAt": "2024-01-01T00:00:00.000+00:00",
  "orderIndex": 1
}
```

