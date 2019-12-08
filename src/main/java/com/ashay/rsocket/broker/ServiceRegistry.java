package com.ashay.rsocket.broker;

import io.rsocket.RSocket;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {
    static Map<String, RSocket> services = new HashMap<>();

    static void addService(String serviceName, RSocket serviceSocket) {
        System.out.println("Registering - " + serviceName);
        services.put(serviceName, serviceSocket);
    }

    static RSocket getServiceSocket(String serviceName) {
        return services.get(serviceName);
    }
}
