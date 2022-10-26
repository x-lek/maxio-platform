package com.maxio.maxioapi.controller;

import com.maxio.maxioapi.entity.Post;
import com.maxio.maxioapi.service.MaxioApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MaxioApiController {
    private final MaxioApiService maxioApiService;

    @GetMapping("/post")
    public List<Post> search(
            @RequestBody String query,
            @RequestHeader(name="Authorization") String token) throws IOException {
        return maxioApiService.search(query, token);
    }

    @GetMapping("/media")
    public @ResponseBody byte[] getObject(
            @RequestParam String bucket,
            @RequestParam String objectName,
            @RequestHeader(name="Authorization") String token) throws IOException {
        return maxioApiService.getObject(bucket, objectName, token);
    }

}
