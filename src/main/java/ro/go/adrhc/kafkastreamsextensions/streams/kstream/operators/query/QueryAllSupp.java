package ro.go.adrhc.kafkastreamsextensions.streams.kstream.operators.query;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.kstream.ValueTransformerSupplier;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;

import java.util.ArrayList;
import java.util.List;

public class QueryAllSupp<OV, NV> implements ValueTransformerSupplier<OV, List<NV>> {
	private final String storeName;

	public QueryAllSupp(String storeName) {
		this.storeName = storeName;
	}

	private static <T> T valueFrom(ValueAndTimestamp valueAndTimestamp) {
		return (T) valueAndTimestamp.value();
	}

	@Override
	public ValueTransformer<OV, List<NV>> get() {
		return new ValueTransformer<>() {
			private KeyValueStore<String, ?> store;

			@Override
			public void init(ProcessorContext context) {
				store = (KeyValueStore) context.getStateStore(storeName);
			}

			@Override
			public List<NV> transform(OV value) {
				// https://docs.confluent.io/current/streams/faq.html#why-does-my-kstreams-application-use-so-much-memory
				try (KeyValueIterator<String, ?> iterator = store.all()) {
					List<NV> records = new ArrayList<>();
					while (iterator.hasNext()) {
						KeyValue<String, ?> kv = iterator.next();
						records.add(valueFrom((ValueAndTimestamp) kv.value));
					}
					return records;
				}
			}

			@Override
			public void close() {}
		};
	}
}
