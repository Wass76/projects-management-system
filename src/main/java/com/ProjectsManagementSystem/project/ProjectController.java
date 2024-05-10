package com.ProjectsManagementSystem.project;

import com.ProjectsManagementSystem.exception.ApiRequestException;
import com.ProjectsManagementSystem.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/projects")
@RequiredArgsConstructor
@Tag(name = "Projects")
public class ProjectController {

private final ProjectService projectService;
    private final ProjectRepository projectRepository;


    @Operation(
            description = "This endpoint build to get all projects which is in our system",
            summary =  "Get all projects",
            responses ={
                    @ApiResponse(
                            description = "get all projects done successfully",
                            responseCode = "200"
                    ),

                    @ApiResponse(
                            description = "there is no projects yet",
                            responseCode = "203"
                    ),
                    @ApiResponse(
                            description = "unauthorized",
                            responseCode = "403"
                    )
            }
    )
@GetMapping
//    @PreAuthorize("")
    public ResponseEntity<List<Project>> getAllProjects() {

        return projectService.getAllProjects();
    }

    @Operation(
            description = "This endpoint build to get one project by filtering it by id",
            summary =  "Get one project by id",
            responses ={
                    @ApiResponse(
                            description = "get one project done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "there is no project in this id",
                            responseCode = "203"
                    ),
                    @ApiResponse(
                            description = "unauthorized",
                            responseCode = "403"
                    )
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Integer id) {
//        return ResponseEntity.ok( projectRepository.getProjectById(id));

//        System.out.println("there is a project in controller");
//        Logger logger = Logger.getLogger(ProjectController.class.getName(), getClass().getSimpleName());
        if(id == null || id<=0){
            throw new ApiRequestException("Invalid Id");
//            return ResponseEntity.badRequest().body("Invalid Id");
        }
        return projectService.getProjectById(id);
    }

    @Operation(
            description = "This endpoint build to create new project on our system",
            summary =  "Add new project",
            responses ={
                    @ApiResponse(
                            description = "add new project done successfully",
                            responseCode = "200"
                    ),
            }
    )

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest project , Principal principal ) {
        return projectService.createProject(project,principal);
    }

    @Operation(
            description = "This endpoint build to edit project details which is in our system",
            summary =  "Edit project details",
            responses ={
                    @ApiResponse(
                            description = "get all projects done successfully",
                            responseCode = "200"
                    )
            }
    )

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@RequestBody ProjectRequest project
            , @PathVariable Integer id
    , Principal principal)
    {
        if(id == null || id<=0){
            throw new ApiRequestException("Invalid Id");
//            return ResponseEntity.badRequest().body("Invalid Id");
        }
//        SecurityContext securityContext = SecurityContextHolder.getContext();
        User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        return projectService.updateProject(id, project,principal);
    }

    @Operation(
            description = "This endpoint to make user able to delete some project" +
                            " from the system by enter id of this project ",
            summary = "Delete project by id",
            responses ={
                    @ApiResponse(
                            description = "delete done successfully",
                            responseCode = "200"
                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String > deleteProject(@PathVariable Integer id) {
        if(id == null || id<=0){
            throw new ApiRequestException("Invalid Id");
        }
        return projectService.deleteProject(id);
    }

//
    @PostMapping("/{projectId}/new-user-to-project")
    @Operation(
            description = "This endpoint build to join user to some project",
            summary = "Join user to project",
            responses ={
                    @ApiResponse(
                            description = "Joined done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<String> addUserToProject(@PathVariable Integer projectId) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User user = (User) securityContext.getAuthentication().getPrincipal();
        projectService.addUserToProject(projectId, user.getId());
        return ResponseEntity.ok("success");
    }
//
    @DeleteMapping("{projectId}/deleting-from-project")
    public ResponseEntity<String> deleteUserFromProject(@PathVariable Integer projectId) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User user = (User) securityContext.getAuthentication().getPrincipal();
        projectService.removeUserFromProject(projectId, user.getId());
        return ResponseEntity.ok("success");
    }
//
    @GetMapping("all-user-projects/{userId}")
    public ResponseEntity< List<ProjectMembership>> getProjectByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(projectService.getProjectsByUserId(userId));
    }
}
