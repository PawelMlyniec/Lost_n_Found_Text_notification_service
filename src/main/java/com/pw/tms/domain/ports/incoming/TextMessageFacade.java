package com.pw.tms.domain.ports.incoming;

import com.pw.tms.domain.TextMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TextMessageFacade {

    TextMessage sendTextMessage(final TextMessage textMessage);

    Long getUnreadMessagesCountForTargetUserId();

    Page<TextMessage> getAllMessagesBetweenUsers(String secondUserId, Pageable pageable);

    List<TextMessage> getAllChatsForUserId();
}
