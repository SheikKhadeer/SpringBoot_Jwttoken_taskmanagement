package com.taskManagement.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.taskManagement.enums.TaskStatus;
import com.taskManagement.exception.ResourceNotFoundException;

@Component
public class StringToStatusConverter implements Converter<String, TaskStatus> {

	@Override
	public TaskStatus convert(String source) {
		try {
			return TaskStatus.valueOf(source.toUpperCase());
		} catch (IllegalArgumentException e) {
		throw new ResourceNotFoundException(String.format("No tasks found With status :  %s", source));
		}
	}

}
