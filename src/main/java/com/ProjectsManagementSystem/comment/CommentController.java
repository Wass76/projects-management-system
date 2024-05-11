package com.ProjectsManagementSystem.comment;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
@Tag(name = "Comments")
@Hidden
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return commentService.getAllComments();
    }






}
