# API Documentation - Tour

**Base URL:** `http://localhost:8080`

## Tour Endpoints

### 1. Tạo tour mới
- **Method:** `POST`
- **Endpoint:** `/tour`
- **Auth:** Không cần
- **Request Body:**
```json
{
  "title": "Tour Đà Lạt 3 ngày 2 đêm",
  "description": "Khám phá Đà Lạt xinh đẹp",
  "location": "Đà Lạt",
  "duration": 3,
  "price": 2000000,
  "max_participants": 20,
  "start_date": "2024-06-01T00:00:00.000+00:00",
  "end_date": "2024-06-03T00:00:00.000+00:00",
  "image": "https://example.com/image.jpg"
}
```
- **Response (200):** TourDTO
- **Error (500):** `"Failed to add tour: [error message]"`

### 2. Tạo nhiều tour
- **Method:** `POST`
- **Endpoint:** `/tours`
- **Auth:** Không cần
- **Request Body:** Array of TourDTO
```json
[
  {
    "title": "Tour 1",
    "description": "Description 1",
    "location": "Location 1",
    "duration": 2,
    "price": 1000000,
    "max_participants": 15,
    "start_date": "2024-06-01T00:00:00.000+00:00",
    "end_date": "2024-06-02T00:00:00.000+00:00",
    "image": "https://example.com/image1.jpg"
  }
]
```
- **Response (200):** Array of TourDTO
- **Error (500):** `"Failed to add tours: [error message]"`

### 3. Lấy tất cả tour
- **Method:** `GET`
- **Endpoint:** `/tours`
- **Auth:** Không cần
- **Response (200):**
```json
[
  {
    "id_tour": 1,
    "title": "Tour Đà Lạt",
    "description": "Khám phá Đà Lạt",
    "location": "Đà Lạt",
    "duration": 3,
    "price": 2000000,
    "max_participants": 20,
    "start_date": "2024-06-01T00:00:00.000+00:00",
    "end_date": "2024-06-03T00:00:00.000+00:00",
    "created_at": "2024-01-01T00:00:00.000+00:00",
    "image": "https://example.com/image.jpg"
  }
]
```
- **Error (500):** Internal Server Error

### 4. Lấy tour theo ID
- **Method:** `GET`
- **Endpoint:** `/tour/{id}`
- **Auth:** Không cần
- **Response (200):** TourDTO
- **Error (404):** Not Found
- **Error (500):** Internal Server Error

### 5. Tìm tour theo địa điểm
- **Method:** `GET`
- **Endpoint:** `/tours/location/{location}`
- **Auth:** Không cần
- **Response (200):** Array of Tour
- **Error (404):** `null` (không tìm thấy)
- **Error (500):** Internal Server Error

### 6. Tìm tour theo tiêu đề
- **Method:** `GET`
- **Endpoint:** `/tours/title/{title}`
- **Auth:** Không cần
- **Response (200):** Array of Tour
- **Error (404):** `null` (không tìm thấy)
- **Error (500):** Internal Server Error

### 7. Tìm tour theo khoảng giá
- **Method:** `GET`
- **Endpoint:** `/tours/price?minPrice={minPrice}&maxPrice={maxPrice}`
- **Auth:** Không cần
- **Query Params:**
  - `minPrice` (double): Giá tối thiểu
  - `maxPrice` (double): Giá tối đa
- **Response (200):** Array of Tour
- **Error (404):** `null` (không tìm thấy)
- **Error (500):** Internal Server Error

### 8. Xóa tour
- **Method:** `DELETE`
- **Endpoint:** `/tour/{id}`
- **Auth:** Không cần
- **Response (200):** `"Xóa tour thành công"`
- **Error (500):** `"Failed to delete tour: [error message]"`

### 9. Cập nhật tour
- **Method:** `PUT`
- **Endpoint:** `/tour`
- **Auth:** Không cần
- **Request Body:** TourDTO (phải có `id_tour`)
- **Response (200):** TourDTO
- **Error (500):** `"Failed to update tour: [error message]"`

### 10. Upload ảnh tour
- **Method:** `POST`
- **Endpoint:** `/tour/upload-image`
- **Auth:** Không cần
- **Content-Type:** `multipart/form-data`
- **Request Body:**
  - `file` (MultipartFile): File ảnh
- **Response (200):** `"https://cloudinary.com/image.jpg"` (URL của ảnh)
- **Error (500):** `"Failed to upload image: [error message]"`

### 11. Tạo tour kèm ảnh
- **Method:** `POST`
- **Endpoint:** `/tour/with-image`
- **Auth:** Không cần
- **Content-Type:** `multipart/form-data`
- **Request Body:**
  - `tour` (String): JSON string của TourDTO
  - `file` (MultipartFile): File ảnh
- **Example:**
```javascript
const formData = new FormData();
formData.append('tour', JSON.stringify({
  title: "Tour Đà Lạt",
  description: "Khám phá Đà Lạt",
  location: "Đà Lạt",
  duration: 3,
  price: 2000000,
  max_participants: 20,
  start_date: "2024-06-01T00:00:00.000+00:00",
  end_date: "2024-06-03T00:00:00.000+00:00"
}));
formData.append('file', fileInput.files[0]);
```
- **Response (200):** TourDTO (đã có `image` URL)
- **Error (500):** `"Failed to add tour with image: [error message]"`

---

## Data Models

### TourDTO
```json
{
  "id_tour": 1,
  "title": "string",
  "description": "string",
  "location": "string",
  "duration": 3,
  "price": 2000000.0,
  "max_participants": 20,
  "start_date": "2024-06-01T00:00:00.000+00:00",
  "end_date": "2024-06-03T00:00:00.000+00:00",
  "created_at": "2024-01-01T00:00:00.000+00:00",
  "image": "https://example.com/image.jpg"
}
```

