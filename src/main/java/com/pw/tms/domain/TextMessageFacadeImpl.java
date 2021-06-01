package com.pw.tms.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pw.tms.TextMessageSentProto;
import com.pw.tms.domain.ports.incoming.TextMessageFacade;
import com.pw.tms.domain.ports.outgoing.EventPublisher;

@Service
class TextMessageFacadeImpl implements TextMessageFacade {

    private final EventPublisher eventPublisher;

    @Autowired
    TextMessageFacadeImpl(EventPublisher eventPublisher) {

        this.eventPublisher = eventPublisher;
    }

    @Override
    public TextMessage sendTextMessage(TextMessage message) {

        fireTextMessageSent(message);
        return message;
    }

    private void fireTextMessageSent(TextMessage message) {

        var event = TextMessageSentProto.newBuilder()
            .setMessageId(message.id().raw())
            .setSourceUserId(message.sourceUserId())
            .setTargetUserId(message.targetUserId())
            .setContent(message.content())
            .build();
        eventPublisher.publishProtobufEvent(message.sourceUserId(), event);
    }
}
