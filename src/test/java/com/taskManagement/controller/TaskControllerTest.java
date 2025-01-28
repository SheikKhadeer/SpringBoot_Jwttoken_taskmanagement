//package com.taskManagement.controller;
//
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import com.taskManagement.dto.TaskDto;
//import com.taskManagement.serviceImpl.TaskServiceImpl;
//
//@ExtendWith(MockitoExtension.class)
//public class TaskControllerTest {
//	@Mock
//	private TaskServiceImpl taskServiceImpl;
//	@InjectMocks
//	private TaskController taskController;
//
//	@Test
//	void saveTask_ShouldReturnCreatedResponse_whenTaskSaved() {
//		Long userId = 1l;
//		TaskDto taskDto = new TaskDto(null, "Test task");
//		TaskDto savedTaskDto = new TaskDto(1L, "Test task");
//
//		when(taskServiceImpl.saveTask(userId, taskDto)).thenReturn(savedTaskDto);
//		ResponseEntity<TaskDto> response = taskController.saveTask(userId, taskDto);
//
////		assertEquals(HttpStatus.CREATED, response.getStatusCode());
////		assertNotNull(response.getBody());
////		assertEquals(1l, response.getBody().getId());
////		assertEquals("Test task", response.getBody().getTaskname());
//
//
//		verify(taskServiceImpl, times(1)).saveTask(userId, taskDto);
//
//	}
//
//	@Test
//	void getAllTasks_ShouldReturnTaskList_whenTasksExists() {
//		Long userId = 1l;
//		List<TaskDto> taskDtos = List.of(new TaskDto(1l, "task 1"), new TaskDto(2l, "task 2"));
//
//		when(taskServiceImpl.getAllTasks(userId)).thenReturn(taskDtos);
//		ResponseEntity<List<TaskDto>> response = taskController.getAllTasks(userId);
//
////		assertEquals(HttpStatus.OK, response.getStatusCode());
////		assertEquals(2, response.getBody().size());
////		assertEquals("task 1",response.getBody().get(0).getTaskname());
//
//		verify(taskServiceImpl, times(1)).getAllTasks(userId);
//
//	}
//
//	@Test
//	void getAllTasks_ShouldReturnTask_whenValidUserIdAndTaskIdProvided() {
//		Long userId = 1l;
//		Long taskId = 1l;
//		TaskDto taskDto = new TaskDto(userId, "task 1");
//
//		when(taskServiceImpl.getTask(userId, taskId)).thenReturn(taskDto);
//		ResponseEntity<TaskDto> response = taskController.getTask(userId, taskId);
//
////		assertEquals(HttpStatus.OK, response.getStatusCode());
////		assertEquals("task 1", response.getBody().getTaskname());
//
//		verify(taskServiceImpl, times(1)).getTask(userId, taskId);
//
//	}
//
//	@Test
//	void deleteTasks_ShouldReturnSuccessMessage_whenTaskDeleted() {
//		Long userId = 1l;
//		Long taskId = 1l;
//
//		doNothing().when(taskServiceImpl).deleteTask(userId, taskId);
//		ResponseEntity<String> response = taskController.deleteTask(userId, taskId);
//
////		assertEquals(HttpStatus.OK, response.getStatusCode());
////		assertEquals("task deleted successfully", response.getBody());
//
//		verify(taskServiceImpl, times(1)).deleteTask(userId, taskId);
//
//	}
//}