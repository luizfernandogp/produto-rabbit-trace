package com.pocms.produtos.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocms.produtos.config.AmqpConfig;
import com.pocms.produtos.event.ProdutoPersistEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ProdutoPersistQueue implements ApplicationListener<ProdutoPersistEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoPersistQueue.class);

    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    public ProdutoPersistQueue(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onApplicationEvent(ProdutoPersistEvent event) {
        try {
            var json = objectMapper.writeValueAsString(event.getProduto());
            rabbitTemplate.convertAndSend(AmqpConfig.QUEUE_PRODUTO, json);
        } catch (JsonProcessingException e) {
            LOGGER.error("Não foi possivel converter o objeto produto para JSON", e);
        }
    }

}
