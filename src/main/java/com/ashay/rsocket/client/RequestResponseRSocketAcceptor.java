package com.ashay.rsocket.client;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Mono;

public class RequestResponseRSocketAcceptor extends AbstractRSocket {
    private String serviceName;

    public RequestResponseRSocketAcceptor(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        return Mono.just(DefaultPayload.create("Echo from " + serviceName + " : " + payload.getDataUtf8()));
    }
}
