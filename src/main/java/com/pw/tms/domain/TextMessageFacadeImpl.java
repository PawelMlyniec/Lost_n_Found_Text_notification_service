package com.pw.tms.domain;

import com.pw.tms.TextMessageSentProto;
import com.pw.tms.domain.ports.incoming.TextMessageFacade;
import com.pw.tms.domain.ports.outgoing.EventPublisher;
import com.pw.tms.domain.ports.outgoing.TextMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

        var persistedMessage = textMessageRepository.save(message.withIsRead(false).withSentAt(Instant.now()));
        fireTextMessageSent(persistedMessage);
        return persistedMessage;
    }

    @Override
    public List<TextMessage> getAllMessagesForTargetUserId(TextMessageId id) {

        var allMessagesForTargetUserIdList = new ArrayList<TextMessage>();
        var allMessagesList = textMessageRepository.findAll();
        // TODO: nie wyciagac wszystkich, tylko te ktore potrzebne (JPA)

        for(TextMessage m : allMessagesList) {
            if(TextMessageId.of(m.targetUserId()).equals(id)) {
                allMessagesForTargetUserIdList.add(m);
            }
        }

        return allMessagesForTargetUserIdList;
    }

    @Override
    public List<TextMessage> getAllMessagesForUsersIds(TextMessageId sourceUserId, TextMessageId targetUserId) {

        var allMessagesForUsersIdsList = new ArrayList<TextMessage>();
        var allMessagesList = textMessageRepository.findAll();
        // TODO: nie wyciagac wszystkich, tylko te ktore potrzebne (JPA)

        for(TextMessage m : allMessagesList) {
            if(TextMessageId.of(m.sourceUserId()).equals(sourceUserId) && TextMessageId.of(m.targetUserId()).equals(targetUserId)) {
                allMessagesForUsersIdsList.add(m);
                m.isRead(true);
                textMessageRepository.save(m);
            }
        }

        return allMessagesForUsersIdsList;
    }

    private void fireTextMessageSent(TextMessage message) {

        var event = TextMessageSentProto.newBuilder()
            .setMessageId(message.id().raw())
            .setSourceUserId(message.sourceUserId())
            .setTargetUserId(message.targetUserId())
            .setContent(message.content())
            .setIsRead(message.isRead())
            .setSentAt(message.sentAt().toEpochMilli())
            .build();
        eventPublisher.publishDomainEvent(message.sourceUserId(), event);
    }
}
