package ro.go.adrhc.kafkastreamsextensions.streams.kstream.operators.aggregation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.Windowed;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static ro.go.adrhc.kafkastreamsextensions.streams.kstream.operators.util.DateUtils.localDateOf;

@Getter
@AllArgsConstructor
@Slf4j
@ToString
public class LocalDateBasedKey<T> {
	private static final ThreadLocal<MessageFormat> WINDOW_KEY =
			ThreadLocal.withInitial(() -> new MessageFormat("{0}-{1}"));
	private static final DateTimeFormatter keyLocalDateFormat =
			DateTimeFormatter.ofPattern("yyyy.MM.dd");

	private final T data;
	private final LocalDate time;

	/**
	 * includingEndDate means that is the included end date of a time period
	 *
	 * @return data-includingEndDate
	 */
	public static <T> String keyOf(T data, LocalDate includingEndDate) {
		return data.toString() + '-' + includingEndDate.format(keyLocalDateFormat);
	}

	/**
	 * windowed.key() = e.g. clientId
	 * windowEndingDateMinus1 is an "including" date
	 *
	 * @return windowed.key()-windowEndingDateMinus1
	 */
	public static <T> String keyOf(Windowed<T> windowed) {
		return LocalDateBasedKey.keyOf(windowed.key().toString(),
				localDateOf(windowed.window().end()).minusDays(1));
	}

	public static <T> LocalDateBasedKey<T> convert(Windowed<T> windowed) {
		return new LocalDateBasedKey<>(windowed.key(),
				localDateOf(windowed.window().end()).minusDays(1));
	}

	public static Optional<LocalDateBasedKey<String>> parseWithStringData(String localDateBasedKey) {
		Object[] parts;
		try {
			parts = WINDOW_KEY.get().parse(localDateBasedKey);
			return Optional.of(new LocalDateBasedKey<>((String) parts[0],
					keyLocalDateFormat.parse((String) parts[1], LocalDate::from)));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Optional.empty();
		}
	}
}
