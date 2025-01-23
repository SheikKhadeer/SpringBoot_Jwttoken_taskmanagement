package com.taskManagement.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public TaskDto saveTask(Long userid, TaskDto taskDto) {
		logger.info("Saving task for user ID {}", userid);
		Users user = usersRepository.findById(userid)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userid)));
		Task task = modelMapper.map(taskDto, Task.class);
		task.setUsers(user);

		logger.debug("Mapped taskDto to task entity for user ID {}", userid);
		// after setting the user we are storing the data into db
		Task savedtask = taskRepository.save(task);
		logger.info("Task saved Sucessfully for user ID {}", userid);
		return modelMapper.map(savedtask, TaskDto.class);
	}

	@Override
	public List<TaskDto> getAllTasks(Long userId) {
		logger.info("Entering getAllTasks() in TaskServiceImpl");
		usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userId)));
		List<Task> tasks = taskRepository.findAllByUsersId(userId);
		logger.debug("fetched {} users form the database ", tasks.size());

		return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
	}

	@Override
	public TaskDto getTask(Long userId, Long taskId) {
		Users user = usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userId)));
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFound(String.format("task id %d not found", taskId)));

		if (user.getId() != task.getUsers().getId()) {
			throw new ApiException(String.format("Task id %d is not belongs to user id", taskId));
		}
		return modelMapper.map(task, TaskDto.class);
	}

	@Override
	public void deleteTask(Long userId, Long taskId) {
		logger.info("Entering deleteTask() in TaskServiceImpl");
		Users user = usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userId)));

		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFound(String.format("task id %d not found", taskId)));
		logger.debug("Checking if user ID {} has any tasks ", userId);

		if (user.getId() != task.getUsers().getId()) {
			throw new ApiException(String.format("Task id %d is not belongs to user id", taskId));
		}
		logger.info("Deleted task ID {} Successfully", taskId);

		taskRepository.deleteById(taskId);// delete the task

	}

}
