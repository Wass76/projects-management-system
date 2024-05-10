package com.ProjectsManagementSystem.bug;

import com.ProjectsManagementSystem.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/v1/bugs")
@RequiredArgsConstructor
@Tag(name = "Bugs")
public class BugController {

    private final BugService bugService;
    @Operation(
            description = "This endpoint build to get all bugs which is in our system",
            summary = "get all bugs",
            responses = {
                    @ApiResponse(
                            description = "get all done successfully",
                            responseCode = "200"
                    )
            }
    )

    @GetMapping
    public ResponseEntity<List<Bug>> getAll() {
        return bugService.findAll();
    }
    @Operation(
            description = "This endpoint build to get bug by id which is in our system",
            summary = "get bug by id",
            responses = {
                    @ApiResponse(
                            description = "get one bug done successfully",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<Bug> getById(@PathVariable Integer id) {
        return bugService.findById(id);
    }
    @Operation(
            description = "This endpoint build to create new bug for some task in our system",
            summary = "add new bug",
            responses = {
                    @ApiResponse(
                            description = "added new bug done successfully",
                            responseCode = "200"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Bug> create(@RequestBody BugRequest request) {
        return bugService.save(request);
    }
    @Operation(
            description = "This endpoint build to edit bug status or bug details which is in our system",
            summary = "Edit bug by id",
            responses = {
                    @ApiResponse(
                            description = "Edited done successfully",
                            responseCode = "200"
                    )
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<Bug> update(
            @RequestBody BugRequest request,
            @PathVariable Integer id,
            Principal connectedUser
    ){
        return bugService.update(id,request);
    }
    @Operation(
            description = "This endpoint build to delete some bug by id which is in our system",
            summary = "Delete bug by id",
            responses = {
                    @ApiResponse(
                            description = "Delete Done successfully",
                            responseCode = "200"
                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return bugService.deleteByTaskId(id);
    }
    @Operation(
            description = "This endpoint build to get all tasks by task id which is in our system",
            summary = "Get bugs by taskId",
            responses = {
                    @ApiResponse(
                            description = "Get done successfully",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("bugs-by-task/{taskId}")
    public ResponseEntity<List<Bug>> getBugsByTask(@PathVariable("taskId") Integer taskId) {
        return bugService.findByTaskId(taskId);
    }

    @PutMapping("bug-reaction/{id}")
    public ResponseEntity<Bug> react(@PathVariable Integer id ,Integer react) {
        return bugService.reactById(id,react);
    }
}
