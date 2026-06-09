# PROMPT COMMIT CHI TIẾT TỪNG THÀNH VIÊN — AITASKER

> Copy từng lệnh git theo đúng thứ tự.  
> Chạy lệnh sau khi đã code xong phần tương ứng.  
> **KHÔNG commit rỗng, KHÔNG commit "update code", KHÔNG commit nhiều module cùng lúc.**

---

## ✅ THÀNH VIÊN 1 — Auth & Security

**Tạo nhánh:**
```bash
git checkout -b feature/tv1-auth
```

**Commit 1** — Sau khi tạo xong `User.java` entity:
```bash
git add backend/src/main/java/com/aitasker/entity/User.java
git add backend/src/main/java/com/aitasker/enums/UserRole.java
git commit -m "feat(auth): add User entity with JPA annotations

- Add User entity with fields: id, email, password, fullName, role, avatar, bio
- Add UserRole enum with CLIENT, EXPERT, ADMIN values
- Configure @PrePersist for auto-set createdAt, updatedAt, default values
- Add @Builder, @Data, @NoArgsConstructor, @AllArgsConstructor via Lombok"
```

**Commit 2** — Sau khi tạo xong `JwtTokenProvider.java`:
```bash
git add backend/src/main/java/com/aitasker/security/JwtTokenProvider.java
git commit -m "feat(auth): implement JwtTokenProvider for token lifecycle

- Implement generateToken() with subject=email, expiration from config
- Implement validateToken() with signature and expiry verification
- Implement getEmailFromToken() for extracting claims
- Use JJWT 0.12.5 with HS256 algorithm and secret key"
```

**Commit 3** — Sau khi tạo `JwtAuthFilter.java` và `CustomUserDetailsService.java`:
```bash
git add backend/src/main/java/com/aitasker/security/JwtAuthFilter.java
git add backend/src/main/java/com/aitasker/security/CustomUserDetailsService.java
git commit -m "feat(auth): add JwtAuthFilter and CustomUserDetailsService

- JwtAuthFilter: extract Bearer token from Authorization header
- Validate token and set SecurityContextHolder authentication
- CustomUserDetailsService: load user by email from UserRepository
- Map UserRole to Spring GrantedAuthority with ROLE_ prefix
- Handle isLocked check to disable locked accounts"
```

**Commit 4** — Sau khi cấu hình `SecurityConfig.java`:
```bash
git add backend/src/main/java/com/aitasker/config/SecurityConfig.java
git commit -m "feat(auth): configure Spring Security with stateless JWT

- Set session management to STATELESS
- Permit public endpoints: /auth/**, /services, /job-posts (GET)
- Require authentication for all other endpoints
- Add JwtAuthFilter before UsernamePasswordAuthenticationFilter
- Configure CORS for http://localhost:5173
- Disable CSRF for REST API"
```

**Commit 5** — Sau khi tạo `AppConfig.java`:
```bash
git add backend/src/main/java/com/aitasker/config/AppConfig.java
git commit -m "feat(auth): configure AppConfig with ObjectMapper and RestTemplate

- Register JavaTimeModule to support LocalDateTime serialization
- Disable WRITE_DATES_AS_TIMESTAMPS for ISO 8601 format output
- Mark ObjectMapper as @Primary to override Spring Boot default
- Configure RestTemplate with 10s connect timeout, 30s read timeout"
```

**Commit 6** — Sau khi tạo DTOs và `AuthService.java`:
```bash
git add backend/src/main/java/com/aitasker/dto/AuthRequest.java
git add backend/src/main/java/com/aitasker/dto/AuthResponse.java
git add backend/src/main/java/com/aitasker/dto/RegisterRequest.java
git add backend/src/main/java/com/aitasker/service/AuthService.java
git commit -m "feat(auth): implement AuthService with register and login logic

- Register: validate email uniqueness, encode password with BCrypt
- Login: authenticate credentials, generate JWT token
- Return AuthResponse with token, id, email, fullName, role
- Throw AppException with appropriate HTTP status on errors"
```

**Commit 7** — Sau khi tạo `AuthController.java`:
```bash
git add backend/src/main/java/com/aitasker/controller/AuthController.java
git commit -m "feat(auth): add AuthController with login and register endpoints

- POST /api/auth/register: create new user account
- POST /api/auth/login: authenticate and return JWT token
- Return 201 for register, 200 for login
- Add @Valid for request body validation"
```

**Commit 8** — Sau khi tạo `AuthContext.jsx` và `axios.js`:
```bash
git add frontend/src/context/AuthContext.jsx
git add frontend/src/api/axios.js
git commit -m "feat(frontend): add AuthContext and axios interceptor for JWT

- AuthContext: manage user state with useState and localStorage
- Auto-restore session from localStorage on app load
- Check JWT expiry with jwtDecode before restoring session
- Axios interceptor: attach Bearer token to all authenticated requests
- Expose login(), register(), logout() functions via context"
```

**Commit 9** — Sau khi tạo `Login.jsx` và `Register.jsx`:
```bash
git add frontend/src/pages/Login.jsx
git add frontend/src/pages/Register.jsx
git commit -m "feat(frontend): implement Login and Register pages

- Login: email/password form with validation, redirect by role after login
- Register: email/password/fullName/role form with error display
- Show toast notification on success and error
- Redirect CLIENT -> /client, EXPERT -> /expert, ADMIN -> /admin
- Handle loading state during API call"
```

**Commit 10** — Sau khi tạo `Navbar.jsx`:
```bash
git add frontend/src/components/layout/Navbar.jsx
git commit -m "feat(frontend): add Navbar with auth-aware navigation

- Show Login/Register links when not authenticated
- Show Dashboard, Notifications, Logout when authenticated
- Display user fullName and role badge
- Highlight active route with CSS class
- Responsive mobile menu toggle"
```

**Commit 11** — Sau khi vẽ UML:
```bash
git add docs/uml/tv1-auth/
git commit -m "docs(auth): add UML diagrams for authentication module

- usecase-auth.puml: Actor Guest/Client/Expert/Admin, use cases login/register/logout
- sequence-login.puml: Browser -> AuthController -> AuthService -> UserRepo -> JwtProvider
- class-auth.puml: User, AuthService, JwtTokenProvider, CustomUserDetailsService classes"
```

