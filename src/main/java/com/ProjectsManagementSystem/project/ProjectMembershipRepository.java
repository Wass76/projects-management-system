package com.ProjectsManagementSystem.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectMembershipRepository extends JpaRepository<ProjectMembership, Integer> {
    @Query("SELECT pm FROM  project_membership pm WHERE pm.project.id = :projectId AND pm.user.id = :userId")
    public ProjectMembership getProjectMemberships(@Param("projectId") Integer projectId, @Param("userId") Integer userId);

    @Query("SELECT pm FROM project_membership pm WHERE pm.user.id = :userId")
    public List<ProjectMembership> getProjectMembershipsByUserId(@Param("userId") Integer userId);

    public List<ProjectMembership> findByProjectId(Integer projectId);
}
