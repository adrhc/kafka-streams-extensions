# Kafka Streams DSL extensions
```jshelllanguage
// kStreamsBuilder is a "native" Kafka StreamsBuilder  
StreamsBuilderEx streamsBuilder = StreamsBuilderEx.enhance(kStreamsBuilder);
KStream stream = streamsBuilder.stream(...);

/*
 * It queries storeName returning all its values as a List.
 * Ignores the received key and value so it is useful mainly for debugging/reporting purposes
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
