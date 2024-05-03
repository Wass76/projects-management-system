package com.ProjectsManagementSystem.bug;

import com.ProjectsManagementSystem.exception.ApiRequestException;
import com.ProjectsManagementSystem.task.Task;
import com.ProjectsManagementSystem.task.TaskRepository;
import com.ProjectsManagementSystem.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BugService {
    private final BugRespository bugRespository;
    private final TaskService taskService;
    private final TaskRepository taskRepository;


    public ResponseEntity<List<Bug>> findAll() {

        return ResponseEntity.ok(bugRespository.findAll()) ;
    }
    public ResponseEntity<Bug> findById(Integer id) {
        return ResponseEntity.ok(bugRespository.findById(id).get()) ;
    }
    public ResponseEntity< Bug> save(BugRequest request) {
        if(request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new ApiRequestException("Title is required");
        }
        if(request.getDescription() == null || request.getDescription().isEmpty()) {
            throw new ApiRequestException("Description is required");
        }
        Task task = taskRepository.getById(request.getTaskId());
        if(task == null) {
            throw new ApiRequestException("task not found");
        }
        Bug newBug = Bug.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .task(task)
                .priority(0)
                .build();
        return ResponseEntity.ok(bugRespository.save(newBug)) ;
    }
    public void delete(Bug bug) {
        bugRespository.delete(bug);
    }

    public ResponseEntity<Bug> update(Integer id, BugRequest request) {
        try {
            if(request.getTitle() == null || request.getTitle().isEmpty()) {
                throw new ApiRequestException("Title is required");
            }
            if(request.getDescription() == null || request.getDescription().isEmpty()) {
                throw new ApiRequestException("Description is required");
            }
            if(request.getPriority() == null) {
                throw new ApiRequestException("Priority is required");
            }
            Bug updatedBug = bugRespository.findById(id).get();
            if(request.getPriority() == 0) {
                Integer bugPriority =  updatedBug.getPriority();
                updatedBug.setTitle(request.getTitle());
                updatedBug.setDescription(request.getDescription());
                updatedBug.setPriority(bugPriority -1);
            }
            else if(request.getPriority() == 1) {
                Integer bugPriority =  updatedBug.getPriority();
                updatedBug.setTitle(request.getTitle());
                updatedBug.setDescription(request.getDescription());
                updatedBug.setPriority(bugPriority +1);
            }
            return ResponseEntity.ok(bugRespository.save(updatedBug)) ;

        }catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }
    public ResponseEntity<List<Bug>> findByTaskId(Integer taskId) {
        return ResponseEntity.ok(bugRespository.findByTaskID(taskId));
    }
    public ResponseEntity<String>  deleteByTaskId(Integer taskId) {
        Bug bug = bugRespository.findById(taskId).get();
        bugRespository.delete(bug);
        return ResponseEntity.ok("Bug deleted") ;
    }
}