---

## ✅ THÀNH VIÊN 2 — User Management & Profile

**Tạo nhánh:**
```bash
git checkout -b feature/tv2-user
```

**Commit 1** — Sau khi tạo DTOs:
```bash
git add backend/src/main/java/com/aitasker/dto/UserDTO.java
git add backend/src/main/java/com/aitasker/dto/UpdateProfileRequest.java
git commit -m "feat(user): add UserDTO and UpdateProfileRequest DTOs

- UserDTO: id, email, fullName, role, avatar, bio, skills, balance, rating, isLocked
- UserDTO.fromEntity() static factory method with null-safe mapping
- UpdateProfileRequest: fullName, avatar, bio, skills, certifications, portfolio, hourlyRate
- Use @Builder pattern for immutable construction"
```

**Commit 2** — Sau khi tạo `UserService.java`:
```bash
git add backend/src/main/java/com/aitasker/service/UserService.java
git commit -m "feat(user): implement UserService with profile management

- getProfile(email): return current user profile as UserDTO
- getUserById(id): find user by ID with ResourceNotFoundException
- updateProfile(email, request): update allowed fields, save to DB
- getAllExperts(): return list of users with EXPERT role
- changePassword(): validate old password, encode and save new one"
```

**Commit 3** — Setelah membuat `UserController.java`:
```bash
git add backend/src/main/java/com/aitasker/controller/UserController.java
git commit -m "feat(user): add UserController with profile endpoints

- GET /api/users/me: return current authenticated user profile
- PUT /api/users/me: update current user profile
- GET /api/users/{id}: get user by ID (public)
- GET /api/users/experts: list all expert users (public)
- Use @AuthenticationPrincipal for current user extraction"
```

**Commit 4** — Sau khi tạo `DataSeeder.java`:
```bash
git add backend/src/main/java/com/aitasker/config/DataSeeder.java
git commit -m "feat(user): add DataSeeder with sample data for development

- Seed 1 ADMIN, 2 CLIENT, 3 EXPERT users with BCrypt passwords
- Seed 3 sample JobPosts with different skills and budgets
- Seed 2 Proposals from expert to client jobs
- Seed 2 Projects in ACTIVE status with sample milestones
- Seed sample ServiceListings for each expert
- Skip seeding if data already exists (idempotent)"
```

**Commit 5** — Sau khi tạo `ExpertProfile.jsx`:
```bash
git add frontend/src/pages/ExpertProfile.jsx
git commit -m "feat(frontend): implement ExpertProfile page with editing

- Fetch current user profile from GET /api/users/me
- Display avatar, fullName, bio, skills, hourlyRate, rating
- Edit mode: inline form for bio, skills, portfolio, certifications
- Call PUT /api/users/me on save with success/error toast
- Show read-only view for non-owner visitors"
```

**Commit 6** — Sau khi tạo `Landing.jsx`:
```bash
git add frontend/src/pages/Landing.jsx
git commit -m "feat(frontend): implement Landing page with cyberpunk UI

- Hero section with gradient title, tagline, CTA buttons
- Stats section: number of experts, clients, projects completed
- Feature cards: AI matching, Escrow payment, Real-time chat
- How it works section: 3 steps for Client and Expert
- Redirect authenticated users to their dashboard"
```

**Commit 7** — Sau khi tạo `Footer.jsx`:
```bash
git add frontend/src/components/layout/Footer.jsx
git commit -m "feat(frontend): add Footer component with links

- Company links: About, Terms, Privacy Policy
- Quick links: Browse Jobs, Marketplace, Post Job
- Social media icons
- Copyright and version info
- Match cyberpunk theme with color variables"
```

**Commit 8** — Sau khi fix mapping lỗi:
```bash
git add backend/src/main/java/com/aitasker/dto/UserDTO.java
git commit -m "fix(user): handle null fields in UserDTO.fromEntity mapping

- Add null check for isLocked field with default false
- Add null check for balance field with default 0.0
- Add null check for rating field with default 0.0
- Prevent NullPointerException when optional fields are missing"
```

**Commit 9** — Sau khi thêm endpoint experts:
```bash
git add backend/src/main/java/com/aitasker/controller/UserController.java
git add backend/src/main/java/com/aitasker/service/UserService.java
git commit -m "feat(user): add GET /users/experts endpoint for expert listing

- Return list of all users with EXPERT role
- Include skills, rating, hourlyRate in response
- Add optional query param ?skill= for filtering by skill
- Used by AI recommendation and client browsing features"
```

**Commit 10** — Sau khi vẽ UML:
```bash
git add docs/uml/tv2-user/
git commit -m "docs(user): add UML diagrams for user management module

- usecase-user.puml: Actor Client/Expert, use cases view/edit profile, list experts
- activity-update-profile.puml: open form -> fill -> validate -> save -> toast
- class-user.puml: UserService, UserController, UserDTO, UpdateProfileRequest"
```

---

## ✅ THÀNH VIÊN 3 — JobPost & Proposal

**Tạo nhánh:**
```bash
git checkout -b feature/tv3-jobpost
```

**Commit 1** — Sau khi tạo `JobPost.java`:
```bash
git add backend/src/main/java/com/aitasker/entity/JobPost.java
git add backend/src/main/java/com/aitasker/enums/JobStatus.java
git add backend/src/main/java/com/aitasker/enums/JobType.java
git add backend/src/main/java/com/aitasker/repository/JobPostRepository.java
git commit -m "feat(job): add JobPost entity with status and type enums

- JobPost entity: id, title, description, budgetMin, budgetMax, skillsRequired
- Fields: timeline, type (FIXED/HOURLY), status (OPEN/IN_PROGRESS/COMPLETED/CANCELLED)
- ManyToOne relation to User (client)
- JobPostRepository with custom queries: findByClientId, findByStatus, searchBySkill
- @PrePersist for auto-set createdAt/updatedAt"
```

