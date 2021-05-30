package com.pw.tms.infrastructure.adapters.kafka;

import com.google.protobuf.Message;
import com.pw.tms.domain.ports.outgoing.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class KafkaEventPublisher implements EventPublisher {

    private static final String OUTPUT_TOPIC = "tms-text-messages-proto";

    private final KafkaTemplate<String, Message> protobufKafka;
    private final KafkaTemplate<String, Object> jsonKafka;

    @Autowired
    KafkaEventPublisher(KafkaTemplate<String, Message> protobufKafka, KafkaTemplate<String, Object> jsonKafka) {

        this.protobufKafka = protobufKafka;
        this.jsonKafka = jsonKafka;
    }

    @Override
    public <T extends Message> void publishProtobufEvent(String userId, T event) {

        protobufKafka.send(OUTPUT_TOPIC, userId, event)
            .addCallback(
                success -> log.debug("Event {} successfully sent to kafka", event),
                e -> log.error("Exception during sending event {} to kafka", event, e));
    }

    @Override
    public void publishJsonEvent(String userId, Object event) {
        jsonKafka.send(OUTPUT_TOPIC, userId, event)
            .addCallback(
                success -> log.debug("Event {} successfully sent to kafka", event),
                e -> log.error("Exception during sending event {} to kafka", event, e));
    }
}
