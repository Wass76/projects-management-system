package com.ProjectsManagementSystem.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

//@Query("SELECT p FROM Project p WHERE p.id = :id")
//    public Project getProjectById(int id);
}
