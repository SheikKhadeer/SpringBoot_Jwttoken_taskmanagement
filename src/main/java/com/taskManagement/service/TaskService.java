package com.taskManagement.service;

import java.time.LocalDate;
import java.util.List;

import com.taskManagement.dto.TaskDto;
import com.taskManagement.entity.Task;
import com.taskManagement.enums.TaskPriority;
import com.taskManagement.enums.TaskStatus;

public interface TaskService {

	public TaskDto saveTask(Long userid, TaskDto taskDto);

	public List<TaskDto> getAllTasks();

	public TaskDto getTaskById(Long userId, Long taskId);

	public void deleteTask(Long userId, Long taskId);

	List<TaskDto> getAllTasksByUserId(Long userId);
	
	
	public List<TaskDto> getTasksByPriority(TaskPriority priority);

	Task dtoToEntity(TaskDto taskDto);

	TaskDto entityToDto(Task task);

	List<TaskDto> getTasksByOverDueDate(LocalDate dueDate);

	List<TaskDto> getTasksByStatus(TaskStatus status);
	
	
}
