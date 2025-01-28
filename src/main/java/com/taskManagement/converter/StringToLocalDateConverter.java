package com.taskManagement.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

	private final DateTimeFormatter  formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
	@Override
	public LocalDate convert(String source) {
		try {
			return LocalDate.parse(source,formatter);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}
