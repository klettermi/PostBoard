package com.example.post.dto;

import com.example.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
    }

    public PostResponseDto(String username, Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = username;
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
    }
}
