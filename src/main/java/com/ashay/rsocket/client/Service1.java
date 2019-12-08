package com.ashay.rsocket.client;

import io.rsocket.RSocketFactory;
import io.rsocket.SocketAcceptor;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class Service1 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        RSocketFactory.connect()
                .frameDecoder(PayloadDecoder.ZERO_COPY)
                .setupPayload(DefaultPayload.create("service-1", "register"))
                .acceptor((SocketAcceptor) (setup, sendingSocket) -> Mono.just(new RequestResponseRSocketAcceptor("service-1")))
                .transport(TcpClientTransport.create(8888))
                .start()
                .flatMapMany(rSocket -> Flux.range(0, 100)
                        .delayElements(Duration.ofSeconds(1))
                        .flatMap(integer -> rSocket.requestResponse(DefaultPayload.create("service-1-" + integer, "service-2"))))
                .doOnNext(payload -> System.out.println(payload.getDataUtf8()))
                .doOnComplete(latch::countDown)
                .subscribe();

        latch.await();
    }
}
