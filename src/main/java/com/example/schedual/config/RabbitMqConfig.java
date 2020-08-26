package com.example.schedual.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * @Author LINVI7
 * @Date 8/26/2020 8:20 AM
 * @Version 1.0
 */
@Configurable
public class RabbitMqConfig {
    @Bean
    public Queue Queue() {
        return new Queue("Queue", true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("DirectExchange", true, false);
    }

    //    绑定交换机和队列
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(Queue()).to(directExchange()).with("DirectRouting");
    }

    @Bean
    public DirectExchange loneDirectExchange() {
        return new DirectExchange("LoneDirectChange");
    }
}
