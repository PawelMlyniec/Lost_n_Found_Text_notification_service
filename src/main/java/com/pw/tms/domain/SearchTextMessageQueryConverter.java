package com.pw.tms.domain;

import com.querydsl.core.BooleanBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class SearchTextMessageQueryConverter implements Converter<SearchTextMessageQuery, BooleanBuilder> {

    private final QTextMessage textMessage;

    @Autowired
    public SearchTextMessageQueryConverter() {
        this.textMessage = QTextMessage.textMessage;
    }

    @Override
    public BooleanBuilder convert(@NotNull SearchTextMessageQuery query) {

        var booleanBuilder = new BooleanBuilder();
        addUsersIdsPredicate(query, booleanBuilder);
        addContentPredicate(query, booleanBuilder);
        addIsReadPredicate(query, booleanBuilder);
        addSentAtPredicate(query, booleanBuilder);
        addTargetUserPredicate(query, booleanBuilder);
        return booleanBuilder;
    }

    private void addUsersIdsPredicate(SearchTextMessageQuery query, BooleanBuilder builder) {

        if(query.firstUserId() != null) {
            builder.and(textMessage.targetUserId.eq(query.firstUserId()).or(textMessage.sourceUserId.eq(query.firstUserId())));
        }
        if(query.secondUserId() != null) {
            builder.and(textMessage.targetUserId.eq(query.secondUserId()).or(textMessage.sourceUserId.eq(query.secondUserId())));
        }
    }

    private void addContentPredicate(SearchTextMessageQuery query, BooleanBuilder builder) {

        if(query.content() != null) {
            builder.and(textMessage.content.containsIgnoreCase(query.content()));
        }
    }

    private void addIsReadPredicate(SearchTextMessageQuery query, BooleanBuilder builder) {

        if(query.isRead() != null) {
            builder.and(textMessage.isRead.eq(query.isRead()));
        }
    }

    private void addSentAtPredicate(SearchTextMessageQuery query, BooleanBuilder builder) {

        if(query.sentAt() != null) {
            builder.and(textMessage.sentAt.eq(query.sentAt()));
        }
    }

    private void addTargetUserPredicate(SearchTextMessageQuery query, BooleanBuilder builder) {

        if(query.targetUserId() != null) {
            builder.and(textMessage.targetUserId.eq(query.targetUserId()));
        }
    }

}
