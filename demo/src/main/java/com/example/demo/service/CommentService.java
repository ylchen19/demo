package com.example.demo.service;

import com.example.demo.apiRequest.CommentRequest;
import com.example.demo.apiResponse.StatusResponse;
import com.example.demo.entity.Comment;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public StatusResponse createComment(Long postId, CommentRequest commentRequest) {
        var post = postRepository.findById(postId).orElseThrow();
        var comment = Comment.builder()
                .content(commentRequest.getContent())
                .createdAt(commentRequest.getCreatedAt())
                .build();
        comment.setPost(post);
        commentRepository.save(comment);
        return StatusResponse.builder()
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .status("Success")
                .build();
    }

    public Optional<Comment> getCommentByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
