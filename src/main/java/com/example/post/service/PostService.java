package com.example.post.service;

import com.example.post.dto.PostRequestDto;
import com.example.post.dto.PostResponseDto;
import com.example.post.dto.SuccessFlagDto;
import com.example.post.entity.Post;
import com.example.post.jwt.JwtUtil;
import com.example.post.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
        String username = "";

        // HttpServlet에서 쿠키 가져와 JWT 토큰 꺼내
        String jwt = jwtUtil.getTokenFromRequest(request);
        System.out.println("jwt = " + jwt);
        // 쿠키에서 JWT 토큰 자르기
        jwt = jwtUtil.substringToken(jwt);

        if(jwtUtil.validateToken(jwt)){
            username = jwtUtil.getUserInfoFromToken(jwt).getSubject();
            Post post = new Post(username, requestDto);

            Post savePost = postRepository.save(post);
            PostResponseDto postResponseDto = new PostResponseDto(savePost);
            return postResponseDto;
        }else{
            throw new IllegalArgumentException("잘못된 접근입니다.");

        }
    }

    public List<PostResponseDto> getPost(){
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    public List<PostResponseDto> getIdPost(Long id){
        return postRepository.findAllByOrderByCreatedAtDesc().stream().filter(n -> n.getId() == id).map(PostResponseDto::new).toList();
    }

    @Transactional
    public Post updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request){
        Post post = findPost(id);
        String username = "";

        // HttpServlet에서 쿠키 가져와 JWT 토큰 꺼내
        String jwt = jwtUtil.getTokenFromRequest(request);
        System.out.println("jwt = " + jwt);
        // 쿠키에서 JWT 토큰 자르기
        jwt = jwtUtil.substringToken(jwt);
        username = jwtUtil.getUserInfoFromToken(jwt).getSubject();

        if(jwtUtil.validateToken(jwt) && username.equals(post.getUsername())){
            post.update(requestDto);
        }else{
            throw new IllegalArgumentException("잘못된 접근입니다.");

        }
        return post;
    }

    public SuccessFlagDto deletePost(Long id, HttpServletRequest request){
        SuccessFlagDto success = new SuccessFlagDto();
        Post post = findPost(id);
        String username = "";
        // HttpServlet에서 쿠키 가져와 JWT 토큰 꺼내
        String jwt = jwtUtil.getTokenFromRequest(request);

        // 쿠키에서 JWT 토큰 자르기
        jwt = jwtUtil.substringToken(jwt);

        username = jwtUtil.getUserInfoFromToken(jwt).getSubject();

        if(jwtUtil.validateToken(jwt) && username.equals(post.getUsername())){
            postRepository.delete(post);
            success.setSuccess(true);
        }else{
            throw new IllegalArgumentException("잘못된 접근입니다.");

        }
        return success;
    }
    private Post findPost(Long id){
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
