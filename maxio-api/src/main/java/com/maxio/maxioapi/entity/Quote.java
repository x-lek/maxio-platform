package com.maxio.maxioapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Quote {
    String author;
    String postId;
    String text;
}