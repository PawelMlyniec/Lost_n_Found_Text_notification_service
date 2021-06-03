package com.pw.tms.domain;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.Instant;

@Data
@With
@Builder(toBuilder = true, setterPrefix = "with")
public class TextMessage {

    private final String id;
    private final String sourceUserId;
    private final String targetUserId;
    private final String chatId;
    private final String content;
    private Boolean isRead;
    private Instant sentAt;

    public TextMessageId id() {
        return TextMessageId.of(id);
    }
}
