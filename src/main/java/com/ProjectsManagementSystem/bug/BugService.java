package com.ProjectsManagementSystem.bug;

import com.ProjectsManagementSystem.comment.CommentRepository;
import com.ProjectsManagementSystem.exception.ApiRequestException;
import com.ProjectsManagementSystem.task.Task;
import com.ProjectsManagementSystem.task.TaskRepository;
import com.ProjectsManagementSystem.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BugService {
    private final BugRespository bugRespository;
    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;


    public ResponseEntity<List<Bug>> findAll() {

        return ResponseEntity.ok(bugRespository.findAll()) ;
    }
    public ResponseEntity<Bug> findById(Integer id) {
    Bug bug =  bugRespository.findById(id).orElse(null);
        if (bug == null) {
            throw new ApiRequestException("Bug not found");
        }
        return ResponseEntity.ok(bug);
    }
    public ResponseEntity< BugResponse> createBug(BugRequest request) {
        if(request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new ApiRequestException("Title is required");
        }
        if(request.getDescription() == null || request.getDescription().isEmpty()) {
            throw new ApiRequestException("Description is required");
        }
        Task task = taskRepository.findById(request.getTaskId()).orElse(null);
        if(task == null) {
            throw new ApiRequestException("task not found");
        }
        Bug newBug = Bug.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .task(task)
                .priority(0)
                .build();
        bugRespository.save(newBug);
        return ResponseEntity.ok(BugResponse.builder()
                .id(newBug.getId())
                .title(newBug.getTitle())
                .description(newBug.getDescription())
                .priority(newBug.getPriority())
                .taskId(newBug.getTask().getId())
                .hasComments(!commentRepository.findByBugId(newBug.getId()).isEmpty())
                .comments(newBug.getComments())
                .createdBy(newBug.getCreatedBy())
                .modifiedBy(newBug.getLastModifiedBy())
                .creationDate(newBug.getCreateDate())
                .modificationDate(newBug.getLastModified())
                .build());
    }

    public ResponseEntity<BugResponse> updateBug(
            Integer id,
            BugRequest request
//            Principal connectedUser
    ) {
        try {
            if(request.getTitle() == null || request.getTitle().isEmpty()) {
                throw new ApiRequestException("Title is required");
            }
            if(request.getDescription() == null || request.getDescription().isEmpty()) {
                throw new ApiRequestException("Description is required");
            }
//            if(request.getPriority() == null) {
//                throw new ApiRequestException("Priority is required");
//            }
            Bug updatedBug = bugRespository.findById(id).get();

//            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
//            if(user.getId().equals(updatedBug.get))


//            if(request.getPriority() == 0) {
//                Integer bugPriority =  updatedBug.getPriority();
                updatedBug.setTitle(request.getTitle());
                updatedBug.setDescription(request.getDescription());
//                updatedBug.setPriority(bugPriority -1);
//            }
//            else if(request.getPriority() == 1) {
//                Integer bugPriority =  updatedBug.getPriority();
//                updatedBug.setTitle(request.getTitle());
//                updatedBug.setDescription(request.getDescription());
//                updatedBug.setPriority(bugPriority +1);
//            }
            bugRespository.save(updatedBug);
            return ResponseEntity.ok(BugResponse.builder()
                    .id(updatedBug.getId())
                    .title(updatedBug.getTitle())
                    .description(updatedBug.getDescription())
                    .priority(updatedBug.getPriority())
                    .taskId(updatedBug.getTask().getId())
                    .hasComments(!commentRepository.findByBugId(updatedBug.getId()).isEmpty())
                    .comments(updatedBug.getComments())
                    .createdBy(updatedBug.getCreatedBy())
                    .modifiedBy(updatedBug.getLastModifiedBy())
                    .creationDate(updatedBug.getCreateDate())
                    .modificationDate(updatedBug.getLastModified())
                    .build());

        }catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }
    public ResponseEntity<List<BugResponse>> findByTaskId(Integer taskId) {
     List<Bug> bugs =   bugRespository.findByTaskID(taskId);
     List<BugResponse> bugResponses = new ArrayList<>();
     for (Bug bug : bugs) {
         bugResponses.add(BugResponse.builder()
                 .id(bug.getId())
                 .title(bug.getTitle())
                 .description(bug.getDescription())
                 .priority(bug.getPriority())
                 .taskId(bug.getTask().getId())
                 .hasComments(!commentRepository.findByBugId(bug.getId()).isEmpty())
                 .comments(bug.getComments())
                 .createdBy(bug.getCreatedBy())
                 .modifiedBy(bug.getLastModifiedBy())
                 .creationDate(bug.getCreateDate())
                 .modificationDate(bug.getLastModified())
                 .build());
     }
    return ResponseEntity.ok(bugResponses);
    }
    public ResponseEntity<String>  deleteByTaskId(Integer taskId) {
        Bug bug = bugRespository.findById(taskId).get();
        bugRespository.delete(bug);
        return ResponseEntity.ok("Bug deleted") ;
    }

    public ResponseEntity<BugResponse> reactById(Integer id ,Integer react) {
      Bug bug =   bugRespository.findById(id).orElse(null);
        if(bug == null) {
            throw new ApiRequestException("bug not found");
        }
        if(react == null) {
            throw new ApiRequestException("react not found");
        }
        Integer bugPriority =  bug.getPriority();
        if(react == 0) {
            bug.setPriority(bugPriority -1);
        }
        else if(react == 1) {
            bug.setPriority(bugPriority +1);
        }
        bugRespository.save(bug);
        return ResponseEntity.ok(BugResponse.builder()
                .id(bug.getId())
                .title(bug.getTitle())
                .description(bug.getDescription())
                .priority(bug.getPriority())
                .taskId(bug.getTask().getId())
                .hasComments(!commentRepository.findByBugId(bug.getId()).isEmpty())
                .comments(bug.getComments())
                .createdBy(bug.getCreatedBy())
                .modifiedBy(bug.getLastModifiedBy())
                .creationDate(bug.getCreateDate())
                .modificationDate(bug.getLastModified())
                .build());
    }
}