**Commit 2** — Sau khi tạo `Proposal.java`:
```bash
git add backend/src/main/java/com/aitasker/entity/Proposal.java
git add backend/src/main/java/com/aitasker/enums/ProposalStatus.java
git add backend/src/main/java/com/aitasker/repository/ProposalRepository.java
git commit -m "feat(job): add Proposal entity with ProposalStatus enum

- Proposal entity: id, coverLetter, proposedBudget, proposedTimeline
- ProposalStatus enum: PENDING, ACCEPTED, REJECTED, WITHDRAWN
- ManyToOne to JobPost and User (expert)
- ProposalRepository: findByJobPostId, findByExpertId, findByJobPostIdAndExpertId
- Unique constraint on (jobPostId, expertId) to prevent duplicate proposals"
```

**Commit 3** — Sau khi tạo DTOs:
```bash
git add backend/src/main/java/com/aitasker/dto/JobPostRequest.java
git add backend/src/main/java/com/aitasker/dto/JobPostResponse.java
git add backend/src/main/java/com/aitasker/dto/ProposalRequest.java
git add backend/src/main/java/com/aitasker/dto/ProposalResponse.java
git commit -m "feat(job): add DTOs for JobPost and Proposal modules

- JobPostRequest: title, description, budgetMin/Max, skillsRequired, timeline, type
- JobPostResponse.fromEntity(): map entity to response with client UserDTO
- ProposalRequest: coverLetter, proposedBudget, proposedTimeline
- ProposalResponse.fromEntity(): include expert UserDTO and job title
- Add @NotBlank, @NotNull validation annotations"
```

**Commit 4** — Sau khi tạo `JobPostService.java`:
```bash
git add backend/src/main/java/com/aitasker/service/JobPostService.java
git commit -m "feat(job): implement JobPostService with CRUD and search

- createJobPost(): validate client role, save with OPEN status
- getJobPostById(): find or throw ResourceNotFoundException
- getAllOpenJobs(): return all OPEN status job posts
- getJobsByClient(email): return client's own job posts
- searchJobs(keyword, skill): filter by title/description and skill
- updateJobPost(): verify ownership before updating
- deleteJobPost(): soft delete or status change to CANCELLED"
```

**Commit 5** — Sau khi tạo `JobPostController.java`:
```bash
git add backend/src/main/java/com/aitasker/controller/JobPostController.java
git commit -m "feat(job): add JobPostController with full REST API

- POST /api/job-posts: create new job (CLIENT only)
- GET /api/job-posts: list all open jobs (public)
- GET /api/job-posts/{id}: get job detail (public)
- GET /api/job-posts/my-posts: client's own jobs (CLIENT only)
- PUT /api/job-posts/{id}: update job (owner only)
- DELETE /api/job-posts/{id}: cancel job (owner only)
- GET /api/job-posts/search?q=&skill=: search jobs"
```

**Commit 6** — Sau khi tạo `ProposalService.java`:
```bash
git add backend/src/main/java/com/aitasker/service/ProposalService.java
git commit -m "feat(job): implement ProposalService with business logic

- submitProposal(): check expert role, check no duplicate, save PENDING
- getProposalsByJob(jobId): list proposals for a job post (client view)
- getMyProposals(email): expert's submitted proposals
- acceptProposal(): change status to ACCEPTED, update job to IN_PROGRESS
- rejectProposal(): change proposal status to REJECTED
- withdrawProposal(): expert can withdraw PENDING proposal"
```

**Commit 7** — Sau khi tạo `ProposalController.java`:
```bash
git add backend/src/main/java/com/aitasker/controller/ProposalController.java
git commit -m "feat(job): add ProposalController with proposal management endpoints

- POST /api/proposals: submit proposal to a job (EXPERT only)
- GET /api/proposals/job/{jobId}: get proposals for job (CLIENT owner)
- GET /api/proposals/my: get my submitted proposals (EXPERT)
- PUT /api/proposals/{id}/accept: accept proposal (CLIENT)
- PUT /api/proposals/{id}/reject: reject proposal (CLIENT)
- DELETE /api/proposals/{id}: withdraw proposal (EXPERT)"
```

**Commit 8** — Sau khi tạo `PostJob.jsx`:
```bash
git add frontend/src/pages/PostJob.jsx
git commit -m "feat(frontend): implement PostJob page with form validation

- Form fields: title, description, budgetMin/Max, skillsRequired, timeline, type
- Client-side validation with error messages
- Submit to POST /api/job-posts with success redirect to /client
- Tag input for skillsRequired with add/remove functionality
- Budget range validation (min must be less than max)"
```

**Commit 9** — Sau khi tạo `BrowseJobs.jsx` và `JobDetail.jsx`:
```bash
git add frontend/src/pages/BrowseJobs.jsx
git add frontend/src/pages/JobDetail.jsx
git commit -m "feat(frontend): add BrowseJobs and JobDetail pages

- BrowseJobs: fetch all open jobs, search by keyword, filter by skill
- Display job cards with budget range, skills tags, posted date
- JobDetail: show full job info + list of proposals (for client)
- Expert proposal form: coverLetter, proposedBudget, proposedTimeline
- Show 'Already Applied' badge if expert already submitted proposal
- Client view: accept/reject buttons for each proposal"
```

**Commit 10** — Sau khi fix lỗi:
```bash
git add backend/src/main/java/com/aitasker/service/ProposalService.java
git commit -m "fix(job): prevent duplicate proposal submission

- Check existing proposal by expertId + jobPostId before inserting
- Throw BadRequestException with message 'Bạn đã ứng tuyển công việc này'
- Add findByJobPostIdAndExpertId query to ProposalRepository"
```

**Commit 11** — Sau khi vẽ UML:
```bash
git add docs/uml/tv3-jobpost/
git commit -m "docs(job): add UML diagrams for jobpost and proposal module

- usecase-job.puml: Client post/edit/delete job, view proposals, accept proposal
- sequence-proposal.puml: Expert submit -> ProposalController -> check duplicate -> save -> notify
- state-job-proposal.puml: JobPost states OPEN/IN_PROGRESS/COMPLETED, Proposal states lifecycle"
```

---

## ✅ THÀNH VIÊN 4 — Project & Milestone

**Tạo nhánh:**
```bash
git checkout -b feature/tv4-project
```

