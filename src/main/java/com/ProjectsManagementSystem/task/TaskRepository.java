package com.ProjectsManagementSystem.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t  WHERE t.project.id = :projectId")
    public List<Task> findByProjectId(@Param("projectId") Integer projectId);
}
