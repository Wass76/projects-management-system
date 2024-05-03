package com.ProjectsManagementSystem.bug;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BugRespository extends JpaRepository<Bug, Integer>
{
    @Query("SELECT b FROM Bug b WHERE b.task.id = :taskID")
    public List<Bug> findByTaskID(@Param("taskID") Integer taskID);
}
