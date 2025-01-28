package com.taskManagement.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.FutureOrPresent;

import com.taskManagement.enums.TaskPriority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TaskDto {
	private Long id;
	private String taskname;
	private String description;
	private String status;
	private TaskPriority priority;
	@FutureOrPresent(message = "Due date must be today or in the future")
	
	private LocalDate dueDate;
	private Long userId;
	private Date createdAt;
	private Date updatedAt;
	
}
