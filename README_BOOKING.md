# API Documentation - Booking

**Base URL:** `http://localhost:8080`

## Booking Endpoints

### 1. Tạo booking mới
- **Method:** `POST`
- **Endpoint:** `/booking`
- **Auth:** Không cần
- **Request Body:**
```json
{
  "user_id": 1,
  "tour_id": 1,
  "booking_date": "2024-06-01T00:00:00.000+00:00",
  "status": "PENDING",
  "number_of_people": 2,
  "total_price": 4000000
}
```
- **Response (200):** BookingServiceDTO (đã có `created_at`)
- **Error (400):** 
  - `"Invalid user_id"` (nếu user_id <= 0)
  - `"Invalid tour_id"` (nếu tour_id <= 0)
  - `"Number of people must be greater than 0"` (nếu number_of_people <= 0)
  - `{"message": "[error message]"}`

### 2. Lấy tất cả booking
- **Method:** `GET`
- **Endpoint:** `/bookings`
- **Auth:** Không cần
- **Response (200):**
```json
[
  {
    "id": 1,
    "user_id": 1,
    "tour_id": 1,
    "booking_date": "2024-06-01T00:00:00.000+00:00",
    "status": "PENDING",
    "number_of_people": 2,
    "total_price": 4000000,
    "created_at": "2024-01-01T00:00:00.000+00:00"
  }
]
```

### 3. Lấy booking theo ID
- **Method:** `GET`
- **Endpoint:** `/booking/{id}`
- **Auth:** Không cần
- **Response (200):** BookingServiceDTO
- **Error (404):** Not Found

### 4. Cập nhật booking
- **Method:** `PUT`
- **Endpoint:** `/booking`
- **Auth:** Không cần
- **Request Body:** BookingServiceDTO
```json
{
  "id": 1,
  "user_id": 1,
  "tour_id": 1,
  "booking_date": "2024-06-01T00:00:00.000+00:00",
  "status": "CONFIRMED",
  "number_of_people": 2,
  "total_price": 4000000
}
```
- **Response (200):** No Content
- **Error (400):** `{"message": "[error message]"}`

### 5. Xóa booking
- **Method:** `DELETE`
- **Endpoint:** `/booking/{id}`
- **Auth:** Không cần
- **Response (200):** No Content
- **Error (400):** `{"message": "[error message]"}`

### 6. Lấy thông tin tour cho booking
- **Method:** `GET`
- **Endpoint:** `/booking/tour/{id}`
- **Auth:** Không cần
- **Response (200):** TourDTO
- **Error (404):** Not Found

### 7. Lấy booking theo user ID
- **Method:** `GET`
- **Endpoint:** `/booking/user/{userId}`
- **Auth:** Không cần
- **Response (200):**
```json
[
  {
    "id": 1,
    "user_id": 1,
    "tour_id": 1,
    "booking_date": "2024-06-01T00:00:00.000+00:00",
    "status": "PENDING",
    "number_of_people": 2,
    "total_price": 4000000,
    "created_at": "2024-01-01T00:00:00.000+00:00"
  }
]
```
- **Error (400):** `{"message": "[error message]"}`

---

## Data Models

### BookingServiceDTO
```json
{
  "id": 1,
  "user_id": 1,
  "tour_id": 1,
  "booking_date": "2024-06-01T00:00:00.000+00:00",
  "status": "PENDING" | "CONFIRMED" | "CANCELLED",
  "number_of_people": 2,
  "total_price": 4000000.0,
  "created_at": "2024-01-01T00:00:00.000+00:00"
}
```

### ErrorResponse
```json
{
  "message": "Error message here"
}
```

