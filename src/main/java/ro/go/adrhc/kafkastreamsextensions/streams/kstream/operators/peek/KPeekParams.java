package ro.go.adrhc.kafkastreamsextensions.streams.kstream.operators.peek;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KPeekParams<K, V> {
	public final K key;
	public final V value;
	public final KPeekContext context;
}
