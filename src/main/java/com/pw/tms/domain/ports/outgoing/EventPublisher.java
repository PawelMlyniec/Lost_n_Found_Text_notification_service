package com.pw.tms.domain.ports.outgoing;

import com.google.protobuf.Message;

public interface EventPublisher {

    <T extends Message> void publishProtobufEvent(String userId, T event);

    void publishJsonEvent(String userId, Object event);
}
