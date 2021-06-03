package com.pw.tms.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class SearchTextMessageQuery {

    private final String firstUserId;
    private final String secondUserId;
    private final String targetUserId;
    private final String content;
    private final Boolean isRead;
    private final Instant sentAt;
    private final Boolean onlyNewestMessagesForChats;
}
