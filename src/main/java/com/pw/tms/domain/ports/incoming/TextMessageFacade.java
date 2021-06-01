package com.pw.tms.domain.ports.incoming;

import com.pw.tms.domain.TextMessage;
import java.util.List;

public interface TextMessageFacade {

    TextMessage sendTextMessage(final TextMessage textMessage);

    Long getUnreadMessagesCountForTargetUserId(String targetUserId);

    List<TextMessage> getAllMessagesBetweenUsers(String firstUserId, String secondUserId);

    List<TextMessage> getAllChatsForUserId(String userId);
}
