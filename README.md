# Kafka Streams DSL extensions
This library provides some wrappers over kafka streams classes containing additional KStreams operators (e.g. `allOf`).
### KStreams operators
```jshelllanguage
// kStreamsBuilder is a "native" Kafka StreamsBuilder  
StreamsBuilderEx streamsBuilder = StreamsBuilderEx.from(kStreamsBuilder);
KStream stream = streamsBuilder.stream(...);

/*
 * It queries storeName returning all its values as a List.
 * It ignores the received value so it is useful mainly for debugging/reporting purposes
*/
stream.allValuesOf(String storeName);

/*
 * It queries storeName returning all its records as a List of KeyValue.
 * It ignores the received value so it is useful mainly for debugging/reporting purposes
*/
stream.allOf(String storeName);

// any TemporalUnit based windowing
stream
    .windowedBy(int windowSize, TemporalUnit unit)
    .aggregate(...)

/*
 * similar to KStream.peek() but also allows partially access to ProcessorContext
 */
stream.peek(Consumer<KPeekParams<K, V>> consumer)
```
### Versioning
The versions will follow the `org.apache.kafka:kafka-streams` versions.
