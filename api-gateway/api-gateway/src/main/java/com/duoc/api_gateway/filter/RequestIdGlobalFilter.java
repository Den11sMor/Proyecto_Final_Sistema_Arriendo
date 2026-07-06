package com.duoc.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Configuration
public class RequestIdGlobalFilter {

    private static final String REQUEST_ID_HEADER = "X-Request-Id";

    @Bean
    public GlobalFilter addRequestIdHeaderFilter() {
        return (exchange, chain) -> {
            String requestId = exchange.getRequest()
                    .getHeaders()
                    .getFirst(REQUEST_ID_HEADER);

            if (requestId == null || requestId.isBlank()) {
                requestId = UUID.randomUUID().toString();
            }

            String finalRequestId = requestId;

            var mutatedRequest = exchange.getRequest()
                    .mutate()
                    .header(REQUEST_ID_HEADER, finalRequestId)
                    .build();

            var mutatedExchange = exchange.mutate()
                    .request(mutatedRequest)
                    .build();

            return chain.filter(mutatedExchange)
                    .then(Mono.fromRunnable(() ->
                            exchange.getResponse().getHeaders().add(REQUEST_ID_HEADER, finalRequestId)
                    ));
        };
    }
}
