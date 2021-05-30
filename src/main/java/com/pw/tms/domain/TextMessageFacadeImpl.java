package com.pw.tms.domain;

import com.pw.tms.TextMessageSentProto;
import com.pw.tms.domain.ports.incoming.TextMessageFacade;
import com.pw.tms.domain.ports.outgoing.EventPublisher;
import com.pw.tms.domain.ports.outgoing.TextMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class TextMessageFacadeImpl implements TextMessageFacade {

    private final TextMessageRepository textMessageRepository;
    private final EventPublisher eventPublisher;

    @Autowired
    TextMessageFacadeImpl(TextMessageRepository textMessageRepository, EventPublisher eventPublisher) {

        this.textMessageRepository = textMessageRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public TextMessage sendTextMessage(TextMessage message) {

        var persistedMessage = textMessageRepository.save(message);
        fireTextMessageSent(persistedMessage);
        return persistedMessage;
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
