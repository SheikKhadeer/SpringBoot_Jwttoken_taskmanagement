package com.taskManagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taskManagement.entity.Task;
import com.taskManagement.enums.TaskPriority;
import com.taskManagement.enums.TaskStatus;

@Repository
@EnableCaching
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllByUsersId(Long userId);

	@Cacheable("tasks")
	List<Task> findAll();



	@Cacheable("tasks_by_priority")
	List<Task> findByPriority(TaskPriority priority);
	
	@Cacheable("tasks_by_dueDate")
	List<Task> findByDueDate(LocalDate date);
	
	@Cacheable("tasks_by_status")
	List<Task> findByStatus(TaskStatus status);

	@Cacheable("overdue_tasks")
	@Query("select t from Task t where t.dueDate < :currentDate and t.status<> 'completed'")
	List<Task> findOverdueTasks(@Param("currentDate") LocalDate currentDate);

}
