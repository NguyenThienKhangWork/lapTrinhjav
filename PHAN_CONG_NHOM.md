# PHÂN CÔNG CÔNG VIỆC NHÓM - DỰ ÁN AITASKER
> Nền tảng freelance kết nối Client - Expert, tích hợp AI (Gemini), thanh toán Escrow (Stripe), WebSocket chat  
> Stack: Spring Boot 3 + React 19 + MySQL  
> **7 thành viên | Mỗi người đều phải CODE + COMMIT Git + VẼ UML**

---

## QUY TẮC COMMIT GIT

```
feat(module): mô tả ngắn gọn
fix(module): mô tả lỗi đã sửa
docs(module): cập nhật tài liệu/UML
```

**Thứ tự commit bắt buộc theo Sprint:**
- Sprint 1 (Nền tảng): Thành viên 1 → 2 → 3
- Sprint 2 (Nghiệp vụ): Thành viên 4 → 5
- Sprint 3 (Tích hợp): Thành viên 6 → 7
- Mỗi thành viên commit **tối thiểu 10 commits** có nội dung rõ ràng

---

## THÀNH VIÊN 1 — Kiến trúc nền tảng & Xác thực (Auth Foundation)

### Phụ trách module
**Backend:**
- `entity/User.java` — entity người dùng
- `enums/UserRole.java` — enum phân quyền
- `security/JwtTokenProvider.java` — tạo/xác thực JWT
- `security/JwtAuthFilter.java` — filter xác thực request
- `security/CustomUserDetailsService.java` — load user từ DB
- `config/SecurityConfig.java` — cấu hình Spring Security
- `config/AppConfig.java` — cấu hình ObjectMapper, RestTemplate
- `dto/AuthRequest.java`, `AuthResponse.java`, `RegisterRequest.java`
- `service/AuthService.java` — đăng ký, đăng nhập
- `controller/AuthController.java` — API `/auth/login`, `/auth/register`
- `repository/UserRepository.java`

**Frontend:**
- `src/context/AuthContext.jsx` — quản lý trạng thái đăng nhập
- `src/api/axios.js` — cấu hình axios interceptor
- `src/pages/Login.jsx`
- `src/pages/Register.jsx`
- `src/components/layout/Navbar.jsx` (phần auth)

### UML phải vẽ
1. **Use Case Diagram** — Actor: Guest, Client, Expert, Admin → các use case đăng ký, đăng nhập, đăng xuất, xem profile
2. **Sequence Diagram** — Luồng đăng nhập: Browser → AuthController → AuthService → UserRepository → JwtTokenProvider → trả token
3. **Class Diagram** — Class `User`, `JwtTokenProvider`, `AuthService`, `CustomUserDetailsService` với các method và quan hệ

### Danh sách commits (theo thứ tự)
```
1. feat(auth): add User entity with JPA annotations and UserRole enum
2. feat(auth): implement JwtTokenProvider for token generation/validation
3. feat(auth): add JwtAuthFilter and CustomUserDetailsService
4. feat(auth): configure Spring Security with JWT stateless auth
5. feat(auth): implement AuthService register and login logic
6. feat(auth): add AuthController with /auth/login and /auth/register endpoints
7. feat(auth): configure AppConfig with ObjectMapper JavaTimeModule
8. feat(frontend): add AuthContext with login/logout state management
9. feat(frontend): implement Login and Register pages with form validation
10. feat(frontend): add axios interceptor for JWT Bearer token
11. docs(auth): add UML diagrams (use case, sequence, class) for auth module
```

---

## THÀNH VIÊN 2 — Quản lý người dùng & Hồ sơ (User Management)

### Phụ trách module
**Backend:**
- `dto/UserDTO.java`, `UpdateProfileRequest.java`
- `service/UserService.java` — xem/cập nhật profile, đổi mật khẩu
- `controller/UserController.java` — API `/users/me`, `/users/{id}`, `/users/profile`
- `config/DataSeeder.java` — seed dữ liệu mẫu

**Frontend:**
- `src/pages/ExpertProfile.jsx` — trang hồ sơ chuyên gia
- `src/pages/Landing.jsx` — trang chủ
- `src/components/layout/Footer.jsx`

