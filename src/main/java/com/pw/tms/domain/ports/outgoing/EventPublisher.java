package com.pw.tms.domain.ports.outgoing;

import com.google.protobuf.Message;

public interface EventPublisher {

    <T extends Message> void publishDomainEvent(String userId, T event);
}
