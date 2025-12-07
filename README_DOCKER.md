# Docker Setup Guide

Hướng dẫn chạy ứng dụng Tour-BE với Docker và MySQL.

## Yêu cầu

- Docker Desktop (Windows/Mac) hoặc Docker Engine + Docker Compose (Linux)
- Tối thiểu 4GB RAM
- Port 8080 và 3306 phải trống

## Cách sử dụng

### 1. Chạy toàn bộ stack (App + Database)

```bash
docker-compose up -d
```

Lệnh này sẽ:
- Build image cho Spring Boot application
- Tạo và khởi động MySQL container
- Tự động import `backup.sql` vào database
- Khởi động application và chờ database sẵn sàng

### 2. Chỉ chạy database (để dev local)

```bash
docker-compose -f docker-compose.dev.yml up -d
```

Sau đó chạy app local với:
```bash
mvn spring-boot:run
```

### 3. Xem logs

```bash
# Logs của tất cả services
docker-compose logs -f

# Logs chỉ của app
docker-compose logs -f app

# Logs chỉ của database
docker-compose logs -f mysql
```

### 4. Dừng services

```bash
docker-compose down
```

### 5. Dừng và xóa volumes (xóa dữ liệu database)

```bash
docker-compose down -v
```

### 6. Rebuild application

```bash
docker-compose build --no-cache app
docker-compose up -d
```

## Kiểm tra

### Health Check

- Application: http://localhost:8080/actuator/health
- API Base: http://localhost:8080

### Database Connection

```bash
# Kết nối từ host
mysql -h 127.0.0.1 -P 3306 -u root -psapassword tour_db

# Hoặc từ container
docker exec -it tour-mysql mysql -u root -psapassword tour_db
```

## Cấu hình

### Environment Variables

Các biến môi trường có thể override trong `docker-compose.yml`:

- `SPRING_DATASOURCE_URL`: JDBC URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `JWT_SECRET`: Secret key cho JWT
- `MOMO_*`: Cấu hình Momo payment
- `VNPAY_*`: Cấu hình VNPay payment

### Volumes

- `mysql_data`: Persistent storage cho MySQL data
- `backup.sql`: Được mount vào `/docker-entrypoint-initdb.d/` để tự động import khi database khởi động lần đầu

## Troubleshooting

### Port đã được sử dụng

```bash
# Kiểm tra port
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Mac/Linux

# Đổi port trong docker-compose.yml
ports:
  - "8081:8080"  # Thay đổi 8080 thành 8081
```

### Database không kết nối được

1. Kiểm tra MySQL đã sẵn sàng:
```bash
docker-compose logs mysql
```

2. Kiểm tra health check:
```bash
docker ps
```

3. Thử kết nối thủ công:
```bash
docker exec -it tour-mysql mysql -u root -psapassword
```

### Application không start

1. Xem logs chi tiết:
```bash
docker-compose logs app
```

2. Kiểm tra database connection string trong logs

3. Rebuild image:
```bash
docker-compose build --no-cache app
docker-compose up -d
```

### Xóa và tạo lại từ đầu

```bash
# Dừng và xóa tất cả
docker-compose down -v

# Xóa images
docker rmi tour-be-app

# Tạo lại
docker-compose up -d --build
```

## Production Notes

⚠️ **Không sử dụng cấu hình này cho production!**

Cho production, cần:
- Sử dụng secrets management (Docker Secrets, Vault, etc.)
- Tắt `SPRING_JPA_HIBERNATE_DDL_AUTO=update`, dùng migrations (Flyway/Liquibase)
- Cấu hình SSL/TLS cho database
- Sử dụng reverse proxy (Nginx/Traefik)
- Setup monitoring và logging
- Backup strategy cho database

