package com.pw.tms.domain;

import com.pw.tms.TextMessageSentProto;
import com.pw.tms.domain.ports.incoming.TextMessageFacade;
import com.pw.tms.domain.ports.outgoing.EventPublisher;
import com.pw.tms.domain.ports.outgoing.TextMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
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
    public Long getUnreadMessagesCountForTargetUserId(String targetUserId) {
        //return textMessageRepository.countByIsReadAndTargetUserId(false, targetUserId);
        return null;
    }

    @Override
    public List<TextMessage> getAllMessagesBetweenUsers(String firstUserId, String secondUserId) {

//        var textMessagesList = textMessageRepository.findBySourceUserIdAndTargetUserId(firstUserId, secondUserId);
//        textMessagesList.addAll(textMessageRepository.findBySourceUserIdAndTargetUserId(secondUserId, firstUserId));
//
//        // TODO: sortowanie listy wiadomosci po timestampie??
//
//        for(TextMessage m : textMessagesList) {
//            if(!m.isRead()) {
//                m.isRead(true);
//                textMessageRepository.save(m);
//            }
//        }
//
//        return textMessagesList;
        return null;
    }

    @Override
    public List<TextMessage> getAllChatsForUserId(String userId) {

//        var allTextMessagesList = textMessageRepository.findByTargetUserId(userId);
//        allTextMessagesList.addAll(textMessageRepository.findBySourceUserId(userId));
//
//        var otherUsersIdToMostRecentMessageMap = new HashMap<String, TextMessage>();
//
//        for(TextMessage m : allTextMessagesList) {
//            var otherUsersId = m.targetUserId().equals(userId) ? m.sourceUserId() : m.targetUserId();
//            if(!otherUsersIdToMostRecentMessageMap.containsKey(otherUsersId)) {
//                otherUsersIdToMostRecentMessageMap.put(otherUsersId, m);
//            }
//            else if(m.sentAt().isAfter(otherUsersIdToMostRecentMessageMap.get(otherUsersId).sentAt())) {
//                otherUsersIdToMostRecentMessageMap.replace(otherUsersId, m);
//            }
//        }
//
//        return (List<TextMessage>) otherUsersIdToMostRecentMessageMap.values();
        return null;
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
