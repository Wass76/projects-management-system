package com.ProjectsManagementSystem.task;

import com.ProjectsManagementSystem.project.Project;
import com.ProjectsManagementSystem.project.ProjectRepository;
import com.ProjectsManagementSystem.user.User;
import com.ProjectsManagementSystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ProjectsManagementSystem.task.TaskStatus.NEW;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public ResponseEntity< List<Task>> getAllTasks() {
//        return  taskRepository.findAll();
        return ResponseEntity.ok(taskRepository.findAll());
    }
    public ResponseEntity<Task> getTaskById(Integer id) {
        Task task = taskRepository.findById(id).orElse(null);
        return ResponseEntity.ok(task);
//        return taskRepository.findById(id).get();
    }
    public TaskResponse createTask(TaskRequest request) {
        Project project = projectRepository.getReferenceById(request.getProject_id());
//        User user = userRepository.getReferenceById(request.getUser_id());


        Task task = Task.builder().title(request.getTaskName())
                .description(request.getTaskDescription())
                .status(NEW)
                .project(project)
                .build();
                taskRepository.save(task);

              TaskResponse taskResponse = TaskResponse.builder().taskId(task.getId())
                      .taskName(task.getTitle())
                      .taskDescription(task.getDescription())
                      .taskStatus(task.getStatus())
//                      .taskProject(task.getProject())
                      .taskCreatedBy(task.getCreatedBy())
                      .taskCreateDate(task.getCreateDate())
                      .taskUpdatedBy(task.getLastModifiedBy())
                      .taskUpdateDate(task.getLastModified())
                      .build();
              return taskResponse;

    }
    public void deleteTaskById(Integer id) {
        taskRepository.deleteById(id);
    }
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }
    public List<Task> getTasksByProjectId(Integer projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks;
    }

    public ResponseEntity<Task> updateTask(Integer id, TaskRequest request) {
        Task task = taskRepository.findById(id).orElse(null);
        Project project = projectRepository.getReferenceById(request.getProject_id());
//        User user = userRepository.getReferenceById(request.getUser_id());
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        task.setTitle(request.getTaskName());
        task.setDescription(request.getTaskDescription());
        task.setStatus(TaskStatus.valueOf(request.getTaskStatus()));
        task.setProject(project);
//        task.set
        return ResponseEntity.ok(taskRepository.save(task));

    }
}
