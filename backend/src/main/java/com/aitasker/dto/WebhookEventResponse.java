package com.aitasker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookEventResponse {
    private String eventId;
    private String type;
    private String status;
    private LocalDateTime timestamp;
}
