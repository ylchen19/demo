package com.example.demo.service;

import com.example.demo.apiRequest.PostRequest;
import com.example.demo.apiResponse.StatusResponse;
import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public StatusResponse Post(PostRequest request) {
        var post = Post.builder()
                .content(request.getContent())
                .createdAt(request.getCreatedAt())
                .build();
        postRepository.save(post);
        return StatusResponse.builder()
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .status("Success")
                .build();
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public StatusResponse UpdatePost(PostRequest request, Long id) {
        var post = postRepository.findById(id).orElseThrow();
        post.setContent(request.getContent());
        postRepository.save(post);
        return StatusResponse.builder()
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .status("Success")
                .build();
    }

    public void deletePostById(Long id) {
        var post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
    }
}