**Commit 1** — Sau khi tạo `Project.java`:
```bash
git add backend/src/main/java/com/aitasker/entity/Project.java
git add backend/src/main/java/com/aitasker/enums/ProjectStatus.java
git add backend/src/main/java/com/aitasker/repository/ProjectRepository.java
git commit -m "feat(project): add Project entity with ProjectStatus enum

- Project entity: id, title, totalAmount, startDate, endDate, status
- ManyToOne to User (client), User (expert), JobPost, ServiceListing
- ProjectStatus: ACTIVE, PAUSED, COMPLETED, CANCELLED, DISPUTED
- Repository: findByClientId, findByExpertId, findByJobPostId
- @PrePersist auto-set createdAt, startDate, default ACTIVE status"
```

**Commit 2** — Sau khi tạo `Milestone.java`:
```bash
git add backend/src/main/java/com/aitasker/entity/Milestone.java
git add backend/src/main/java/com/aitasker/enums/MilestoneStatus.java
git add backend/src/main/java/com/aitasker/repository/MilestoneRepository.java
git commit -m "feat(project): add Milestone entity with MilestoneStatus enum

- Milestone entity: id, title, description, amount, deliverables, feedback
- MilestoneStatus: PENDING, SUBMITTED, APPROVED, REVISION_REQUESTED, DISPUTED
- ManyToOne to Project
- Repository: findByProjectId, findByProjectIdAndStatus
- @PrePersist auto-set createdAt, default PENDING status"
```

**Commit 3** — Sau khi tạo DTOs:
```bash
git add backend/src/main/java/com/aitasker/dto/ProjectResponse.java
git add backend/src/main/java/com/aitasker/dto/MilestoneRequest.java
git add backend/src/main/java/com/aitasker/dto/MilestoneResponse.java
git add backend/src/main/java/com/aitasker/dto/MilestoneSubmitRequest.java
git add backend/src/main/java/com/aitasker/dto/MilestoneApproveRequest.java
git commit -m "feat(project): add ProjectResponse and Milestone DTOs

- ProjectResponse.fromEntity(): include client/expert UserDTO, dates as LocalDateTime
- MilestoneRequest: title, amount, description for creation
- MilestoneResponse.fromEntity(): include project id, status, deliverables, feedback
- MilestoneSubmitRequest: deliverables field for expert submission
- MilestoneApproveRequest: feedback field for client review"
```

**Commit 4** — Sau khi tạo `ProjectService.java`:
```bash
git add backend/src/main/java/com/aitasker/service/ProjectService.java
git commit -m "feat(project): implement ProjectService with full lifecycle

- createProject(proposalId): create project from accepted proposal
- getProjectById(id): find with access control (client or expert only)
- getProjectsByClient(email): return client's projects
- getProjectsByExpert(email): return expert's projects
- completeProject(id): set COMPLETED status, trigger review prompt
- pauseProject(id) / resumeProject(id): toggle PAUSED/ACTIVE
- disputeProject(id, reason): set DISPUTED, create Dispute record"
```

**Commit 5** — Sau khi tạo `ProjectController.java`:
```bash
git add backend/src/main/java/com/aitasker/controller/ProjectController.java
git commit -m "feat(project): add ProjectController with project management API

- GET /api/projects/{id}: get project detail (authenticated participants)
- GET /api/projects/my: get current user's projects
- PUT /api/projects/{id}/complete: mark project completed (CLIENT)
- PUT /api/projects/{id}/pause: pause project (CLIENT)
- PUT /api/projects/{id}/resume: resume paused project (CLIENT)
- PUT /api/projects/{id}/dispute: open dispute on project
- GET /api/projects/{id}/payments: get payment history for project"
```

**Commit 6** — Sau khi tạo `MilestoneService.java`:
```bash
git add backend/src/main/java/com/aitasker/service/MilestoneService.java
git commit -m "feat(milestone): implement MilestoneService with workflow logic

- createMilestone(projectId, request): client creates milestone, status PENDING
- submitMilestone(id, deliverables): expert submits deliverables, status SUBMITTED
- approveMilestone(id, feedback): client approves, status APPROVED, trigger payment release
- requestRevision(id, feedback): client requests changes, status REVISION_REQUESTED
- disputeMilestone(id, reason): set DISPUTED status for admin review
- getMilestonesByProject(projectId): return all milestones for a project"
```

**Commit 7** — Sau khi tạo `MilestoneController.java`:
```bash
git add backend/src/main/java/com/aitasker/controller/MilestoneController.java
git commit -m "feat(milestone): add MilestoneController with milestone management API

- POST /api/projects/{projectId}/milestones: create milestone (CLIENT)
- GET /api/projects/{projectId}/milestones: list milestones
- PUT /api/milestones/{id}/submit: expert submits deliverables
- PUT /api/milestones/{id}/approve: client approves and releases payment
- PUT /api/milestones/{id}/revision: client requests revision
- PUT /api/milestones/{id}/dispute: raise dispute on milestone"
```

**Commit 8** — Sau khi tạo `ProjectDetail.jsx`:
```bash
git add frontend/src/pages/ProjectDetail.jsx
git commit -m "feat(frontend): implement ProjectDetail page with full project management

- Display project info: title, status, client/expert names, WebSocket status
- Milestone list with status badges, amount, deliverables, feedback
- Client actions: create milestone, fund escrow, approve/request revision
- Expert actions: submit deliverables with text input
- Escrow payment modal: amount input, payment method selector
- Project actions: pause, resume, complete, dispute
- Real-time chat via WebSocket (SockJS + StompJS) with REST fallback"
```

**Commit 9** — Sau khi thêm phần projects vào dashboards:
```bash
git add frontend/src/pages/ClientDashboard.jsx
git add frontend/src/pages/ExpertDashboard.jsx
git commit -m "feat(frontend): add project sections to Client and Expert dashboards

- ClientDashboard: active projects list with quick links to ProjectDetail
- ExpertDashboard: active projects with pending milestone count badge
- Show project status with color-coded indicators
- Link to /projects/:id for each project card
- Show totalAmount and partner name for each project"
```

