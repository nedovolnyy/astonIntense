/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.notificationservice.consumer;

import com.notificationservice.dto.MessageDto;
import com.notificationservice.utils.KafkaConsumerConfigurationProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

/**
 *
 * @author nedo
 */
@Configuration
@EnableConfigurationProperties(KafkaConsumerConfigurationProperties.class)
@RequiredArgsConstructor
public class KafkaConsumerConfiguration {
    
    final KafkaConsumerConfigurationProperties kafkaConfigurationProperties;

    @Bean
    ConsumerFactory<String, MessageDto> messageConsumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigurationProperties.bootstrapServers());
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfigurationProperties.groupId());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConfigurationProperties.autoOffsetReset());
        configProps.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "*");
        configProps.put(JacksonJsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        configProps.put(JacksonJsonDeserializer.VALUE_DEFAULT_TYPE, MessageDto.class.getName());
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JacksonJsonDeserializer<>(MessageDto.class));
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, MessageDto> messageDtoKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, MessageDto>();
        factory.setConsumerFactory(messageConsumerFactory());
        return factory;
    }
}
