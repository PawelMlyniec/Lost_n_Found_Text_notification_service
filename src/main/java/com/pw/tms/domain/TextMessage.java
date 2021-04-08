package com.pw.tms.domain;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder(toBuilder = true, setterPrefix = "with")
public class TextMessage {

    private final String id;
    private final String sourceUserId;
    private final String targetUserId;
    private final String content;

    public TextMessageId id() {
        return TextMessageId.of(id);
    }
}