**Commit 10** — Sau khi fix state variables:
```bash
git add frontend/src/pages/ProjectDetail.jsx
git commit -m "fix(frontend): add missing state variables for escrow payment modal

- Add fundingMilestoneId, escrowAmount, paymentMethod, creatingPayment states
- Refactor handleFundEscrow to open modal instead of direct confirm()
- Add handleConfirmEscrowPayment function for modal submission
- Prevent React crash from undefined variable references"
```

**Commit 11** — Sau khi vẽ UML:
```bash
git add docs/uml/tv4-project/
git commit -m "docs(project): add UML diagrams for project and milestone module

- usecase-project.puml: Client create/manage project, milestones | Expert submit work
- state-project-milestone.puml: Project states ACTIVE/PAUSED/COMPLETED/DISPUTED
- sequence-approve.puml: approve milestone -> MilestoneService -> PaymentService release -> notify"
```

---

## ✅ THÀNH VIÊN 5 — Payment & Stripe & Withdrawal

**Tạo nhánh:**
```bash
git checkout -b feature/tv5-payment
```

**Commit 1** — Sau khi tạo entities:
```bash
git add backend/src/main/java/com/aitasker/entity/Payment.java
git add backend/src/main/java/com/aitasker/entity/Withdrawal.java
git add backend/src/main/java/com/aitasker/enums/PaymentStatus.java
git add backend/src/main/java/com/aitasker/repository/PaymentRepository.java
git add backend/src/main/java/com/aitasker/repository/WithdrawalRepository.java
git commit -m "feat(payment): add Payment and Withdrawal entities with repositories

- Payment: id, amount, status, paymentMethod, escrowStatus, ManyToOne Project/Milestone
- PaymentStatus enum: PENDING, ESCROWED, RELEASED, REFUNDED, FAILED
- Withdrawal: id, amount, bankAccount, status, ManyToOne User (expert)
- Repository queries: findByProjectId, findByMilestoneId, findByStatus
- @PrePersist default PENDING status"
```

**Commit 2** — Sau khi tạo Payment DTOs:
```bash
git add backend/src/main/java/com/aitasker/dto/PaymentRequest.java
git add backend/src/main/java/com/aitasker/dto/PaymentResponse.java
git add backend/src/main/java/com/aitasker/dto/WithdrawalRequest.java
git add backend/src/main/java/com/aitasker/dto/WithdrawalResponse.java
git commit -m "feat(payment): add Payment and Withdrawal DTOs

- PaymentRequest: projectId, milestoneId, amount, paymentMethod
- PaymentResponse.fromEntity(): include project title, milestone title, status
- WithdrawalRequest: amount, bankAccount, bankName, accountHolder
- WithdrawalResponse.fromEntity(): include expert name, status, requestedAt"
```

**Commit 3** — Sau khi tạo Stripe DTOs:
```bash
git add backend/src/main/java/com/aitasker/dto/CreatePaymentIntentRequest.java
git add backend/src/main/java/com/aitasker/dto/ConfirmPaymentRequest.java
git add backend/src/main/java/com/aitasker/dto/RefundPaymentRequest.java
git add backend/src/main/java/com/aitasker/dto/PaymentIntentResponse.java
git add backend/src/main/java/com/aitasker/dto/PaymentStatusResponse.java
git add backend/src/main/java/com/aitasker/dto/WebhookEventResponse.java
git commit -m "feat(payment): add Stripe-specific DTO classes

- CreatePaymentIntentRequest: projectId, milestoneId, amount, paymentMethod
- ConfirmPaymentRequest: paymentIntentId, stripeToken
- RefundPaymentRequest: paymentId, reason
- PaymentIntentResponse: clientSecret, paymentIntentId, amount, currency, status
- PaymentStatusResponse: paymentIntentId, status, amount, paymentMethod
- WebhookEventResponse: eventId, type, status, timestamp"
```

**Commit 4** — Sau khi tạo `PaymentService.java`:
```bash
git add backend/src/main/java/com/aitasker/service/PaymentService.java
git commit -m "feat(payment): implement PaymentService with escrow workflow

- createEscrowPayment(): client funds escrow for milestone, status ESCROWED
- releasePayment(milestoneId): release escrowed funds to expert balance
- refundPayment(paymentId, reason): refund to client balance, status REFUNDED
- getPaymentsByProject(projectId): list all payments for a project
- calculatePlatformFee(amount): compute 5% platform fee
- updateExpertBalance(): add released amount to expert User.balance"
```

**Commit 5** — Sau khi cấu hình `StripeConfig.java` và `StripeService.java`:
```bash
git add backend/src/main/java/com/aitasker/config/StripeConfig.java
git add backend/src/main/java/com/aitasker/service/StripeService.java
git commit -m "feat(payment): configure Stripe and implement StripeService

- StripeConfig: inject API keys, init Stripe.apiKey on @PostConstruct
- StripeService.createPaymentIntent(): create Stripe PaymentIntent in VND
- Add metadata: projectId, milestoneId, clientEmail to PaymentIntent
- StripeService.confirmPaymentIntent(): handle payment confirmation
- StripeService.refundPayment(): process refund via Stripe API
- StripeService.processExpertPayout(): create Stripe Payout to expert"
```

**Commit 6** — Sau khi tạo `PaymentController.java` và `PaymentIntentController.java`:
```bash
git add backend/src/main/java/com/aitasker/controller/PaymentController.java
git add backend/src/main/java/com/aitasker/controller/PaymentIntentController.java
git commit -m "feat(payment): add PaymentController and PaymentIntentController

- POST /api/payments: create escrow payment for milestone (CLIENT)
- GET /api/projects/{id}/payments: list payments for a project
- POST /api/stripe/payment-intent: create Stripe PaymentIntent
- GET /api/stripe/payment-intent/{id}: get payment status
- POST /api/stripe/confirm-payment: confirm payment success
- POST /api/stripe/refund: process refund
- POST /api/stripe/payout: trigger expert payout"
```

