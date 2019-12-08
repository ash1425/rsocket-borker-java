package com.ashay.rsocket.broker;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.server.TcpServerTransport;
import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;

public class Broker {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        RSocketFactory.receive()
                .frameDecoder(PayloadDecoder.ZERO_COPY)
                .acceptor(new SocketAcceptor())
                .transport(TcpServerTransport.create(8888))
                .start()
                .block()
                .onClose()
                .doOnSuccess(aVoid -> latch.countDown());

        latch.await();
    }
}

class SocketAcceptor implements io.rsocket.SocketAcceptor {

    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {
        if (setup.getMetadataUtf8().equals("register")) {
            ServiceRegistry.addService(setup.getDataUtf8(), sendingSocket);
        }

        return Mono.just(new BrokerSocket());
    }
}
