package com.maxio.maxioapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@Document(indexName = "post")
public class Post {

    @Id
    Long id;
    String threadId;
    String threadTitle;
    int localPostId;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    Date publishedDate;
    String publishedDateString;
    String author;
    Long authorPostCount;
    String html;
    String text;
    List<Image> images;
    List<Quote> quotes;
}
