package com.ProjectsManagementSystem.comment;

import com.ProjectsManagementSystem.bug.BugRespository;
import com.ProjectsManagementSystem.user.User;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
@Tag(name = "Comments")
public class CommentController {

    private final CommentService commentService;
    private final BugRespository bugRespository;

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return commentService.getAllComments();
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest request , Principal principal) {
        return commentService.addComment(request,principal);
    }

    @GetMapping("{id}")
    public ResponseEntity<Comment> getComment(@PathVariable("id")Integer id) {
        return commentService.getCommentById(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") Integer id, @RequestBody CommentRequest request , Principal principal) {
        return commentService.updateComment(id,request,principal);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Integer id) {
        return commentService.deleteComment(id);
    }






}
