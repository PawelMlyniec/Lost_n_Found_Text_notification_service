package com.pw.tms.infrastructure.adapters.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pw.tms.domain.TextMessage;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.checkerframework.checker.nullness.qual.Nullable;

@Data
@With
@Builder(toBuilder = true, setterPrefix = "with")
public class TextMessageRest {

    @JsonProperty @Nullable private final String textMessageId;
    @JsonProperty private final String sourceUserId;
    @JsonProperty private final String targetUserId;
    @JsonProperty private final String subject;
    @JsonProperty private final String content;

    public TextMessage toDomain() {

        return TextMessage.builder()
            .withId(textMessageId)
            .withSourceUserId(sourceUserId)
            .withTargetUserId(targetUserId)
            .withSubject(subject)
            .withContent(content)
            .build();
    }

    public static TextMessageRest fromDomain(TextMessage domain) {

        return TextMessageRest.builder()
            .withTextMessageId(domain.id().raw())
            .withSourceUserId(domain.sourceUserId())
            .withTargetUserId(domain.targetUserId())
            .withSubject(domain.subject())
            .withContent(domain.content())
            .build();
    }
}
