package com.maxio.maxioapi.controller;

import com.maxio.maxioapi.entity.Post;
import com.maxio.maxioapi.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@Slf4j
@RestController
public class MaxioApiController {
    private final PostRepository postRepository;

    public MaxioApiController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //@RolesAllowed("kibana-user")
    //, @RequestHeader(name="Authorization") String token
    @GetMapping("/post")
    public Iterable<Post> searchPost(@RequestParam String author) {
        log.info("Searching for author {}", author);
        //log.info(token);
        return postRepository.findByAuthor(author);
    }

//
//
//    @GetMapping("/raw")
}