### UML phải vẽ
1. **Use Case Diagram** — Actor: Client, Expert → xem hồ sơ, cập nhật hồ sơ, xem danh sách expert
2. **Activity Diagram** — Luồng cập nhật profile: mở form → nhập thông tin → validate → lưu → hiển thị thông báo
3. **Class Diagram** — Class `UserService`, `UserController`, `UserDTO`, `UpdateProfileRequest` và các quan hệ

### Danh sách commits (theo thứ tự)
```
1. feat(user): add UserDTO and UpdateProfileRequest DTOs
2. feat(user): implement UserService with getProfile and updateProfile methods
3. feat(user): add UserController endpoints for profile management
4. feat(user): implement DataSeeder with sample users/data for dev
5. feat(frontend): add ExpertProfile page with skill/bio editing
6. feat(frontend): implement Landing page with hero section and stats
7. feat(frontend): add Footer component
8. fix(user): handle null fields in UserDTO.fromEntity mapping
9. feat(user): add endpoint GET /users/experts to list all experts
10. docs(user): add UML diagrams (use case, activity, class) for user module
```

---

## THÀNH VIÊN 3 — JobPost & Proposal (Đăng việc & Ứng tuyển)

### Phụ trách module
**Backend:**
- `entity/JobPost.java`, `entity/Proposal.java`
- `enums/JobStatus.java`, `JobType.java`, `ProposalStatus.java`
- `dto/JobPostRequest.java`, `JobPostResponse.java`
- `dto/ProposalRequest.java`, `ProposalResponse.java`
- `repository/JobPostRepository.java`, `ProposalRepository.java`
- `service/JobPostService.java` — CRUD job post, tìm kiếm, lọc
- `service/ProposalService.java` — nộp proposal, chấp nhận/từ chối
- `controller/JobPostController.java` — API `/job-posts/**`
- `controller/ProposalController.java` — API `/proposals/**`

**Frontend:**
- `src/pages/PostJob.jsx` — form đăng việc
- `src/pages/BrowseJobs.jsx` — danh sách việc làm
- `src/pages/JobDetail.jsx` — chi tiết job + nút apply

### UML phải vẽ
1. **Use Case Diagram** — Client: đăng job, xem proposals, chấp nhận proposal | Expert: xem jobs, nộp proposal, rút proposal
2. **Sequence Diagram** — Luồng nộp proposal: Expert → ProposalController → ProposalService → kiểm tra trùng → lưu DB → gửi notification
3. **State Diagram** — Vòng đời JobPost: OPEN → IN_PROGRESS → COMPLETED / CANCELLED; Vòng đời Proposal: PENDING → ACCEPTED / REJECTED

### Danh sách commits (theo thứ tự)
```
1. feat(job): add JobPost entity with enums JobStatus, JobType
2. feat(job): add Proposal entity with ProposalStatus enum
3. feat(job): add DTOs for JobPost and Proposal (request/response)
4. feat(job): implement JobPostService with CRUD and search/filter
5. feat(job): add JobPostController with full REST API
6. feat(job): implement ProposalService with submit/accept/reject logic
7. feat(job): add ProposalController with proposal management endpoints
8. feat(frontend): implement PostJob page with form validation
9. feat(frontend): add BrowseJobs page with search and filter
10. feat(frontend): implement JobDetail page with proposal submission
11. docs(job): add UML diagrams (use case, sequence, state) for job/proposal module
```

---

## THÀNH VIÊN 4 — Project & Milestone (Quản lý dự án & Cột mốc)

### Phụ trách module
**Backend:**
- `entity/Project.java`, `entity/Milestone.java`
- `enums/ProjectStatus.java`, `MilestoneStatus.java`
- `dto/ProjectResponse.java`
- `dto/MilestoneRequest.java`, `MilestoneResponse.java`, `MilestoneSubmitRequest.java`, `MilestoneApproveRequest.java`
- `repository/ProjectRepository.java`, `MilestoneRepository.java`
- `service/ProjectService.java` — tạo project, cập nhật trạng thái, hoàn thành
- `service/MilestoneService.java` — tạo, nộp, duyệt, yêu cầu chỉnh sửa milestone
- `controller/ProjectController.java` — API `/projects/**`
- `controller/MilestoneController.java` — API `/milestones/**`

**Frontend:**
- `src/pages/ProjectDetail.jsx` — toàn bộ trang quản lý dự án (milestones, chat, payments)
- `src/pages/ClientDashboard.jsx` — phần hiển thị danh sách projects
- `src/pages/ExpertDashboard.jsx` — phần hiển thị projects của expert

