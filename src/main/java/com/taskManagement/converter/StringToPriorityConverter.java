package com.taskManagement.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.taskManagement.enums.TaskPriority;

@Component
public class StringToPriorityConverter implements Converter<String, TaskPriority> {

	@Override
	public TaskPriority convert(String source) {
		try {
			return TaskPriority.valueOf(source.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}
