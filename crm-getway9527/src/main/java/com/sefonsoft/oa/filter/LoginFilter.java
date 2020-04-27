package com.sefonsoft.oa.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @ClassName: LoginFilter
 * @author: Peng YiWen
 * @date: 2020/4/27  17:21
 */
@Component
@Slf4j
public class LoginFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI uri = exchange.getRequest().getURI();
        log.info(uri.toString());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