### UML phải vẽ
1. **Use Case Diagram** — Client: tạo project, tạo milestone, duyệt milestone, hoàn thành project | Expert: nộp deliverable, xem project
2. **State Diagram** — Vòng đời Project: ACTIVE → PAUSED / COMPLETED / DISPUTED / CANCELLED; Vòng đời Milestone: PENDING → SUBMITTED → APPROVED / REVISION_REQUESTED / DISPUTED
3. **Sequence Diagram** — Luồng duyệt milestone: Client approve → MilestoneService → cập nhật status → PaymentService giải ngân → NotificationService gửi thông báo

### Danh sách commits (theo thứ tự)
```
1. feat(project): add Project entity with ProjectStatus enum
2. feat(project): add Milestone entity with MilestoneStatus enum
3. feat(project): add ProjectResponse and Milestone DTOs
4. feat(project): implement ProjectService with lifecycle management
5. feat(project): add ProjectController with REST endpoints
6. feat(milestone): implement MilestoneService with submit/approve/revision logic
7. feat(milestone): add MilestoneController with full milestone management API
8. feat(frontend): implement ProjectDetail page with milestone management UI
9. feat(frontend): add project list sections to ClientDashboard and ExpertDashboard
10. fix(project): add missing state variables for escrow payment modal in ProjectDetail
11. docs(project): add UML diagrams (use case, state, sequence) for project/milestone module
```

---

## THÀNH VIÊN 5 — Payment & Withdrawal (Thanh toán & Rút tiền)

### Phụ trách module
**Backend:**
- `entity/Payment.java`, `entity/Withdrawal.java`
- `enums/PaymentStatus.java`
- `dto/PaymentRequest.java`, `PaymentResponse.java`, `WithdrawalRequest.java`, `WithdrawalResponse.java`
- `dto/CreatePaymentIntentRequest.java`, `ConfirmPaymentRequest.java`, `RefundPaymentRequest.java`
- `dto/PaymentIntentResponse.java`, `PaymentStatusResponse.java`, `WebhookEventResponse.java`
- `repository/PaymentRepository.java`, `WithdrawalRepository.java`
- `service/PaymentService.java` — escrow, release, refund
- `service/StripeService.java` — tích hợp Stripe API
- `service/WithdrawalService.java` — xử lý rút tiền
- `config/StripeConfig.java`
- `controller/PaymentController.java` — API `/payments/**`
- `controller/PaymentIntentController.java` — API `/stripe/**`
- `controller/WithdrawalController.java` — API `/withdrawals/**`
- `controller/StripeWebhookController.java` — Stripe webhook

**Frontend:**
- `src/pages/TransactionHistory.jsx` — lịch sử giao dịch
- Phần escrow payment modal trong `ProjectDetail.jsx`

### UML phải vẽ
1. **Use Case Diagram** — Client: nạp escrow, yêu cầu refund | Expert: rút tiền, xem số dư | Admin: xem transactions
2. **Sequence Diagram** — Luồng Escrow: Client tạo payment → StripeService tạo PaymentIntent → Stripe webhook → xác nhận → tiền vào escrow → milestone approved → giải ngân cho Expert
3. **Class Diagram** — `PaymentService`, `StripeService`, `WithdrawalService`, `Payment`, `Withdrawal` entity và các quan hệ

### Danh sách commits (theo thứ tự)
```
1. feat(payment): add Payment and Withdrawal entities with PaymentStatus enum
2. feat(payment): add Payment and Withdrawal DTOs (request/response)
3. feat(payment): add Stripe DTO classes (PaymentIntent, WebhookEvent, etc.)
4. feat(payment): implement PaymentService with escrow/release/refund logic
5. feat(payment): configure StripeConfig and implement StripeService
6. feat(payment): add PaymentController and PaymentIntentController
7. feat(payment): implement WithdrawalService and WithdrawalController
8. feat(payment): add StripeWebhookController for payment events
9. feat(frontend): implement TransactionHistory page with payment list
10. feat(frontend): add escrow payment modal with amount and method selection
11. docs(payment): add UML diagrams (use case, sequence, class) for payment module
```

---

## THÀNH VIÊN 6 — Chat & Notification & Review (Tương tác)

