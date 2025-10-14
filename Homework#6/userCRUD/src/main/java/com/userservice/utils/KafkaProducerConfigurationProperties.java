/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.userservice.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author nedo
 */
@ConfigurationProperties(prefix = "spring.kafka.producer")
public record KafkaProducerConfigurationProperties(
        String bootstrapServers,
        String acks,
        String createTopic,
        String deleteTopic) {

}
