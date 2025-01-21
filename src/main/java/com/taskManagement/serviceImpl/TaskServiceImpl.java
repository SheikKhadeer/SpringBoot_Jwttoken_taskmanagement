package com.taskManagement.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskManagement.dto.TaskDto;
import com.taskManagement.entity.Task;
import com.taskManagement.entity.Users;
import com.taskManagement.exception.ApiException;
import com.taskManagement.exception.TaskNotFound;
import com.taskManagement.exception.UserNotFound;
import com.taskManagement.repository.TaskRepository;
import com.taskManagement.repository.UsersRepository;
import com.taskManagement.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public TaskDto saveTask(Long userid, TaskDto taskDto) {
		Users user = usersRepository.findById(userid)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userid)));
		Task task = modelMapper.map(taskDto, Task.class);
		task.setUsers(user);
		// after setting the user we are storing the data into db
		Task savedtask = taskRepository.save(task);
		return modelMapper.map(savedtask, TaskDto.class);
	}

	@Override
	public List<TaskDto> getAllTasks(Long userId) {
		usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userId)));
		List<Task> tasks = taskRepository.findAllByUsersId(userId);
		return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
	}

	@Override
	public TaskDto getTask(Long userId, Long taskId) {
		Users user = usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userId)));
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFound(String.format("task id %d not found", taskId)));
		
		if(user.getId()!=task.getUsers().getId()) {
			throw new ApiException(String.format("Task id %d is not belongs to user id", taskId));
		}
		return modelMapper.map(task,TaskDto.class);
	}

	@Override
	public void deleteTask(Long userId, Long taskId) {
		Users user = usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userId)));
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFound(String.format("task id %d not found", taskId)));
		
		if(user.getId()!=task.getUsers().getId()) {
			throw new ApiException(String.format("Task id %d is not belongs to user id", taskId));
		}
		
		taskRepository.deleteById(taskId);//delete the task
		
	}

}
