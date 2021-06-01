package com.pw.tms.domain;

import com.pw.tms.domain.TextMessage;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import java.io.Serial;
import java.time.Instant;

import static com.querydsl.core.types.PathMetadataFactory.*;

public class QTextMessage extends EntityPathBase<TextMessage> {

    @Serial
    private static final long serialVersionUID = -1484827847L;

    public static final QTextMessage lostReport = new QTextMessage("textMessage");

    // TODO: pola TextMessage

    public final StringPath id = createString("id");

    public final StringPath title = createString("title");

    public final StringPath description = createString("description");

    public final StringPath category = createString("category");

    public final DateTimePath<Instant> reportedAt = createDateTime("reportedAt", Instant.class);

    public final DateTimePath<Instant> dateFrom = createDateTime("dateFrom", Instant.class);

    public final DateTimePath<Instant> dateTo = createDateTime("dateTo", Instant.class);

    public final ListPath<String,StringPath> tags = createList("tags", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath userId = createString("userId");

    public final BooleanPath isResolved = createBoolean("isResolved");

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
