package com.example.post.controller;

import com.example.post.dto.PostRequestDto;
import com.example.post.dto.PostResponseDto;
import com.example.post.dto.SuccessFlagDto;
import com.example.post.entity.Post;
import com.example.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public PostResponseDto createNotice(@RequestBody PostRequestDto requestDto, HttpServletRequest request){
        return postService.createPost(requestDto, request);
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(){
        return postService.getPost();
    }

    @GetMapping("/posts/{id}")
    public List<PostResponseDto> getIdPosts(@PathVariable Long id){
        return postService.getIdPost(id);
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request){
        return postService.updatePost(id, requestDto, request);
    }

    @DeleteMapping("/posts/{id}")
    public SuccessFlagDto deletePost(@PathVariable Long id, HttpServletRequest request){
        return postService.deletePost(id, request);
    }
}
