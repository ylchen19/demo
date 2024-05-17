package com.example.demo.controller;

import com.example.demo.apiRequest.PostRequest;
import com.example.demo.apiResponse.StatusResponse;
import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@Tag(name = "Post.", description = "Post add, getAll, update, delete.")
@Slf4j
public class PostController {
    private final PostService postService;

    @Operation(summary = "Add new post.")
    @PostMapping("/addPost")
    public ResponseEntity<StatusResponse> addPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(postService.Post(postRequest));
    }

    @Operation(summary = "Get all posts.")
    @GetMapping("/getAllPosts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPost());
    }

    @Operation(summary = "Update post.")
    @PutMapping("/{id}")
    public ResponseEntity<StatusResponse> updatePost(
            @RequestBody PostRequest postRequest,
            @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity.ok(postService.UpdatePost(postRequest, id));
    }

    @Operation(summary = "Delete post.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }
}
