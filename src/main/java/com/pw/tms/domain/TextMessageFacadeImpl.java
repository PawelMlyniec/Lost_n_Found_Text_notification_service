package com.pw.tms.domain;

import com.pw.tms.TextMessageSentProto;
import com.pw.tms.domain.ports.incoming.TextMessageFacade;
import com.pw.tms.domain.ports.outgoing.EventPublisher;
import com.pw.tms.domain.ports.outgoing.TextMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
class TextMessageFacadeImpl implements TextMessageFacade {

    private final TextMessageRepository textMessageRepository;
    private final EventPublisher eventPublisher;
    private final SearchTextMessageQueryConverter searchTextMessageQueryConverter;

    @Override
    public TextMessage sendTextMessage(TextMessage message) {

        var userId = UserOperation.getAuthenticatedUserId();
        var persistedMessage = textMessageRepository.save(
            message
                .withSourceUserId(userId)
                .withIsRead(false)
                .withSentAt(Instant.now())
                .withChatId(createChatId(message)));
        fireTextMessageSent(persistedMessage);
        return persistedMessage;
    }

    @Override
    public Long getUnreadMessagesCountForTargetUserId() {

        var userId = UserOperation.getAuthenticatedUserId();
        var query = SearchTextMessageQuery.builder()
                .targetUserId(userId)
                .isRead(false)
                .build();

        var pageable = PageRequest.of(0, 1);
        var messages = searchTextMessagesPage(query, pageable);

        return messages.getTotalElements();
    }

    @Override
    @Transactional
    public Page<TextMessage> getAllMessagesBetweenUsers(String secondUserId, Pageable pageable) {

        var firstUserId = UserOperation.getAuthenticatedUserId();
        var query = SearchTextMessageQuery.builder()
            .firstUserId(firstUserId)
            .secondUserId(secondUserId)
            .build();

        var messages = searchTextMessagesPage(query, pageable);
        for(TextMessage m : messages) {
            if(m.targetUserId().equals(firstUserId)) {
                m.isRead(true);
                textMessageRepository.save(m);
            }
        }

        return messages;
    }

    @Override
    public List<TextMessage> getAllChatsForUserId() {

        var userId = UserOperation.getAuthenticatedUserId();
        var query = SearchTextMessageQuery.builder()
                .firstUserId(userId)
                .build();

        var predicate = searchTextMessageQueryConverter.convert(query);
        assert predicate != null;
        var allTextMessages = textMessageRepository.findAll(predicate);
        var otherUsersIdToMostRecentMessageMap = new HashMap<String, TextMessage>();

        for(TextMessage m : allTextMessages) {
            var otherUsersId = m.targetUserId().equals(userId) ? m.sourceUserId() : m.targetUserId();
            if(!otherUsersIdToMostRecentMessageMap.containsKey(otherUsersId)) {
                otherUsersIdToMostRecentMessageMap.put(otherUsersId, m);
            }
            else if(m.sentAt().isAfter(otherUsersIdToMostRecentMessageMap.get(otherUsersId).sentAt())) {
                otherUsersIdToMostRecentMessageMap.replace(otherUsersId, m);
            }
        }

        return new ArrayList<>(otherUsersIdToMostRecentMessageMap.values());
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

    private Page<TextMessage> searchTextMessagesPage(SearchTextMessageQuery query, Pageable pageable) {

        var predicate = searchTextMessageQueryConverter.convert(query);
        assert predicate != null;
        return textMessageRepository.findAll(predicate, pageable);
    }

    private String createChatId(TextMessage message) {

        var firstId = message.sourceUserId().compareTo(message.targetUserId()) < 0 ? message.sourceUserId() : message.targetUserId();
        var secondId = message.sourceUserId().compareTo(message.targetUserId()) > 0 ? message.sourceUserId() : message.targetUserId();

        return firstId + "-" + secondId;
    }

}
