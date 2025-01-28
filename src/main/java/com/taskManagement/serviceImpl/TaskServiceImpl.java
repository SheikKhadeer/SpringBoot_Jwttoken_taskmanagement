package com.taskManagement.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.taskManagement.dto.TaskDto;
import com.taskManagement.entity.Task;
import com.taskManagement.entity.Users;
import com.taskManagement.enums.TaskPriority;
import com.taskManagement.enums.TaskStatus;
import com.taskManagement.exception.ApiException;
import com.taskManagement.exception.ResourceNotFoundException;
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
		logger.info("creating new task with name : {}", taskDto.getTaskname());

		if (taskDto.getDueDate() == null)
			throw new IllegalArgumentException("Password should not be null");

		Users user = usersRepository.findById(userid)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userid)));

		Task taskEntity = dtoToEntity(taskDto);
		taskEntity.setUsers(user);

		logger.debug("Mapped taskDto to task entity for user ID {}", userid);
		// after setting the user we are storing the data into db
		Task savedtask = taskRepository.save(taskEntity);
		logger.info("Task saved Sucessfully for user ID {}", userid);
		return entityToDto(savedtask);
	}

	public List<TaskDto> getAllTasks() {
		logger.info("Entering getAllTasks() in taskServiceImpl");
		List<Task> tasks = taskRepository.findAll();
		logger.debug("fetched {} tasks form the database ", tasks.size());
		return tasks.stream().map(task -> entityToDto(task)).collect(Collectors.toList());
	}

	@Override
	public List<TaskDto> getAllTasksByUserId(Long userId) {
		logger.info("Entering getAllTasks() in TaskServiceImpl");
		usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userId)));
		List<Task> tasks = taskRepository.findAllByUsersId(userId);
		logger.debug("fetched {} users form the database ", tasks.size());

		return tasks.stream().map(task -> entityToDto(task)).collect(Collectors.toList());
	}

	@Cacheable("tasks_by_user")
	@Override
	public TaskDto getTaskById(Long userId, Long taskId) {
		Users user = usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("user id %d not found", userId)));
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFound(String.format("task id %d not found", taskId)));

		if (user.getId() != task.getUsers().getId()) {
			throw new ApiException(String.format("Task id %d is not belongs to user id", taskId));
		}
		return entityToDto(task);
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

	@Override
	public List<TaskDto> getTasksByOverDueDate(LocalDate dueDate) {// TODO Auto-generated method stub
		logger.info("Fetching dueDate tasks:{}", dueDate);
		List<Task> tasks = taskRepository.findByDueDate(dueDate);
		if (tasks.isEmpty())
			throw new ResourceNotFoundException(String.format("No tasks found With duedate :  %s", dueDate));

		return tasks.stream().map(this::entityToDto).collect(Collectors.toList());
	}

	@Override
	public List<TaskDto> getTasksByPriority(TaskPriority priority) {
		logger.info("Fetching tasks with priority :{}", priority);

		List<Task> tasks = taskRepository.findByPriority(priority);
		if (tasks.isEmpty())
			throw new ResourceNotFoundException(String.format("No tasks found With priority :  %s", priority));

		return tasks.stream().map(this::entityToDto).collect(Collectors.toList());
	}

	@Override
	public List<TaskDto> getTasksByStatus(TaskStatus status) {
		logger.info("Fetching tasks with status :{}", status);
		
		List<Task> tasks = taskRepository.findByStatus(status);
		System.out.println(tasks);
		if (tasks.isEmpty())
			throw new ResourceNotFoundException(String.format("No tasks found With status :  %s", status));

		return tasks.stream().map(this::entityToDto).collect(Collectors.toList());
	}

	@Override
	public Task dtoToEntity(TaskDto taskDto) {
		return modelMapper.map(taskDto, Task.class);
	}

	@Override
	public TaskDto entityToDto(Task task) {
		TaskDto taskDto = modelMapper.map(task, TaskDto.class);
		taskDto.setCreatedAt(task.getCreatedAt());
		taskDto.setUpdatedAt(task.getUpdatedAt());
		if (task.getUsers() != null)
			taskDto.setUserId(task.getUsers().getId());
		return taskDto;
	}
}
