package com.taskManagement.converter;

import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
	private final StringToPriorityConverter stringToPriorityConverter;
	private final StringToStatusConverter stringToStatusConverter;
	private final StringToLocalDateConverter stringToLocalDateConverter;

	public WebConfig(StringToPriorityConverter stringToPriorityConverter,
			StringToStatusConverter stringToStatusConverter, StringToLocalDateConverter stringToLocalDateConverter) {
		super();
		this.stringToPriorityConverter = stringToPriorityConverter;
		this.stringToStatusConverter = stringToStatusConverter;
		this.stringToLocalDateConverter = stringToLocalDateConverter;
	}

	@Override
	public void addFormatters(FormatterRegistry formatterRegistry) {
		formatterRegistry.addConverter(stringToLocalDateConverter);
		formatterRegistry.addConverter(stringToStatusConverter);
		formatterRegistry.addConverter(stringToPriorityConverter);
	}

}
