package com.taskManagement.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.taskManagement.dto.TaskDto;
import com.taskManagement.entity.Task;
import com.taskManagement.entity.Users;
import com.taskManagement.repository.TaskRepository;
import com.taskManagement.repository.UsersRepository;
import com.taskManagement.serviceImpl.TaskServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private UsersRepository usersRepository;

	@Mock
	private TaskRepository taskRepository;

	@InjectMocks
	private TaskServiceImpl taskServiceImpl;

	@Test
	void saveTask_shouldSaveTask_whenValidUserIdAndTaskDtoProvided() {
		Long userId = 1l;
		TaskDto taskDto = new TaskDto(null, "test task");
		Users user = new Users();
		user.setId(userId);
		Task task = new Task();
		Task SavedTask = new Task();
		SavedTask.setId(1L);

		TaskDto savedTaskDto = new TaskDto(null, "test task");

		when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
		when(modelMapper.map(taskDto, Task.class)).thenReturn(task);
		when(taskRepository.save(task)).thenReturn(SavedTask);
		when(modelMapper.map(SavedTask, TaskDto.class)).thenReturn(savedTaskDto);

		TaskDto result = taskServiceImpl.saveTask(userId, taskDto);
//		assertNotNull(result);
//		assertEquals(1L, result.getId());
//		assertEquals("test task", result.getTaskname());

		verify(usersRepository, times(1)).findById(userId);
		verify(taskRepository, times(1)).save(task);
	}

	@Test
	void getAllTask_shouldReturnListOfTasks_whenValidUserIdProvided() {
		Long userId = 1l;
		Users user = new Users();
		user.setId(userId);

		Task task1 = new Task();
		Task task2 = new Task();

		List<Task> tasks = List.of(task1, task2);

		TaskDto taskDto1 = new TaskDto(1l, "task 1");

		TaskDto taskDto2 = new TaskDto(2l, "task 2");

		when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
		when(taskRepository.findAllByUsersId(userId)).thenReturn(tasks);
		when(modelMapper.map(task1, TaskDto.class)).thenReturn(taskDto1);
		when(modelMapper.map(task2, TaskDto.class)).thenReturn(taskDto2);

		List<TaskDto> result = taskServiceImpl.getAllTasks(userId);

//		assertEquals(2, result.size());
//		assertEquals("task 1", result.get(0).getTaskname());
//		assertEquals("task 2", result.get(1).getTaskname());

		verify(usersRepository, times(1)).findById(userId);
		verify(taskRepository, times(1)).findAllByUsersId(userId);
	}

	@Test
	void deleteTask_shouldDeleteTask_whenValidUserIdAndTaskIdProvided() {
		Long userId = 1l;
		Long taskId = 1l;
		Users user = new Users();
		user.setId(userId);

		Task task = new Task();
		task.setId(userId);
		task.setUsers(user);

		when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
		when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

		taskServiceImpl.deleteTask(userId, taskId);

		verify(taskRepository, times(1)).deleteById(taskId);
	}

}
