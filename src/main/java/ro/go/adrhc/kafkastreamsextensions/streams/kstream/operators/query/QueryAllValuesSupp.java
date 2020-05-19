package ro.go.adrhc.kafkastreamsextensions.streams.kstream.operators.query;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.ValueAndTimestamp;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <V>  the value this processor is receiving from upstream processors
 * @param <VR> the value this processor is returning in a List to downstream processors
 */
public class QueryAllValuesSupp<V, VR> extends AbstractQueryAllSupp<V, List<VR>> {
	public QueryAllValuesSupp(String storeName) {
		super(storeName);
	}

	@Override
	protected List<VR> transform(KeyValueIterator<?, ?> iterator) {
		List<VR> records = new ArrayList<>();
		while (iterator.hasNext()) {
			KeyValue<?, ?> kv = iterator.next();
			records.add(valueFrom((ValueAndTimestamp) kv.value));
		}
		return records;
	}
}
