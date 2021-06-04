package com.pw.tms.domain;

import com.pw.tms.domain.textmessage.TextMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TextMessageFacade {

    TextMessage sendTextMessage(final TextMessage textMessage);

    Long getUnreadMessagesCountForTargetUserId(String targetUserId);

    Page<TextMessage> getAllMessagesBetweenUsers(String firstUserId, String secondUserId, Pageable pageable);

    List<TextMessage> getAllChatsForUserId(String userId);
}
