package com.ProjectsManagementSystem.project;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ProjectsManagementSystem.project.ProjectStatus.NEW;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ResponseEntity<List<Project>> getAllProjects() {

        List<Project> projects = projectRepository.findAll();

        return ResponseEntity.ok(projects);
    }

    public ResponseEntity<Project> getProjectById(Integer id)
    {
//        Optional<Project> project = projectRepository.findById(id);
        Project project = projectRepository.findById(id).orElse(null);
//        if(project.isPresent()){
//            return ResponseEntity.ok(project.get());
//        }
//        return ResponseEntity.notFound().build();

        return ResponseEntity.ok(project);
//        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Project> createProject(ProjectRequest projectRequest) {
        Project project = Project.builder().name(projectRequest.getProjectName())
                        .description(projectRequest.getProjectDescription())
                                .status(NEW).build();
        projectRepository.save(project);
        return ResponseEntity.ok(project);
    }

    public ResponseEntity<Project> updateProject(Integer id, ProjectRequest projectRequest) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()){
            project.get().setName(projectRequest.getProjectName());
            project.get().setDescription(projectRequest.getProjectDescription());
            project.get().setStatus(projectRequest.getProjectStatus());
            projectRepository.save(project.get());
        }
        return ResponseEntity.ok(project.get());
    }

    public ResponseEntity<String> deleteProject(Integer id) {
        Project project = projectRepository.findById(id).orElse(null);
        projectRepository.delete(project);
        return ResponseEntity.ok("Project deleted");
    }
}