**Commit 7** — Sau khi tạo `WithdrawalService.java` và `WithdrawalController.java`:
```bash
git add backend/src/main/java/com/aitasker/service/WithdrawalService.java
git add backend/src/main/java/com/aitasker/controller/WithdrawalController.java
git commit -m "feat(payment): implement WithdrawalService and WithdrawalController

- requestWithdrawal(): validate expert balance >= amount, create PENDING request
- approveWithdrawal(id): admin approves, deduct from balance, status APPROVED
- rejectWithdrawal(id, reason): admin rejects, return funds, status REJECTED
- GET /api/withdrawals/my: expert's withdrawal history
- POST /api/withdrawals: request withdrawal (EXPERT)
- PUT /api/withdrawals/{id}/approve: approve withdrawal (ADMIN)"
```

**Commit 8** — Sau khi tạo `StripeWebhookController.java`:
```bash
git add backend/src/main/java/com/aitasker/controller/StripeWebhookController.java
git commit -m "feat(payment): add StripeWebhookController for payment events

- POST /api/stripe/webhook: receive Stripe webhook events
- Verify webhook signature using webhookSecret
- Handle payment_intent.succeeded: confirm payment in DB
- Handle payment_intent.payment_failed: update payment status FAILED
- Handle charge.refunded: update payment status REFUNDED
- Handle payout.paid: log expert payout completion"
```

**Commit 9** — Sau khi tạo `TransactionHistory.jsx`:
```bash
git add frontend/src/pages/TransactionHistory.jsx
git commit -m "feat(frontend): implement TransactionHistory page

- Fetch payments from GET /api/payments/my or by project
- Display transactions in table: ID, project, milestone, amount, status, date
- Color code status: ESCROWED=yellow, RELEASED=green, REFUNDED=red
- Filter by status and date range
- Show total balance for expert with withdrawal button
- Withdrawal request modal with bank account form"
```

**Commit 10** — Sau khi fix và test payment flow:
```bash
git add backend/src/main/java/com/aitasker/service/PaymentService.java
git commit -m "fix(payment): correct balance update logic on payment release

- Fix double-release bug when approving milestone multiple times
- Add check: only release ESCROWED payments, skip RELEASED ones
- Ensure atomic balance update with @Transactional
- Add null check for milestone.project before accessing expert"
```

**Commit 11** — Sau khi vẽ UML:
```bash
git add docs/uml/tv5-payment/
git commit -m "docs(payment): add UML diagrams for payment and withdrawal module

- usecase-payment.puml: Client fund escrow/refund, Expert withdraw, Admin approve
- sequence-escrow.puml: Stripe PaymentIntent flow -> webhook -> escrow -> milestone approve -> release
- class-payment.puml: PaymentService, StripeService, WithdrawalService, Payment, Withdrawal"
```

---

## ✅ THÀNH VIÊN 6 — Chat & Notification & Review

**Tạo nhánh:**
```bash
git checkout -b feature/tv6-chat
```

**Commit 1** — Sau khi tạo `Message.java`:
```bash
git add backend/src/main/java/com/aitasker/entity/Message.java
git add backend/src/main/java/com/aitasker/repository/MessageRepository.java
git commit -m "feat(chat): add Message entity and MessageRepository

- Message entity: id, content, senderId, senderName, projectId, createdAt
- ManyToOne to Project (via projectId)
- MessageRepository: findByProjectIdOrderByCreatedAtAsc
- @PrePersist auto-set createdAt timestamp
- Add index on projectId for query performance"
```

**Commit 2** — Sau khi tạo `Notification.java` và `Review.java`:
```bash
git add backend/src/main/java/com/aitasker/entity/Notification.java
git add backend/src/main/java/com/aitasker/entity/Review.java
git add backend/src/main/java/com/aitasker/repository/NotificationRepository.java
git add backend/src/main/java/com/aitasker/repository/ReviewRepository.java
git commit -m "feat(notification): add Notification and Review entities

- Notification: id, userId, title, message, type, isRead, createdAt
- Review: id, projectId, reviewerId, revieweeId, rating (1-5), comment, createdAt
- NotificationRepository: findByUserId, findByUserIdAndIsRead, countUnread
- ReviewRepository: findByRevieweeId, findByProjectId, avgRatingByRevieweeId"
```

**Commit 3** — Sau khi tạo `MessageService.java` và `WebSocketConfig.java`:
```bash
git add backend/src/main/java/com/aitasker/service/MessageService.java
git add backend/src/main/java/com/aitasker/config/WebSocketConfig.java
git commit -m "feat(chat): implement MessageService and configure WebSocket

- MessageService.sendMessage(): save to DB, return MessageResponse
- MessageService.getMessagesByProject(): return sorted message history
- WebSocketConfig: enable STOMP, set /ws endpoint with SockJS fallback
- Configure /app prefix for application destinations
- Configure /topic prefix for broadcast destinations
- Allow WebSocket connections from http://localhost:5173"
```

**Commit 4** — Sau khi tạo `ChatController.java`:
```bash
git add backend/src/main/java/com/aitasker/controller/ChatController.java
git add backend/src/main/java/com/aitasker/dto/MessageRequest.java
git add backend/src/main/java/com/aitasker/dto/MessageResponse.java
git commit -m "feat(chat): add ChatController with STOMP WebSocket handling

- @MessageMapping('/chat/{projectId}'): receive and broadcast messages
- Broadcast to /topic/project/{projectId} for all subscribers
- MessageRequest: content, senderEmail fields
- MessageResponse.fromEntity(): id, content, senderId, senderName, createdAt
- GET /api/messages/{projectId}: REST fallback for message history
- POST /api/messages/{projectId}: REST fallback for sending messages"
```

**Commit 5** — Sau khi tạo `NotificationService.java` và `NotificationEventService.java`:
```bash
git add backend/src/main/java/com/aitasker/service/NotificationService.java
git add backend/src/main/java/com/aitasker/service/NotificationEventService.java
git commit -m "feat(notification): implement NotificationService and NotificationEventService

- NotificationService.createNotification(): save notification for user
- NotificationService.getNotifications(email): return user's notifications
- NotificationService.markAsRead(id): set isRead = true
- NotificationService.getUnreadCount(email): return unread count
- NotificationEventService: event-driven notification triggers
- Trigger on: proposal accepted, milestone submitted/approved, payment released
- NotificationEventService.notifyPaymentConfirmed(): payment notification"
```

