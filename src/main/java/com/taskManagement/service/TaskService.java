package com.taskManagement.service;

import java.util.List;

import com.taskManagement.dto.TaskDto;

public interface TaskService {

	public TaskDto saveTask(Long userid, TaskDto taskDto);

	public List<TaskDto> getAllTasks(Long userId);

	public TaskDto getTask(Long userId, Long taskId);

	public void deleteTask(Long userId, Long taskId);
}
