package com.pw.tms.domain.textmessage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor(staticName = "of")
public class TextMessageId {

    @Getter(AccessLevel.NONE)
    private final String id;

    public String raw()
    {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
