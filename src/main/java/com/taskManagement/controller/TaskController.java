package com.taskManagement.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskManagement.dto.TaskDto;
import com.taskManagement.entity.Task;
import com.taskManagement.enums.TaskPriority;
import com.taskManagement.enums.TaskStatus;
import com.taskManagement.exception.ResourceNotFoundException;
import com.taskManagement.exception.TaskNotFound;
import com.taskManagement.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	// save task in db
	@PostMapping("/{userid}")
	public ResponseEntity<TaskDto> saveTask(@PathVariable(name = "userid") long userid,
			@RequestBody @Valid TaskDto taskDto) {
		return new ResponseEntity<>(taskService.saveTask(userid, taskDto), HttpStatus.CREATED);
	}

	// GET ALL TASKS
//	@PreAuthorize(value="ROLE_ADMIN")
	@GetMapping("/user/{userid}")
	public ResponseEntity<List<TaskDto>> getAllTasksByUserId(@PathVariable(name = "userid") Long userid) {
		logger.info("fetching all tasks...");
		return new ResponseEntity<>(taskService.getAllTasksByUserId(userid), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<TaskDto>> getAllTasks() {
		logger.info("fetching all tasks...");
		return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
	}

	// GET individual task
	@GetMapping("/{userid}/tasks/{taskid}")
	public ResponseEntity<TaskDto> getTaskById(@PathVariable(name = "userid") Long userid,
			@PathVariable(name = "taskid") Long taskid) {
		return new ResponseEntity<>(taskService.getTaskById(userid, taskid), HttpStatus.OK);
	}

	@GetMapping("/dueDate/{dueDate}")
	public ResponseEntity<List<TaskDto>> getOverdueTasks(@PathVariable("dueDate") LocalDate date) {

		return new ResponseEntity<>(taskService.getTasksByOverDueDate(date), HttpStatus.OK);
	}

	@GetMapping("/priority/{priority}")
	public ResponseEntity<List<TaskDto>> getTasksByPriority(@PathVariable("priority") TaskPriority priority) {
		return new ResponseEntity<>(taskService.getTasksByPriority(priority), HttpStatus.OK);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<TaskDto>> getTasksByStatus(@PathVariable("status") TaskStatus status) {
		return new ResponseEntity<>(taskService.getTasksByStatus(status), HttpStatus.OK);

	}

	// delete the task
	@DeleteMapping("/{userid}/tasks/{taskid}")
	public ResponseEntity<String> deleteTask(@PathVariable(name = "userid") long userid,
			@PathVariable(name = "taskid") long taskid) {
		logger.info("Attempting to delete task with ID {} ", taskid);
		taskService.deleteTask(userid, taskid);

		return new ResponseEntity<>("Task deleted succesfully", HttpStatus.OK);
	}
}
