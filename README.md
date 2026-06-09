# AiTasker — Nền Tảng Freelance Tích Hợp AI

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19-blue.svg)](https://react.dev/)
[![Vite](https://img.shields.io/badge/Vite-8-purple.svg)](https://vite.dev/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**AiTasker** là nền tảng kết nối **Client (nhà tuyển dụng)** và **Expert (chuyên gia tự do)** được xây dựng theo kiến trúc fullstack hiện đại. Điểm khác biệt cốt lõi là tích hợp trực tiếp **Google Gemini AI** vào quy trình đăng việc, gợi ý ứng viên và sinh nội dung dịch vụ — cùng hệ thống thanh toán ký quỹ (Escrow) an toàn qua **Stripe** và trò chuyện thời gian thực qua **WebSocket**.

---

## Mục Lục

- [Tính năng chính](#tính-năng-chính)
- [Công nghệ sử dụng](#công-nghệ-sử-dụng)
- [Cấu trúc dự án](#cấu-trúc-dự-án)
- [Cài đặt & Chạy thử](#cài-đặt--chạy-thử)
- [Tài khoản demo](#tài-khoản-demo)
- [Tài liệu API](#tài-liệu-api)
- [Phân công nhóm](#phân-công-nhóm)

---

## Tính Năng Chính

### Xác thực & Phân quyền
- Đăng ký / đăng nhập với JWT stateless
- 3 vai trò: **CLIENT**, **EXPERT**, **ADMIN**
- Bảo vệ endpoint theo role, khóa tài khoản vi phạm

### Quản lý Công Việc & Ứng Tuyển
- Client đăng job (FIXED / HOURLY), tìm kiếm và lọc theo kỹ năng
- Expert nộp proposal với giá và timeline đề xuất
- Client chấp nhận proposal → tự động tạo Project

### Quản lý Dự Án & Milestone
- Chia dự án thành nhiều milestone với trạng thái riêng
- Expert nộp deliverables → Client duyệt / yêu cầu chỉnh sửa
- Milestone được duyệt → tự động giải ngân từ escrow

### Thanh Toán Escrow (Stripe)
- Client nạp tiền vào tài khoản ký quỹ qua Stripe
- Tiền giữ đến khi milestone được nghiệm thu
- Expert rút tiền về ngân hàng, Admin phê duyệt
- Stripe Webhook xử lý sự kiện thanh toán thời gian thực

### Trợ Lý AI (Google Gemini)
- **AI Job Assistant**: tối ưu mô tả job, gợi ý ngân sách, gắn tag kỹ năng tự động
- **AI Service Generator**: sinh mô tả dịch vụ cho Expert từ tiêu đề và giá
- **AI Recommendation**: gợi ý Expert phù hợp dựa trên kỹ năng và job requirements

### Giao Tiếp Thời Gian Thực
- Chat WebSocket (STOMP/SockJS) trong từng Project
- Hệ thống thông báo realtime cho các sự kiện quan trọng
- Lịch sử tin nhắn lưu trên MySQL

### Admin Dashboard
- Thống kê doanh thu, số lượng user, project, giao dịch
- Quản lý người dùng: khóa/mở khóa tài khoản
- Xử lý tranh chấp (Dispute) giữa Client và Expert
- Phê duyệt yêu cầu rút tiền của Expert

---

## Công Nghệ Sử Dụng

| Layer | Công nghệ |
|-------|-----------|
| Frontend | React 19, Vite 8, React Router DOM 7, Axios, SockJS + StompJS |
| Styling | Vanilla CSS (Glassmorphism), CSS Variables, Responsive |
| Backend | Java 17, Spring Boot 3.3.0, Spring Security, Spring Data JPA |
| Auth | JWT (JJWT 0.12.5), BCrypt |
| Database | MySQL 8.0, Hibernate ORM |
| Payment | Stripe API (PaymentIntent, Webhook, Payout) |
| AI | Google Gemini API |
| Realtime | Spring WebSocket, STOMP Broker |
| API Docs | Springdoc OpenAPI 3 (Swagger UI) |
| Build | Maven (backend), npm (frontend) |

---

## Cấu Trúc Dự Án

```
AlTasker/
├── backend/                        # Spring Boot (Maven)
│   └── src/main/java/com/aitasker/
│       ├── config/                 # SecurityConfig, AppConfig, StripeConfig, WebSocketConfig
│       ├── controller/             # REST & WebSocket controllers
│       ├── dto/                    # Request/Response DTOs
│       ├── entity/                 # JPA entities
│       ├── enums/                  # UserRole, JobStatus, PaymentStatus, ...
│       ├── exception/              # AppException, GlobalExceptionHandler
│       ├── repository/             # Spring Data JPA repositories
│       ├── security/               # JwtTokenProvider, JwtAuthFilter, CustomUserDetailsService
│       └── service/                # Business logic services
│
├── frontend/                       # React SPA (Vite)
│   └── src/
│       ├── api/                    # axios.js (interceptor)
│       ├── components/layout/      # Navbar, Footer
│       ├── context/                # AuthContext.jsx
│       └── pages/                  # Login, Register, Dashboard, ProjectDetail, ...
│
├── landingpage/                    # Static landing page (HTML/CSS/JS)
│
├── docs/
│   └── uml/                        # PlantUML diagrams (.puml) theo từng module
│       ├── tv1-auth/
│       ├── tv2-user/
│       ├── tv3-jobpost/
│       ├── tv4-project/
│       ├── tv5-payment/
│       ├── tv6-chat/
│       └── tv7-ai-admin/
│
├── PHAN_CONG_NHOM.md
└── README.md
```

---

## Cài Đặt & Chạy Thử

### Yêu cầu hệ thống
- **JDK 17+**
- **Node.js 18+** (LTS)
- **MySQL 8.0+**
- **Maven 3.8+**

---

### Bước 1 — Tạo database

```sql
CREATE DATABASE aitasker_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

---

### Bước 2 — Cấu hình backend

Mở file `backend/src/main/resources/application.yml` và điều chỉnh:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/aitasker_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
    username: root
    password: your_password

app:
  jwt:
    secret: your_jwt_secret_key
    expiration: 86400000   # 24 giờ (ms)

stripe:
  secret-key: sk_test_...
  webhook-secret: whsec_...

gemini:
  api-key: AIza...
```

---

### Bước 3 — Chạy Backend

```bash
cd backend
mvn spring-boot:run
```

Server khởi động tại `http://localhost:8080`.

> **DataSeeder** tự động chạy lần đầu khi database trống, tạo sẵn người dùng, job posts, proposals, projects, milestones và service listings mẫu.

---

### Bước 4 — Chạy Frontend

```bash
cd frontend
npm install
npm run dev
```

Ứng dụng chạy tại `http://localhost:5173`.

---

### Bước 5 — Xem Landing Page

Mở trực tiếp `landingpage/index.html` trên trình duyệt hoặc dùng **Live Server** (VS Code extension).

---

## Tài Khoản Demo

| Vai trò | Email | Mật khẩu | Ghi chú |
|---------|-------|-----------|---------|
| Admin | `admin@aitasker.com` | `123456` | Quản trị toàn hệ thống |
| Client | `client@aitasker.com` | `123456` | Đăng job, quản lý dự án |
| Expert 1 | `expert@aitasker.com` | `123456` | Chuyên gia AI / Chatbot |
| Expert 2 | `expert2@aitasker.com` | `123456` | Chuyên gia NLP |

**Truy cập Admin Dashboard:** Đăng nhập với tài khoản Admin → `http://localhost:5173/admin`

---

## Tài Liệu API

Swagger UI khả dụng khi backend đang chạy:

```
http://localhost:8080/swagger-ui/index.html
```

Các nhóm endpoint chính:

| Prefix | Mô tả |
|--------|-------|
| `/api/auth/**` | Đăng ký, đăng nhập |
| `/api/users/**` | Profile, danh sách expert |
| `/api/job-posts/**` | Đăng và tìm kiếm việc làm |
| `/api/proposals/**` | Nộp và quản lý proposal |
| `/api/projects/**` | Quản lý dự án |
| `/api/milestones/**` | Tạo, nộp, duyệt milestone |
| `/api/payments/**` | Thanh toán escrow |
| `/api/stripe/**` | Stripe PaymentIntent, refund, payout |
| `/api/withdrawals/**` | Rút tiền |
| `/api/ai/**` | Tính năng AI (Gemini) |
| `/api/admin/**` | Quản trị hệ thống |
| `/api/services/**` | Service listings của expert |
| `/ws/**` | WebSocket endpoint (chat) |

---

## Phân Công Nhóm

| Thành viên | Module | Nhánh Git |
|------------|--------|-----------|
| TV1 | Auth & Security (JWT, Spring Security) | `feature/tv1-auth` |
| TV2 | User Management & Profile | `feature/tv2-user` |
| TV3 | JobPost & Proposal | `feature/tv3-jobpost` |
| TV4 | Project & Milestone | `feature/tv4-project` |
| TV5 | Payment, Stripe & Withdrawal | `feature/tv5-payment` |
| TV6 | Chat, Notification & Review | `feature/tv6-chat` |
| TV7 | AI Module, Admin & ServiceListing | `feature/tv7-ai-admin` |

Chi tiết phân công, commit guideline và UML requirements xem tại [`PHAN_CONG_NHOM.md`](./PHAN_CONG_NHOM.md).

---

## Giấy Phép

Dự án phân phối dưới [MIT License](LICENSE).
