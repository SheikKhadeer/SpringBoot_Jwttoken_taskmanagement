package com.taskManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskManagement.dto.TaskDto;
import com.taskManagement.service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskController {

	@Autowired
	private TaskService taskService;

	// save task in db
	@PostMapping("/{userid}/tasks")
	public ResponseEntity<TaskDto> saveTask(@PathVariable(name="userid") long userid,@RequestBody TaskDto taskDto) {
		return new ResponseEntity<>(taskService.saveTask(userid,taskDto), HttpStatus.CREATED);
	}
	
	
	//GET ALL TASKS
//	@PreAuthorize(value="ROLE_ADMIN")
	@GetMapping("/{userid}/tasks")
	public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable(name="userid") long userid) {
		return new ResponseEntity<>(taskService.getAllTasks(userid), HttpStatus.OK);
	}
	
	
	//GET individual task
	@GetMapping("/{userid}/tasks/{taskid}")
	public ResponseEntity<TaskDto> getTask(@PathVariable(name="userid") long userid,
			@PathVariable(name="taskid") long taskid) {
		return new ResponseEntity<>(taskService.getTask(userid, taskid), HttpStatus.OK);
	}
	
	
	
	//delete the task
	@DeleteMapping("/{userid}/tasks/{taskid}")
	public ResponseEntity<String> deleteTask(@PathVariable(name="userid") long userid,
			@PathVariable(name="taskid") long taskid) {
		taskService.deleteTask(userid, taskid);
		
		return new ResponseEntity<>("Task deleted succesfully", HttpStatus.OK);
	}
}
