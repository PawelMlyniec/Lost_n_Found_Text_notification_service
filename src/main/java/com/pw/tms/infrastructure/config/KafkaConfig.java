//package com.pw.tms.infrastructure.config;
//
//import com.google.protobuf.Message;
//import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//
//@Configuration
//@EnableKafka
//public class KafkaConfig {
//
//    private static final String SCHEMA_REGISTRY_CONFIG = "schema.registry.url";
//
//    @Bean
//    public ProducerFactory<String, Message> producerFactory(KafkaProperties kafkaProps) {
//
//        var props = kafkaProps.buildProducerProperties();
////        props.put(SCHEMA_REGISTRY_CONFIG, "localhost:8081");
//
//        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(), new KafkaProtobufSerializer<>());
//    }
//
//    @Bean
//    public KafkaTemplate<String, Message> kafkaTemplate(ProducerFactory<String, Message> producerFactory) {
//
//        return new KafkaTemplate<>(producerFactory);
//    }
//}