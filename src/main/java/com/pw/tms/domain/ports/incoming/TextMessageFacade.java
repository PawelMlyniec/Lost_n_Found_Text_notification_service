package com.pw.tms.domain.ports.incoming;

import com.pw.tms.domain.TextMessage;

public interface TextMessageFacade {

    TextMessage sendTextMessage(final TextMessage textMessage);
}
