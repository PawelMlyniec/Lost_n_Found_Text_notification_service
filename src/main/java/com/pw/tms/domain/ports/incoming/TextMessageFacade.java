package com.pw.tms.domain.ports.incoming;

import com.pw.tms.domain.TextMessage;
import com.pw.tms.domain.TextMessageId;
import java.util.List;

public interface TextMessageFacade {

    TextMessage sendTextMessage(final TextMessage textMessage);

    List<TextMessage> getAllMessagesForTargetUserId(TextMessageId id);

    List<TextMessage> getAllMessagesForUsersIds(TextMessageId sourceUserId, TextMessageId targetUserId);
}
