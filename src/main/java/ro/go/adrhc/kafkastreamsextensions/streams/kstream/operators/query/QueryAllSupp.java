package ro.go.adrhc.kafkastreamsextensions.streams.kstream.operators.query;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.ValueAndTimestamp;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <V>  the value this processor is receiving from upstream processors
 * @param <KR> the key this processor is returning in a List of KeyValue to downstream processors
 * @param <VR> the value this processor is returning in a List of KeyValue to downstream processors
 */
public class QueryAllSupp<V, KR, VR> extends AbstractQueryAllSupp<V, List<KeyValue<KR, VR>>> {
	public QueryAllSupp(String storeName) {
		super(storeName);
	}

	@Override
	protected List<KeyValue<KR, VR>> transform(KeyValueIterator<?, ?> iterator) {
		List<KeyValue<KR, VR>> records = new ArrayList<>();
		while (iterator.hasNext()) {
			KeyValue<?, ?> kv = iterator.next();
			records.add(KeyValue.pair((KR) kv.key,
					valueFrom((ValueAndTimestamp) kv.value)));
		}
		return records;
	}
}
