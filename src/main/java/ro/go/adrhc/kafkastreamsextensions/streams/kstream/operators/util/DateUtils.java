package ro.go.adrhc.kafkastreamsextensions.streams.kstream.operators.util;

import java.time.*;

public class DateUtils {
	public static long millisecondsOf(LocalDate localDate) {
		return localDate.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000;
	}

	public static long millisecondsOf(LocalDateTime ldt) {
		return ldt.toEpochSecond(ZoneOffset.UTC) * 1000;
	}

	public static LocalDate localDateOf(long milliseconds) {
		return LocalDate.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC);
	}

	public static LocalDateTime localDateTimeOf(long milliseconds) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC);
	}
}
