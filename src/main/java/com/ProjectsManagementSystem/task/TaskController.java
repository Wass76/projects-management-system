package com.ProjectsManagementSystem.task;

import com.ProjectsManagementSystem.exception.ApiRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
@Tag( name = "Tasks")
public class TaskController {
    private final TaskService taskService;

    @Operation(
            description = "This endpoint build to get all tasks which is in our system",
            summary = "get all tasks",
            responses = {
                    @ApiResponse(
                            description = "get all tasks done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "there is no tasks yet",
                            responseCode = "203"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
//        return ResponseEntity.build( taskService.getAllTasks());
        return taskService.getAllTasks();
    }
    @Operation(
            description = "This endpoint build to get task by id which is in our system",
            summary = "Get all tasks",
            responses = {
                    @ApiResponse(
                            description = "get task by id done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "there is no task in this id",
                            responseCode = "203"
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
        if(id == null || id<=0){
            throw new ApiRequestException("Invalid Id");
//            return ResponseEntity.badRequest().body("Invalid Id");
        }
        return  taskService.getTaskById(id);
    }

    @Operation(
            description = "This endpoint build to create new task on our system on some project",
            summary =  "Add new task",
            responses ={
                    @ApiResponse(
                            description = "add new task to project done successfully",
                            responseCode = "200"
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<TaskResponse> addTask(TaskRequest request) {
//        try {
//            Task task = taskService.createTask(request);
//            return ResponseEntity.ok(task);
//
//        }
//        catch (ApiRequestException e) {
//            throw new ApiRequestException(e.getMessage());
//        }
        return ResponseEntity.ok(taskService.createTask(request));
    }

    @Operation(
            description = "This endpoint build to edit task's status by one user",
            summary =  "Edit task status",
            responses ={
                    @ApiResponse(
                            description = "edit status done successfully",
                            responseCode = "200"
                    )

//                    @ApiResponse(
//                            description = "there is no projects yet",
//                            responseCode = "203"
//                    ),
//                    @ApiResponse(
//                            description = "unauthorized",
//                            responseCode = "403"
//                    )
            }
    )

    @GetMapping("/tasks-by-project/{project_id}")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable Integer project_id) {
        return ResponseEntity.ok(taskService.getTasksByProjectId(project_id)) ;
    }
    @PutMapping("{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id, TaskRequest request) {
        return taskService.updateTask(id, request);
    }

    @Operation(
            description = "This endpoint build to delete task by id",
            summary =  "Delete task from the project",
            responses ={
                    @ApiResponse(
                            description = "delete done successfully",
                            responseCode = "200"
                    )

//                    @ApiResponse(
//                            description = "there is no projects yet",
//                            responseCode = "203"
//                    ),
//                    @ApiResponse(
//                            description = "unauthorized",
//                            responseCode = "403"
//                    )
            }
    )

    @DeleteMapping
    public ResponseEntity<String> deleteTask(@PathVariable Integer id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.ok("Task deleted successfully");
    }

}
