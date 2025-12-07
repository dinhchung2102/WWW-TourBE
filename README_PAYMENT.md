# API Documentation - Payment

**Base URL:** `http://localhost:8080`

## Payment Endpoints

### 1. Tạo payment

- **Method:** `POST`
- **Endpoint:** `/api/payments`
- **Auth:** Không cần
- **Request Body:**

```json
{
  "orderId": "ORDER123456",
  "amount": 2000000,
  "paymentMethod": "MOMO" | "VNPAY",
  "returnUrl": "http://localhost:5173/payment-result",
  "customerEmail": "customer@example.com",
  "description": "Thanh toán tour Đà Lạt"
}
```

- **Response (201):**

```json
{
  "orderId": "ORDER123456",
  "transactionId": "TXN123456",
  "amount": 2000000,
  "paymentMethod": "MOMO",
  "status": "PENDING",
  "paymentUrl": "https://test-payment.momo.vn/...",
  "responseCode": "0",
  "responseMessage": "Success"
}
```

### 2. Lấy trạng thái payment

- **Method:** `GET`
- **Endpoint:** `/api/payments/{orderId}`
- **Auth:** Không cần
- **Response (200):** PaymentResponseDto
- **Error (404):** Not Found

### 3. VNPay Callback (Internal)

- **Method:** `GET`
- **Endpoint:** `/api/payments/vnpay/callback`
- **Auth:** Không cần (VNPay gọi tự động)
- **Query Params:** VNPay callback parameters
- **Response (200):**
  - `"Payment processed successfully"` (nếu vnp_ResponseCode = "00")
  - `"Payment processing failed"` (nếu khác)
- **Error (400):** `"Missing response code"`
- **Error (500):** `"Failed to process payment"`

### 4. Payment Result Page

- **Method:** `GET`
- **Endpoint:** `/payment-result`
- **Auth:** Không cần (VNPay redirect về)
- **Query Params:**
  - `vnp_ResponseCode`: Mã phản hồi ("00" = thành công)
  - `vnp_TxnRef`: Mã đơn hàng
  - `vnp_Amount`: Số tiền (đã nhân 100)
  - `vnp_OrderInfo`: Thông tin đơn hàng
  - `vnp_TransactionNo`: Mã giao dịch VNPay
- **Response (200):** HTML page hiển thị kết quả thanh toán

---

## Data Models

### PaymentRequestDto

```json
{
  "orderId": "string",
  "amount": 2000000.0,
  "paymentMethod": "MOMO" | "VNPAY",
  "returnUrl": "string",
  "customerEmail": "string",
  "description": "string"
}
```

### PaymentResponseDto

```json
{
  "orderId": "string",
  "transactionId": "string",
  "amount": 2000000.0,
  "paymentMethod": "MOMO" | "VNPAY",
  "status": "PENDING" | "SUCCESS" | "FAILED" | "CANCELLED",
  "paymentUrl": "string",
  "responseCode": "string",
  "responseMessage": "string"
}
```

---

## Payment Flow

### VNPay Flow:

1. FE gọi `POST /api/payments` với `paymentMethod: "VNPAY"`
2. Backend trả về `paymentUrl` trong response
3. FE redirect user đến `paymentUrl`
4. User thanh toán trên VNPay
5. VNPay redirect về `/payment-result` với query params
6. FE hiển thị kết quả từ HTML response

### Momo Flow:

1. FE gọi `POST /api/payments` với `paymentMethod: "MOMO"`
2. Backend trả về `paymentUrl` trong response
3. FE redirect user đến `paymentUrl`
4. User thanh toán trên Momo
5. Momo redirect về `returnUrl` đã cấu hình
6. FE kiểm tra trạng thái bằng `GET /api/payments/{orderId}`

---

## Notes

- `amount` trong request là số tiền gốc (VND)
- VNPay trả về `vnp_Amount` đã nhân 100, cần chia 100 để lấy số tiền thực
- `paymentUrl` chỉ dùng 1 lần, có thời hạn
- Sau khi thanh toán, kiểm tra `status` bằng `GET /api/payments/{orderId}`
