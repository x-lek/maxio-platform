package com.maxio.maxioapi.repository;

import com.maxio.maxioapi.entity.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface PostRepository extends ElasticsearchRepository<Post, Long> {
    Iterable<Post> findByAuthor(String author);
}
