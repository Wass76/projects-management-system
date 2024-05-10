package com.ProjectsManagementSystem.project;

import com.ProjectsManagementSystem.exception.ApiRequestException;
import com.ProjectsManagementSystem.user.User;
import com.ProjectsManagementSystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static com.ProjectsManagementSystem.project.ProjectStatus.NEW;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    private final ProjectMembershipRepository projectMembershipRepository;

    public ResponseEntity<List<Project>> getAllProjects() {

        List<Project> projects = projectRepository.findAll();

        return ResponseEntity.ok(projects);
    }

    public ResponseEntity<Project> getProjectById(Integer id)
    {
//        Optional<Project> project = projectRepository.findById(id);
        Project project = projectRepository.findById(id).orElse(null);

//        ProjectMembership projectMembership = projectMembershipRepository.findById(id).orElse(null);
//        if (project != null && projectMembership != null) {
//            System.out.println("there is a project in service");
//        }
        if(project == null){
            throw new ApiRequestException("Project not found");
        }
//        return ResponseEntity.notFound().build();

//        var creatorUser = userRepository.findById(project.getLastModifiedBy());
//        var modifiedUser = userRepository.findById(project.getLastModifiedBy());

//        return ResponseEntity.ok(ProjectResponse
//                .builder()
//                .id(project.getId())
//                .name(project.getName())
//                .description(project.getDescription())
//                .status(String.valueOf(project.getStatus()))
//                .CreatedBy(creatorUser.get().getFirstName())
//                .createDate(project.getCreateDate())
//                .lastModifiedBy(modifiedUser.get().getFirstName())
//                .lastModified(project.getLastModified())
//                .build());
        return ResponseEntity.ok(project);

//        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<ProjectResponse> createProject(ProjectRequest projectRequest,Principal principal) {

        Project project = Project.builder().name(projectRequest.getProjectName())
                        .description(projectRequest.getProjectDescription())
                                .status(NEW).build();
        projectRepository.save(project);
        User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        ProjectMembership projectMembership = ProjectMembership.builder()
                .project(project)
                .user(user)
                .role("OWNER").build();
        projectMembershipRepository.save(projectMembership);
        var modifiedUser = userRepository.findById(project.getLastModifiedBy());

        return ResponseEntity.ok(ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .status(String.valueOf(project.getStatus()))
                .createDate(project.getCreateDate())
                .CreatedBy(user.getFirstName())
                .lastModified(project.getLastModified())
                .lastModifiedBy(modifiedUser.get().getFirstName())
                .build()
        );
    }

    public ResponseEntity<Project> updateProject(Integer id, ProjectRequest projectRequest , Principal principal) {
        Optional<Project> project = projectRepository.findById(id);
//        if(project.isPresent()){
            project.get().setName(projectRequest.getProjectName());
            project.get().setDescription(projectRequest.getProjectDescription());
            project.get().setStatus(projectRequest.getProjectStatus());
            projectRepository.save(project.get());
//        }

//        User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
//        var creatorUser = userRepository.findById(project.get().getLastModifiedBy());

//        return ResponseEntity.ok(ProjectResponse.builder()
//                .id(project.get().getId())
//                .name(project.get().getName())
//                .description(project.get().getDescription())
//                .status(String.valueOf(project.get().getStatus()))
//                .createDate(project.get().getCreateDate())
//                .CreatedBy(creatorUser.get().getFirstName())
//                .lastModified(project.get().getLastModified())
//                .lastModifiedBy(user.getFirstName())
//                .build());
        return ResponseEntity.ok(project.get());
    }

    public ResponseEntity<String> deleteProject(Integer id) {
        Project project = projectRepository.findById(id).orElse(null);
        if(project == null){
            throw new ApiRequestException("Project not found");
        }
        List<ProjectMembership> projectMemberships = projectMembershipRepository.findByProjectId(id);

        projectMembershipRepository.deleteAll(projectMemberships);

        projectRepository.delete(project);
        return ResponseEntity.ok("Project deleted");
    }
//
    public void addUserToProject(Integer id, Integer userId) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()){
            User user = userRepository.getReferenceById(userId);
            ProjectMembership projectMembership = ProjectMembership.builder()
                    .project(project.get())
                    .user(user)
                    .role("Member").build();
            projectMembershipRepository.save(projectMembership);
        }
        else{
            throw new ApiRequestException("there is no project with this id");
        }
    }
//
    public void removeUserFromProject(Integer id, Integer userId) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()){
            User user = userRepository.getReferenceById(userId);
            if(user !=null){
                ProjectMembership projectMembership =  projectMembershipRepository
                        .getProjectMemberships(project.get().getId() , userId);
                projectMembershipRepository.delete(projectMembership);
            }
           }
    }
//
    public List<ProjectMembership> getProjectsByUserId(Integer userId) {
        return projectMembershipRepository.getProjectMembershipsByUserId(userId);
    }

}
