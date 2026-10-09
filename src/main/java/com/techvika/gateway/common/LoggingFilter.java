package com.techvika.gateway.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Configuration
public class LoggingFilter implements GlobalFilter {


    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        log.info("Accept = " + request.getHeaders().getFirst("Accept"));


        return chain.filter(exchange).then(Mono.fromRunnable( () -> {
            ServerHttpResponse response = exchange.getResponse();
            log.info("Post Filters = " + response.getStatusCode());

        } ));
    }
}
