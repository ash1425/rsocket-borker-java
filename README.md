# rsocket-borker-java
Simple Java based RSocket Broker - a minimal conceptual implementation

Broker acts as gateway - where every service registers itself

Once registered, a service can try sending request to broker with destination service metadata.

If desitination service is not registered, broker discards the payloads and prints message.

Once the destination service registers itself with broker, broker starts forwarding the payloads.

## How to run
* Run main method in Broker
* Bring up each service one by one
