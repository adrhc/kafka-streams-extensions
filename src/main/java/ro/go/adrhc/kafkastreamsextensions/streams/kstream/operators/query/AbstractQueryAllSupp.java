package ro.go.adrhc.kafkastreamsextensions.streams.kstream.operators.query;

import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.kstream.ValueTransformerSupplier;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;

public abstract class AbstractQueryAllSupp<V, VR>
		implements ValueTransformerSupplier<V, VR> {
	protected final String storeName;

	protected AbstractQueryAllSupp(String storeName) {
		this.storeName = storeName;
	}

	protected static <T> T valueFrom(ValueAndTimestamp valueAndTimestamp) {
		return (T) valueAndTimestamp.value();
	}

	abstract protected VR transform(KeyValueIterator<?, ?> iterator);

	@Override
	public ValueTransformer<V, VR> get() {
		return new ValueTransformer<>() {
			private KeyValueStore<?, ?> store;

			@Override
			public void init(ProcessorContext context) {
				store = (KeyValueStore) context.getStateStore(storeName);
			}

			@Override
			public VR transform(V value) {
				// https://docs.confluent.io/current/streams/faq.html#why-does-my-kstreams-application-use-so-much-memory
				try (KeyValueIterator<?, ?> iterator = store.all()) {
					return AbstractQueryAllSupp.this.transform(iterator);
				}
			}

			@Override
			public void close() {}
		};
	}
}