### Phụ trách module
**Backend:**
- `entity/Message.java`, `entity/Notification.java`, `entity/Review.java`
- `dto/MessageRequest.java`, `MessageResponse.java`
- `dto/NotificationResponse.java`
- `dto/ReviewRequest.java`, `ReviewResponse.java`
- `repository/MessageRepository.java`, `NotificationRepository.java`, `ReviewRepository.java`
- `service/MessageService.java` — gửi/nhận tin nhắn
- `service/NotificationService.java` — tạo/đọc thông báo
- `service/NotificationEventService.java` — sự kiện kích hoạt notification
- `service/EmailService.java` — gửi email thông báo
- `service/ReviewService.java` — tạo/xem đánh giá
- `controller/ChatController.java` — WebSocket `/app/chat/{projectId}`
- `controller/NotificationController.java` — API `/notifications/**`
- `controller/ReviewController.java` — API `/reviews/**`
- `config/WebSocketConfig.java` — cấu hình STOMP WebSocket

**Frontend:**
- `src/pages/ChatList.jsx` — danh sách cuộc trò chuyện
- `src/pages/Notifications.jsx` — trang thông báo
- `src/pages/ReviewPage.jsx` — trang đánh giá

### UML phải vẽ
1. **Use Case Diagram** — Client & Expert: gửi tin nhắn, xem thông báo, viết đánh giá, xem đánh giá
2. **Sequence Diagram** — Luồng WebSocket chat: Client connect → STOMP handshake → subscribe topic → gửi message → server broadcast → Expert nhận realtime
3. **Component Diagram** — Kiến trúc WebSocket: React (SockJS + StompJS) ↔ Spring WebSocket (STOMP Broker) ↔ ChatController ↔ MessageService ↔ MySQL

### Danh sách commits (theo thứ tự)
```
1. feat(chat): add Message entity and MessageRepository
2. feat(chat): add Notification and Review entities with repositories
3. feat(chat): implement MessageService and configure WebSocketConfig
4. feat(chat): add ChatController with STOMP message handling
5. feat(notification): implement NotificationService and NotificationEventService
6. feat(notification): add NotificationController and EmailService
7. feat(review): implement ReviewService and ReviewController
8. feat(frontend): implement ChatList page with conversation list
9. feat(frontend): add Notifications page with read/unread functionality
10. feat(frontend): implement ReviewPage with star rating and comments
11. docs(chat): add UML diagrams (use case, sequence, component) for chat/notification/review module
```

---

## THÀNH VIÊN 7 — AI Module & Admin & ServiceListing (Tính năng nâng cao)

### Phụ trách module
**Backend:**
- `entity/AiModule.java`, `entity/Dispute.java`
- `dto/JobPostAiRequest.java`, `JobPostAiResponse.java`
- `dto/ServiceAiRequest.java`, `ServiceAiResponse.java`
- `dto/ExpertRecommendationResponse.java`
- `dto/AdminAnalyticsResponse.java`
- `dto/ServiceListingRequest.java`, `ServiceListingResponse.java`
- `dto/StatusChangeRequest.java`
- `repository/AiModuleRepository.java`, `DisputeRepository.java`, `ServiceListingRepository.java`
- `service/AiService.java` — tích hợp Google Gemini API
- `service/AdminService.java` — analytics, quản lý users/disputes
- `service/ServiceListingService.java` — dịch vụ của expert
- `controller/AiController.java` — API `/ai/**`
- `controller/AdminController.java` — API `/admin/**`
- `controller/ServiceListingController.java` — API `/services/**`
- `config/OpenApiConfig.java` — Swagger UI

**Frontend:**
- `src/pages/AdminDashboard.jsx` — dashboard quản trị viên
- `src/pages/ExpertServices.jsx` — quản lý dịch vụ của expert
- `src/pages/Marketplace.jsx` — trang khám phá dịch vụ

### UML phải vẽ
1. **Use Case Diagram** — Admin: xem analytics, khóa user, giải quyết dispute, xóa nội dung | Expert: tạo/xóa service listing | AI: gợi ý mô tả job, gợi ý expert
2. **Sequence Diagram** — Luồng AI gợi ý: Frontend gửi request → AiController → AiService → Gemini API → parse response → trả kết quả / fallback nếu lỗi
3. **Deployment Diagram** — Kiến trúc triển khai: Browser → React (Vite) → Spring Boot (Tomcat) → MySQL; Spring Boot → Stripe API; Spring Boot → Google Gemini API

