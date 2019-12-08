package com.ashay.rsocket.broker;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class BrokerSocket extends AbstractRSocket {

    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        RSocket targetServiceSocket = ServiceRegistry.getServiceSocket(payload.getMetadataUtf8());
        if (Objects.isNull(targetServiceSocket)) {
            String errorMsg = payload.getMetadataUtf8() + " is not yet up, discarding the payload";
            System.out.println(errorMsg);
            return Mono.just(DefaultPayload.create(errorMsg));
        }
        return targetServiceSocket.requestResponse(payload);
    }
}
