package com.challenge3.msusers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConstants {

    public final String queueName;

    public final String exchange;

    public final String routingkey;

    public final String username;

    public final String password;

    public final String host;

    public final String virtualHost;

    public final Integer replyTimeout;

    public final Integer concurrentConsumers;

    public final Integer maxConcurrentConsumers;

    public final Integer port;

    public RabbitMQConstants(@Value("${rabbitmq.queue}") String queueName,
                             @Value("${rabbitmq.exchange}") String exchange,
                             @Value("${rabbitmq.routingkey}") String routingkey,
                             @Value("${rabbitmq.username}") String username,
                             @Value("${rabbitmq.password}") String password,
                             @Value("${rabbitmq.host}") String host,
                             @Value("${rabbitmq.virtualhost}") String virtualHost,
                             @Value("${rabbitmq.reply.timeout}") Integer replyTimeout,
                             @Value("${rabbitmq.concurrent.consumers}") Integer concurrentConsumers,
                             @Value("${rabbitmq.max.concurrent.consumers}") Integer maxConcurrentConsumers,
                             @Value("${rabbitmq.port}") Integer port) {
        this.queueName = queueName;
        this.exchange = exchange;
        this.routingkey = routingkey;
        this.username = username;
        this.password = password;
        this.host = host;
        this.virtualHost = virtualHost;
        this.replyTimeout = replyTimeout;
        this.concurrentConsumers = concurrentConsumers;
        this.maxConcurrentConsumers = maxConcurrentConsumers;
        this.port = port;
    }
}