**Commit 6** — Sau khi tạo `NotificationController.java` và `EmailService.java`:
```bash
git add backend/src/main/java/com/aitasker/controller/NotificationController.java
git add backend/src/main/java/com/aitasker/service/EmailService.java
git add backend/src/main/java/com/aitasker/dto/NotificationResponse.java
git commit -m "feat(notification): add NotificationController and EmailService

- GET /api/notifications: get current user's notifications
- PUT /api/notifications/{id}/read: mark notification as read
- PUT /api/notifications/read-all: mark all as read
- GET /api/notifications/unread-count: get unread count for badge
- EmailService.sendEmail(): send HTML email via Spring Mail (SMTP)
- Email templates for: proposal accepted, milestone approved, payment released"
```

**Commit 7** — Sau khi tạo `ReviewService.java` và `ReviewController.java`:
```bash
git add backend/src/main/java/com/aitasker/service/ReviewService.java
git add backend/src/main/java/com/aitasker/controller/ReviewController.java
git add backend/src/main/java/com/aitasker/dto/ReviewRequest.java
git add backend/src/main/java/com/aitasker/dto/ReviewResponse.java
git commit -m "feat(review): implement ReviewService and ReviewController

- ReviewService.createReview(): validate project COMPLETED, no duplicate review
- ReviewService.getReviewsByUser(userId): return reviews received by user
- Update User.rating = avg(review ratings) after each new review
- ReviewRequest: projectId, revieweeId, rating (1-5), comment
- GET /api/reviews/user/{id}: get reviews for a user (public)
- POST /api/reviews: submit review after project completion"
```

**Commit 8** — Sau khi tạo `ChatList.jsx`:
```bash
git add frontend/src/pages/ChatList.jsx
git commit -m "feat(frontend): implement ChatList page with conversations

- Fetch user's active projects from /api/projects/my
- Display each project as a conversation item with partner name
- Show last message preview and unread count badge
- Click to navigate to /projects/:id (ProjectDetail with chat)
- Sort by latest message timestamp
- Show online/offline status indicator"
```

**Commit 9** — Sau khi tạo `Notifications.jsx`:
```bash
git add frontend/src/pages/Notifications.jsx
git commit -m "feat(frontend): add Notifications page with read management

- Fetch notifications from GET /api/notifications
- Group by date: Today, Yesterday, Earlier
- Show notification icon based on type (proposal, payment, milestone, etc.)
- Click notification to mark as read and navigate to relevant page
- Mark All Read button: PUT /api/notifications/read-all
- Badge in Navbar showing unread count, auto-refresh every 30s"
```

**Commit 10** — Sau khi tạo `ReviewPage.jsx`:
```bash
git add frontend/src/pages/ReviewPage.jsx
git commit -m "feat(frontend): implement ReviewPage with star rating

- Load project info and determine who to review (partner)
- Interactive star rating component (1-5 stars)
- Comment textarea for review text
- Submit to POST /api/reviews with projectId and revieweeId
- Prevent reviewing same project twice (show existing review)
- Redirect to dashboard after successful submission"
```

**Commit 11** — Sau khi vẽ UML:
```bash
git add docs/uml/tv6-chat/
git commit -m "docs(chat): add UML diagrams for chat, notification and review modules

- usecase-chat.puml: Client/Expert send message, view notifications, write review
- sequence-websocket.puml: React connect SockJS -> STOMP handshake -> subscribe -> broadcast
- component-websocket.puml: React (SockJS+Stomp) <-> Spring WebSocket <-> ChatController <-> DB"
```

---

## ✅ THÀNH VIÊN 7 — AI & Admin & ServiceListing

**Tạo nhánh:**
```bash
git checkout -b feature/tv7-ai-admin
```

**Commit 1** — Sau khi tạo `ServiceListing.java`:
```bash
git add backend/src/main/java/com/aitasker/entity/ServiceListing.java
git add backend/src/main/java/com/aitasker/repository/ServiceListingRepository.java
git add backend/src/main/java/com/aitasker/dto/ServiceListingRequest.java
git add backend/src/main/java/com/aitasker/dto/ServiceListingResponse.java
git commit -m "feat(service): add ServiceListing entity with DTOs and repository

- ServiceListing: id, title, category, description, price, deliveryTime, ManyToOne User
- ServiceListingRepository: findByExpertId, findByCategory, searchByTitle
- ServiceListingRequest: title, category, description, price, deliveryTime
- ServiceListingResponse.fromEntity(): include expert UserDTO
- Index on category for marketplace filtering"
```

**Commit 2** — Sau khi tạo `ServiceListingService.java` và `ServiceListingController.java`:
```bash
git add backend/src/main/java/com/aitasker/service/ServiceListingService.java
git add backend/src/main/java/com/aitasker/controller/ServiceListingController.java
git commit -m "feat(service): implement ServiceListingService and controller

- createService(): validate EXPERT role, save listing
- getServicesByExpert(email): return expert's own services
- getAllServices(): return all active listings (public)
- getServicesByCategory(category): filter by category
- updateService(): verify ownership, update fields
- deleteService(): remove listing (owner only)
- GET /api/services: list all (public), POST /api/services: create (EXPERT)
- GET /api/services/my: own listings, DELETE /api/services/{id}: delete"
```

**Commit 3** — Sau khi tạo `AiModule.java` và AI DTOs:
```bash
git add backend/src/main/java/com/aitasker/entity/AiModule.java
git add backend/src/main/java/com/aitasker/repository/AiModuleRepository.java
git add backend/src/main/java/com/aitasker/dto/JobPostAiRequest.java
git add backend/src/main/java/com/aitasker/dto/JobPostAiResponse.java
git add backend/src/main/java/com/aitasker/dto/ServiceAiRequest.java
git add backend/src/main/java/com/aitasker/dto/ServiceAiResponse.java
git add backend/src/main/java/com/aitasker/dto/ExpertRecommendationResponse.java
git commit -m "feat(ai): add AiModule entity and all AI DTOs

- AiModule entity: track AI usage logs (type, input, output, timestamp)
- JobPostAiRequest: title, description, budgetMin/Max, skillsRequired, timeline
- JobPostAiResponse: improvedDescription, recommendedSkills, suggestedBudget, tips
- ServiceAiRequest: title, category, price, deliveryTime, description
- ServiceAiResponse: description, deliveryProcess, keywords
- ExpertRecommendationResponse: expertId, fullName, rating, compatibilityScore, specializations"
```

