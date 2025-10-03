/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.notificationservice.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author nedo
 */
@ConfigurationProperties(prefix = "spring.kafka.consumer")
public record KafkaConsumerConfigurationProperties(
        String bootstrapServers,
        String groupId,
        String autoOffsetReset,
        String listenableTopics,
        String fromEmail,
        String createMessage,
        String deleteMessage) {

}
