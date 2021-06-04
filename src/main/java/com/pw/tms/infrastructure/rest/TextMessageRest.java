package com.pw.tms.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pw.tms.domain.textmessage.TextMessage;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Data
@With
@Builder(toBuilder = true, setterPrefix = "with")
public class TextMessageRest {

    @JsonProperty @Nullable private final String textMessageId;
    @JsonProperty private final String sourceUserId;
    @JsonProperty private final String targetUserId;
    @JsonProperty private final String content;
    @JsonProperty private final Boolean isRead;
    @JsonProperty @Nullable private final OffsetDateTime sentAt;

    public TextMessage toDomain() {

        return TextMessage.builder()
            .withId(textMessageId)
            .withSourceUserId(sourceUserId)
            .withTargetUserId(targetUserId)
            .withContent(content)
            .withIsRead(isRead)
            .withSentAt(Optional.ofNullable(sentAt)
                    .map(OffsetDateTime::toInstant)
                    .orElse(null))
            .build();
    }

    public static TextMessageRest fromDomain(TextMessage domain) {

        return TextMessageRest.builder()
            .withTextMessageId(domain.id().raw())
            .withSourceUserId(domain.sourceUserId())
            .withTargetUserId(domain.targetUserId())
            .withContent(domain.content())
            .withIsRead(domain.isRead())
            .withSentAt(domain.sentAt().atOffset(ZoneOffset.UTC))
            .build();
    }
}
