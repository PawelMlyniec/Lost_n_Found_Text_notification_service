package com.pw.tms.domain.textmessage;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import java.io.Serial;
import java.time.Instant;

import static com.querydsl.core.types.PathMetadataFactory.*;

public class QTextMessage extends EntityPathBase<TextMessage> {

    @Serial
    private static final long serialVersionUID = -1484827847L;

    public static final QTextMessage textMessage = new QTextMessage("textMessage");

    public final StringPath id = createString("id");

    public final StringPath sourceUserId = createString("sourceUserId");

    public final StringPath targetUserId = createString("targetUserId");

    public final StringPath chatId = createString("chatId");

    public final StringPath content = createString("content");

    public final BooleanPath isRead = createBoolean("isRead");

    public final DateTimePath<Instant> sentAt = createDateTime("sentAt", Instant.class);

    public QTextMessage(String variable) {
        super(TextMessage.class, forVariable(variable));
    }

    public QTextMessage(Path<? extends TextMessage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTextMessage(PathMetadata metadata) {
        super(TextMessage.class, metadata);
    }

}
