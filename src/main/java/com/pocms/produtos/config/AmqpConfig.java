package com.pocms.produtos.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AmqpConfig {

    public static final String QUEUE_PRODUTO = "queue.produto.persist";

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_PRODUTO)
                .build();
    }

}