**Commit 4** — Sau khi implement `AiService.java`:
```bash
git add backend/src/main/java/com/aitasker/service/AiService.java
git commit -m "feat(ai): implement AiService with Google Gemini API integration

- callGeminiApi(prompt): HTTP POST to Gemini v1beta endpoint with API key
- improveJobPost(request): build prompt, call Gemini, parse JSON response
- generateServiceDetails(request): generate professional service description
- recommendExperts(jobPostId): rule-based expert matching with rating/skills
- Fallback responses when Gemini API fails or returns invalid JSON
- Fix model URL to gemini-1.5-flash (gemini-pro deprecated)
- Handle null skillsRequired in fallback with safe null check"
```

**Commit 5** — Sau khi tạo `AiController.java`:
```bash
git add backend/src/main/java/com/aitasker/controller/AiController.java
git commit -m "feat(ai): add AiController with AI-powered endpoints

- POST /api/ai/improve-job: optimize job post description with Gemini AI
- POST /api/ai/generate-service: auto-generate service listing description
- GET /api/ai/recommend-experts/{jobId}: get AI-ranked expert recommendations
- All endpoints require authentication
- Return fallback data if AI service unavailable"
```

**Commit 6** — Sau khi tạo `Dispute.java` và `AdminService.java`:
```bash
git add backend/src/main/java/com/aitasker/entity/Dispute.java
git add backend/src/main/java/com/aitasker/repository/DisputeRepository.java
git add backend/src/main/java/com/aitasker/service/AdminService.java
git add backend/src/main/java/com/aitasker/dto/AdminAnalyticsResponse.java
git commit -m "feat(admin): add Dispute entity and implement AdminService

- Dispute entity: projectId, clientName, expertName, title, amount, reason, status
- DisputeRepository: findAll, findByStatus, findByProjectId
- AdminService.getAllUsers(): return all users as UserDTO list
- AdminService.toggleUserLock(): lock/unlock user account
- AdminService.getAnalytics(): totalRevenue, transactions, newUsers, topExperts
- AdminService.getAllDisputes(): return all disputes (seed sample if empty)
- AdminService.resolveDispute(id, REFUND/RELEASE): update balances and statuses"
```

**Commit 7** — Sau khi tạo `AdminController.java`:
```bash
git add backend/src/main/java/com/aitasker/controller/AdminController.java
git commit -m "feat(admin): add AdminController with management endpoints

- GET /api/admin/users: list all users (ADMIN only)
- PUT /api/admin/users/{id}/toggle-lock: lock/unlock user
- GET /api/admin/analytics: get platform analytics
- GET /api/admin/disputes: list all disputes
- PUT /api/admin/disputes/{id}/resolve?action=REFUND|RELEASE: resolve dispute
- DELETE /api/admin/job-posts/{id}: remove inappropriate job post
- DELETE /api/admin/reviews/{id}: remove inappropriate review"
```

**Commit 8** — Sau khi cấu hình `OpenApiConfig.java`:
```bash
git add backend/src/main/java/com/aitasker/config/OpenApiConfig.java
git commit -m "feat(admin): configure OpenApiConfig for Swagger UI documentation

- Set API title: AiTasker Platform API, version 1.0
- Configure JWT Bearer authentication scheme in Swagger
- Add API description with module overview
- Group endpoints by tags: auth, user, job, project, payment, ai, admin
- Access Swagger UI at http://localhost:8080/swagger-ui.html"
```

**Commit 9** — Sau khi tạo `AdminDashboard.jsx`:
```bash
git add frontend/src/pages/AdminDashboard.jsx
git commit -m "feat(frontend): implement AdminDashboard with full management UI

- Analytics section: total revenue, transactions, new users, top experts
- User management table: list all users, lock/unlock toggle button
- Disputes table: list disputes with resolve REFUND/RELEASE buttons
- Job posts moderation: list all jobs with delete option
- Reviews moderation: list all reviews with delete option
- Real-time data fetch on mount with loading states"
```

**Commit 10** — Sau khi tạo `ExpertServices.jsx` và `Marketplace.jsx`:
```bash
git add frontend/src/pages/ExpertServices.jsx
git add frontend/src/pages/Marketplace.jsx
git commit -m "feat(frontend): add ExpertServices page and Marketplace page

- ExpertServices: fetch /api/services/my, display own service cards
- Create new service form: title, category, price, deliveryTime, description
- AI generate button: call /api/ai/generate-service for auto description
- Edit/Delete existing services with confirm dialog
- Marketplace: public page listing all expert services
- Filter by category: AI, Development, Design, Marketing, etc.
- Search by keyword, sort by price/rating"
```

**Commit 11** — Sau khi vẽ UML:
```bash
git add docs/uml/tv7-ai-admin/
git commit -m "docs(ai-admin): add UML diagrams for AI, Admin and ServiceListing modules

- usecase-admin.puml: Admin manage users/disputes, Expert manage services, AI features
- sequence-ai.puml: Frontend -> AiController -> AiService -> Gemini API -> parse -> fallback
- deployment.puml: Browser -> React/Vite -> Spring Boot/Tomcat -> MySQL + Stripe API + Gemini API"
```

---

## 🔁 MERGE VÀO MAIN

Sau khi mỗi thành viên hoàn thành tất cả commits, tạo Pull Request:

```bash
# Push nhánh lên remote
git push origin feature/tv1-auth   # TV1
git push origin feature/tv2-user   # TV2
# ... (tương tự cho TV3-TV7)

# Tạo Pull Request trên GitHub/GitLab
# Title: "feat: complete [module-name] module - TV[n]"
# Description: liệt kê các tính năng đã implement

# Sau khi được review và approve, merge vào main
git checkout main
git pull origin main
git merge feature/tv1-auth
git push origin main
```

**Lệnh kiểm tra lịch sử commit của từng thành viên:**
```bash
git log --oneline --author="TenThanhVien" --all
```
