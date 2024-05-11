package com.ProjectsManagementSystem.comment;

import com.ProjectsManagementSystem.bug.Bug;
import com.ProjectsManagementSystem.bug.BugRespository;
import com.ProjectsManagementSystem.exception.ApiRequestException;
import com.ProjectsManagementSystem.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BugRespository bugRespository;

    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        if (comments.isEmpty()) {
            throw new ApiRequestException("No comments found");
        }
        return ResponseEntity.ok(comments);
    }
    public ResponseEntity<Comment> getCommentById(int id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return ResponseEntity.ok(comment.get());
        }
        throw new ApiRequestException("Comment not found");
    }
    public ResponseEntity<List<Comment>> findByBugId(Integer id) {
        Bug bug = bugRespository.findById(id).get();
        if (bug == null) {
            throw new ApiRequestException("Bug not found");
        }
        List<Comment>  comments = commentRepository.findByBugId(id);
        return ResponseEntity.ok(comments);
    }

    public ResponseEntity<Comment> addComment(CommentRequest request , Principal principal) {
        if (request.getBugId() == null || request.getComment() == null) {
            throw new ApiRequestException("Invalid comment requested");
        }
        Bug bug = bugRespository.findById(request.getBugId()).orElse(null);
        if (bug == null) {
            throw new ApiRequestException("Bug not found");
        }
        var user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    Comment comment = Comment.builder()
            .comment(request.getComment())
            .bug(bug)
            .author(user.getFirstName())
            .priority(0)
            .build();
    commentRepository.save(comment);
    return ResponseEntity.ok(comment);
    }
}
