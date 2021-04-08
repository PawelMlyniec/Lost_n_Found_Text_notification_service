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

    @Nullable private final String id;
    @JsonProperty private final String sourceUserId;
    @JsonProperty private final String targetUserId;
    @JsonProperty private final String content;

    public TextMessage toDomain() {

        return TextMessage.builder()
            .withId(id)
            .withSourceUserId(sourceUserId)
            .withTargetUserId(targetUserId)
            .withContent(content)
            .build();
    }

    public static TextMessageRest fromDomain(TextMessage domain) {

        return TextMessageRest.builder()
            .withId(domain.id().raw())
            .withSourceUserId(domain.sourceUserId())
            .withTargetUserId(domain.targetUserId())
            .withContent(domain.content())
            .build();
    }
}