### Danh sách commits (theo thứ tự)
```
1. feat(service): add ServiceListing entity and ServiceListingRepository
2. feat(service): implement ServiceListingService and ServiceListingController
3. feat(ai): add AiModule entity and AI DTOs (request/response)
4. feat(ai): implement AiService with Google Gemini API integration
5. feat(ai): add AiController with job-improve and service-generate endpoints
6. feat(admin): add Dispute entity and implement AdminService with analytics
7. feat(admin): add AdminController with user management and dispute resolution
8. feat(admin): configure OpenApiConfig for Swagger UI documentation
9. feat(frontend): implement AdminDashboard with analytics and management panels
10. feat(frontend): add ExpertServices page and Marketplace page
11. docs(ai-admin): add UML diagrams (use case, sequence, deployment) for AI/Admin module
```

---

## BẢNG TỔNG HỢP

| TV | Tên module | Backend files | Frontend files | UML diagrams | Min commits |
|----|-----------|--------------|----------------|--------------|-------------|
| 1 | Auth & Security | User, JWT, Security, AuthService | AuthContext, Login, Register | Use Case, Sequence, Class | 11 |
| 2 | User Management | UserService, UserController, DataSeeder | ExpertProfile, Landing | Use Case, Activity, Class | 10 |
| 3 | JobPost & Proposal | JobPost, Proposal entities/services | PostJob, BrowseJobs, JobDetail | Use Case, Sequence, State | 11 |
| 4 | Project & Milestone | Project, Milestone entities/services | ProjectDetail, Dashboards | Use Case, State, Sequence | 11 |
| 5 | Payment & Stripe | Payment, Withdrawal, Stripe | TransactionHistory | Use Case, Sequence, Class | 11 |
| 6 | Chat & Notify & Review | Message, Notification, Review, WebSocket | ChatList, Notifications, ReviewPage | Use Case, Sequence, Component | 11 |
| 7 | AI & Admin & Service | AiService, AdminService, ServiceListing | AdminDashboard, Marketplace, ExpertServices | Use Case, Sequence, Deployment | 11 |

---

## THỨ TỰ THỰC HIỆN (SPRINT TIMELINE)

```
SPRINT 1 — Nền tảng (TV1 → TV2 → TV3 phải xong trước)
├── TV1: Auth, Security, User entity     ← commit đầu tiên
├── TV2: User profile, DataSeeder        ← sau khi TV1 merge
└── TV3: JobPost, Proposal               ← sau khi TV2 merge

SPRINT 2 — Nghiệp vụ chính (TV4 → TV5 song song với TV3 xong)
├── TV4: Project, Milestone              ← phụ thuộc TV3 (Proposal → Project)
└── TV5: Payment, Stripe                 ← phụ thuộc TV4 (Milestone → Payment)

SPRINT 3 — Tính năng nâng cao (TV6 → TV7 sau Sprint 2)
├── TV6: Chat, Notification, Review      ← phụ thuộc TV4 (Project → Message)
└── TV7: AI, Admin, ServiceListing       ← phụ thuộc tất cả module trên
```

---

## QUY ĐỊNH NHÁNH GIT

```bash
# Mỗi thành viên tạo nhánh riêng
git checkout -b feature/tv1-auth
git checkout -b feature/tv2-user
git checkout -b feature/tv3-jobpost
git checkout -b feature/tv4-project
git checkout -b feature/tv5-payment
git checkout -b feature/tv6-chat
git checkout -b feature/tv7-ai-admin

# Sau khi xong, tạo Pull Request vào nhánh main
# Cần ít nhất 1 người review trước khi merge
```

---

## FILE UML LƯU TẠI

Mỗi thành viên tạo thư mục và lưu file UML (PlantUML `.puml` hoặc ảnh `.png`):

```
AlTasker/
└── docs/
    ├── uml/
    │   ├── tv1-auth/
    │   │   ├── usecase-auth.puml
    │   │   ├── sequence-login.puml
    │   │   └── class-auth.puml
    │   ├── tv2-user/
    │   ├── tv3-jobpost/
    │   ├── tv4-project/
    │   ├── tv5-payment/
    │   ├── tv6-chat/
    │   └── tv7-ai-admin/
    └── README.md
```
