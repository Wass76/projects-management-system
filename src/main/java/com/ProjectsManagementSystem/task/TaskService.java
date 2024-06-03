package com.ProjectsManagementSystem.task;

import com.ProjectsManagementSystem.bug.BugRespository;
import com.ProjectsManagementSystem.exception.ApiRequestException;
import com.ProjectsManagementSystem.project.Project;
import com.ProjectsManagementSystem.project.ProjectMembership;
import com.ProjectsManagementSystem.project.ProjectMembershipRepository;
import com.ProjectsManagementSystem.project.ProjectRepository;
import com.ProjectsManagementSystem.user.User;
import com.ProjectsManagementSystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ProjectsManagementSystem.task.TaskStatus.NEW;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final BugRespository bugRespository;
    private final ProjectMembershipRepository projectMembershipRepository;

    public ResponseEntity< List<TaskResponse>> getAllTasks() {
//        return  taskRepository.findAll();
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponse> taskResponses = tasks.stream()
                .map(task -> TaskResponse.builder()
                        .taskDescription(task.getDescription())
                        .taskId(task.getId())
                        .taskStatus(task.getStatus())
                        .taskCreateDate(task.getCreateDate())
                        .bugs(task.getBugList())
                        .hasBugs(!bugRespository.findByTaskID(task.getId()).isEmpty())
                        .taskCreatedBy(task.getCreatedBy())
                        .taskCreateDate(task.getCreateDate())
                        .taskUpdateDate(task.getLastModified())
                        .build())
                .toList();
        return ResponseEntity.ok(taskResponses);
    }
    public ResponseEntity<TaskResponse> getTaskById(Integer id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            throw new ApiRequestException("task not found");
        }
        TaskResponse response = TaskResponse.builder()
                .taskDescription(task.getDescription())
                .taskId(task.getId())
                .taskStatus(task.getStatus())
                .taskCreateDate(task.getCreateDate())
                .bugs(task.getBugList())
                .hasBugs(!bugRespository.findByTaskID(task.getId()).isEmpty())
                .taskCreatedBy(task.getCreatedBy())
                .taskCreateDate(task.getCreateDate())
                .taskUpdateDate(task.getLastModified())
                .build();
        return ResponseEntity.ok(response);
//        return taskRepository.findById(id).get();
    }
    public TaskResponse createTask(TaskRequest request) {
        Project project = projectRepository.findById(request.getProject_id()).orElse(null);
//        User user = userRepository.getReferenceById(request.getUser_id());
        if (project == null) {
            throw new ApiRequestException("project not found");
        }
        Task task = Task.builder()
//                .title(request.getTaskName())
                .description(request.getTaskDescription())
                .status(NEW)
                .project(project)
                .build();
                taskRepository.save(task);

        return TaskResponse.builder().taskId(task.getId())
//                      .taskName(task.getTitle())
                      .taskDescription(task.getDescription())
                      .taskStatus(task.getStatus())
                      .taskProject(task.getProject().getId())
                      .taskCreatedBy(task.getCreatedBy())
                      .taskCreateDate(task.getCreateDate())
                      .taskUpdatedBy(task.getLastModifiedBy())
                      .taskUpdateDate(task.getLastModified())
                      .build();
    }
    public void deleteTaskById(Integer id) {
        taskRepository.deleteById(id);
    }
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }
    public ResponseEntity<List<TaskResponse>> getTasksByProjectId(Integer projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : tasks){
            taskResponses.add( TaskResponse.builder()
                    .taskDescription(task.getDescription())
                    .taskId(task.getId())
                    .taskProject(task.getProject().getId())
                    .hasBugs(!bugRespository.findByTaskID(task.getId()).isEmpty())
                    .bugs(task.bugList)
                    .taskCreatedBy(task.getCreatedBy())
                    .taskCreateDate(task.getCreateDate())
                    .taskStatus(task.getStatus())
                    .taskUpdateDate(task.getLastModified())
                    .taskUpdatedBy(task.getLastModifiedBy())
                    .build());
        }
        return ResponseEntity.ok(taskResponses);
    }

    public ResponseEntity<TaskResponse> updateTask(Integer id, TaskRequest request , Principal principal) {
        Task task = taskRepository.findById(id).orElse(null);
        var user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        if(task == null) {
            throw new ApiRequestException("task not found");
        }
        ProjectMembership projectMembership = projectMembershipRepository.getProjectMemberships(task.getProject().getId() ,user.getId());
        Project project = projectRepository.getReferenceById(request.getProject_id());
        if(projectMembership == null) {
            throw new ApiRequestException("You are not allowed to update this project, You must join to the project first");
        }

//        task.setTitle(request.getTaskName());
        task.setDescription(request.getTaskDescription());
        task.setStatus(TaskStatus.valueOf(request.getTaskStatus()));
        task.setProject(project);
        taskRepository.save(task);
//        task.set


        return ResponseEntity.ok(TaskResponse.builder()
                .taskDescription(task.getDescription())
                .taskId(task.getId())
                .taskProject(task.getProject().getId())
                        .hasBugs(!bugRespository.findByTaskID(task.getId()).isEmpty())
                .bugs(task.bugList)
                .taskCreatedBy(task.getCreatedBy())
                .taskCreateDate(task.getCreateDate())
                .taskStatus(task.getStatus())
                .taskUpdateDate(task.getLastModified())
                .taskUpdatedBy(task.getLastModifiedBy())
                .build());

    }
}
