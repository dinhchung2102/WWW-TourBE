# Tour Booking API Documentation

**Base URL:** `http://localhost:8080`

## Overview

API này cung cấp các endpoint cho hệ thống đặt tour du lịch, bao gồm:
- Authentication & Customer Management
- Tour Management
- Booking Management
- Payment Processing

## Documentation Files

1. **[README_AUTH.md](./README_AUTH.md)** - Authentication & Customer endpoints
2. **[README_TOUR.md](./README_TOUR.md)** - Tour endpoints
3. **[README_BOOKING.md](./README_BOOKING.md)** - Booking endpoints
4. **[README_PAYMENT.md](./README_PAYMENT.md)** - Payment endpoints

## Authentication

Hầu hết các endpoint không yêu cầu authentication. Các endpoint yêu cầu auth sẽ có header:
```
Authorization: Bearer {token}
```

Token được lấy từ endpoint `/customer/auth/login` hoặc `/customer/auth/login/google`.

## Common Response Codes

- **200 OK** - Request thành công
- **201 Created** - Tạo mới thành công
- **204 No Content** - Xóa/Cập nhật thành công (không có body)
- **400 Bad Request** - Request không hợp lệ
- **401 Unauthorized** - Chưa đăng nhập hoặc token không hợp lệ
- **404 Not Found** - Không tìm thấy resource
- **409 Conflict** - Dữ liệu bị trùng lặp
- **500 Internal Server Error** - Lỗi server

## Error Response Format

```json
{
  "timestamp": "2024-01-01T00:00:00.000+00:00",
  "code": "400",
  "status": 400,
  "error": "Bad Request",
  "message": "Error message here"
}
```

## CORS

API hỗ trợ CORS từ origin: `http://localhost:5173`

## Rate Limiting

API có rate limiting. Kiểm tra trạng thái tại: `GET /api/rate-limit/status`

## Quick Start

1. **Đăng ký tài khoản:**
   ```
   POST /customer/auth/register
   ```

2. **Đăng nhập:**
   ```
   POST /customer/auth/login
   ```

3. **Lấy danh sách tour:**
   ```
   GET /tours
   ```

4. **Tạo booking:**
   ```
   POST /booking
   ```

5. **Thanh toán:**
   ```
   POST /api/payments
   ```

Xem chi tiết trong các file README tương ứng.

