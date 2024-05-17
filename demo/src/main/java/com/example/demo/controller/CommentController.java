package com.example.demo.controller;

import com.example.demo.apiRequest.CommentRequest;
import com.example.demo.apiResponse.StatusResponse;
import com.example.demo.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@Tag(name = "Comment.", description = "Comment")
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Add new comment")
    @PostMapping("/{postId}/addComments")
    public ResponseEntity<StatusResponse> addComment(
            @PathVariable(value = "postId") Long postId,
            @RequestBody CommentRequest commentRequest
    ) {
        return ResponseEntity.ok(commentService.createComment(postId, commentRequest));
    }

    @Operation(summary = "get comments")
    @GetMapping("/{postId}/getComments")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        return ResponseEntity.ok(commentService.getCommentByPostId(postId));
    }
}
