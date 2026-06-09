package com.aitasker.controller;

import com.aitasker.entity.Notification;
import com.aitasker.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.aitasker.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    
    /**
     * GET /api/notifications
     * Láº¥y táº¥t cáº£ thĂ´ng bĂ¡o cá»§a ngÆ°á»i dĂ¹ng (phĂ¢n trang)
     */
    @GetMapping
    public ResponseEntity<Page<Notification>> getUserNotifications(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {
        
        Long userId = userRepository.findByEmail(userDetails.getUsername())
                .map(u -> u.getId())
                .orElseThrow(() -> new RuntimeException("KhĂ´ng tĂ¬m tháº¥y ngÆ°á»i dĂ¹ng"));
        
        Page<Notification> notifications = notificationService.getUserNotifications(userId, pageable);
        return ResponseEntity.ok(notifications);
    }
    
    /**
     * GET /api/notifications/unread
     * Láº¥y thĂ´ng bĂ¡o chÆ°a Ä‘á»c (phĂ¢n trang)
     */
    @GetMapping("/unread")
    public ResponseEntity<Page<Notification>> getUnreadNotifications(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {
        
        Long userId = userRepository.findByEmail(userDetails.getUsername())
                .map(u -> u.getId())
                .orElseThrow(() -> new RuntimeException("KhĂ´ng tĂ¬m tháº¥y ngÆ°á»i dĂ¹ng"));
        
        Page<Notification> notifications = notificationService.getUnreadNotificationsPaginated(userId, pageable);
        return ResponseEntity.ok(notifications);
    }
    
    /**
     * GET /api/notifications/unread/count
     * Äáº¿m sá»‘ thĂ´ng bĂ¡o chÆ°a Ä‘á»c
     */
    @GetMapping("/unread/count")
    public ResponseEntity<Long> countUnreadNotifications(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = userRepository.findByEmail(userDetails.getUsername())
                .map(u -> u.getId())
                .orElseThrow(() -> new RuntimeException("KhĂ´ng tĂ¬m tháº¥y ngÆ°á»i dĂ¹ng"));
        
        long count = notificationService.countUnreadNotifications(userId);
        return ResponseEntity.ok(count);
    }
    
    /**
     * PUT /api/notifications/{id}/read
     * ÄĂ¡nh dáº¥u thĂ´ng bĂ¡o lĂ  Ä‘Ă£ Ä‘á»c
     */
    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Notification notification = notificationService.markAsRead(id);
        return ResponseEntity.ok(notification);
    }
    
    /**
     * PUT /api/notifications/read-all
     * ÄĂ¡nh dáº¥u táº¥t cáº£ thĂ´ng bĂ¡o lĂ  Ä‘Ă£ Ä‘á»c
     */
    @PutMapping("/read-all")
    public ResponseEntity<String> markAllAsRead(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = userRepository.findByEmail(userDetails.getUsername())
                .map(u -> u.getId())
                .orElseThrow(() -> new RuntimeException("KhĂ´ng tĂ¬m tháº¥y ngÆ°á»i dĂ¹ng"));
        
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok("Táº¥t cáº£ thĂ´ng bĂ¡o Ä‘Ă£ Ä‘Æ°á»£c Ä‘Ă¡nh dáº¥u lĂ  Ä‘Ă£ Ä‘á»c");
    }
    
    /**
     * DELETE /api/notifications/{id}
     * XĂ³a thĂ´ng bĂ¡o
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("ThĂ´ng bĂ¡o Ä‘Ă£ Ä‘Æ°á»£c xĂ³a");
    }
}
